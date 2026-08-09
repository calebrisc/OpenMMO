package de.fiereu.openmmo.patcher

import java.io.StringReader
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import kotlin.io.encoding.Base64
import kotlin.system.exitProcess
import org.bouncycastle.util.io.pem.PemReader

private const val POKEMMO_PUBKEY_GAME =
    "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEtqx2myJz3ftlYWgd7cbNqf2t208itQMY7ouPNBDpQetbi7eXbEDxDDZy4Q9fMnI6mF5/D0qMdRd40SRXf0OS7Q=="
private const val POKEMMO_PUBKEY_CHAT =
    "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEh4Vqgnd+8Fqebu0H40v+FgwhE6RwgAYxJMihb8mJmcHDy8r/rPz3kLHH1oabyKIRUa5Y2cK0TsxZky+mp7DKWA=="

/** The client accepts a feed signed by either of these, so both are replaced with ours. */
private val POKEMMO_PUBKEYS_FEED =
    listOf(
        "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAyfYQx1kSfIVGdGzcHmVVP7cbyLsMXGdLhwMnx2AD1MYgU170iFN5gHT+U248rH10L6D1UMlZK1LfCsbPkdQOir3C+8Do212NONyNm/7+ZGeIwbpy+jxEQH8Jfn4JYY7+Sn4qg249yW7DSY+XKvTOcphoXRNzSQp8u6IVj03mIw7zDA0SqMMFtnCXVP3NRmtjK1SuVVFLltFctz1Pp7f9uqgqnFlgD2l8/THnddTRM5IR6O9pbOXu7My0+Jli6+4zJgw5gQvgivYPCeess9gWRqpw66VTpMJERJYA6AIbVierAbjGmtRETRsHUOGAgo54G0oxtXXEaTWXF6n6mdgSE2Ra8q7P23stsSWU3mDNQjXO0XOhtAKQCZfvICxmsH3ed5hm8bEC5yga8z8m0vyZ71fWzP4Q3g6B+o6oDsMX1nWbV2GEHci/6nwFofgOJkLINaZfUTivAIRuxECVwjTTa7ruRNgFlA2ciGUIIke2Ev2cYzyBA4LLARky2FZiEM0VAgMBAAE=",
        "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAyNb7iGEOL8/7hBkzvSm0edntPPO6/oaXlsl+1/OKukUifNnLlx+ApInkJyPy7PK6ds6R5C8liCvEEBD6G4/TRi8riG/8pIOCFAe/bjyBoXodzSJ2NRvArW6xVxax6Dpl5/tpsIBOSQDYH/BUXVZZM+DbIAbJhgvxK3r1eth7vO+kj3VLBVzJTrYpzEUz+cW+SxukOsDzHWxuUcogCEC3tzY0M3f/jMv8yrO/xVlWTzn7orrZtg0JfBMOs59NLLPKOlfB2Dtxg517Pbg2knpnNliKYE8vXwcYEJDb1jxgicqV4sYTNwu7MPSBEnYsPpBqbwbDIRI4Nrigqr+9D6Q7LPZjbI8JUZIJ1+pyYtXkSNOnnpbBPyT5WCMrviNDgocct+/PpzlYnwGRjGusqbR+6OTY9U2rptNETyK+0kmmNqxWCxAH93MqgiJk+WonkIaA5zkRlhpstlhIBwrQiYZWY6XgOMRvrdUom8FYLYKRIwRvoTRQG92gI8+WCL8+sjCpAgMBAAE=",
    )

private const val POKEMMO_LOGINSERVER = "loginserver.pokemmo.com"

/** Mirrors the client tries in turn until one serves a feed. */
private val POKEMMO_FEED_HOSTS =
    listOf(
        "dl.pokemmo.com",
        "dl.pokemmo.eu",
        "dl.pokemmo.download",
        "files.pokemmo.com",
        "files.pokemmo.eu",
    )

private const val EXECUTABLE_PROPERTY = "openmmo.executable"
private const val WORKING_DIR_PROPERTY = "openmmo.workingDir"
private const val OUTPUT_PROPERTY = "openmmo.output"

private const val TRUSTSTORE_NAME = "openmmo-truststore.p12"

object Launcher {

  @Volatile private var logger: (String) -> Unit = ::println

  fun log(message: () -> String) {
    logger("[OpenMMO Patcher] ${message()}")
  }

  @JvmStatic
  fun main(args: Array<String>) {
    if (args.contentEquals(arrayOf("--gui")) ||
        (args.isEmpty() &&
            System.getProperty(EXECUTABLE_PROPERTY) == null &&
            System.getenv("OPENMMO_CLIENT") == null)) {
      LauncherWindow.show()
      return
    }
    exitProcess(run(parseOptions(args)))
  }

  internal fun run(options: LauncherOptions, outputLog: (String) -> Unit = ::println): Int {
    logger = outputLog
    val executable =
        options.executable
            ?: System.getProperty(EXECUTABLE_PROPERTY)?.let(Path::of)
            ?: error("Pass --client <path to PokeMMO.exe>")
    require(Files.isRegularFile(executable)) { "Not a file: $executable" }

    val workingDir =
        options.workingDir
            ?: System.getProperty(WORKING_DIR_PROPERTY)?.let(Path::of)
            ?: executable.parent
            ?: error("Missing -D$WORKING_DIR_PROPERTY=<path to the PokeMMO install directory>")

    val output =
        options.output
            ?: System.getProperty(OUTPUT_PROPERTY)?.let(Path::of)
            ?: Path.of(System.getProperty("java.io.tmpdir"), "PokeMMO-openmmo.exe")

    val keyStore = FeedTls.keyStore()
    val feed = FeedServer(loadPrivateKey("/feed.private.key"), keyStore)
    val revision = readRevision(workingDir)
    feed.publish(revision, options.loginHost, options.loginPort)
    feed.start()
    log { "Serving the main feed on https://$LOOPBACK:${feed.port} for revision $revision" }
    log { "Login fallback points to ${loginServerLiteral(options.loginHost)}" }

    val patcher = ClientPatcher(patches(feed.port, options.loginHost, options.gamePublicKey))

    log { "Reading $executable" }
    val bytes = Files.readAllBytes(executable)

    val results = patcher.apply(bytes)
    results.forEach { (patch, count) -> log { "${patch.name}: replaced $count occurrences" } }
    val missed = results.filterValues { it == 0 }.keys
    if (missed.isNotEmpty()) {
      error(
          "No match for ${missed.joinToString { it.name }}. " +
              "The client probably changed the patched strings.")
    }

    output.parent?.let(Files::createDirectories)
    Files.write(output, bytes)
    if (!output.toFile().setExecutable(true)) {
      log { "Could not mark $output as executable" }
    }
    log { "Wrote patched client to $output" }

    val trustStore =
        FeedTls.writeTrustStore(
            feed.certificate(), workingDir, output.resolveSibling(TRUSTSTORE_NAME))

    return try {
      ClientConfigOverride.install(workingDir, options.loginHost, options.loginPort).use {
        log { "Launching $output in $workingDir" }
        ProcessBuilder(
                listOf(
                    output.toString(),
                    "-Djavax.net.ssl.trustStore=$trustStore",
                    "-Djavax.net.ssl.trustStorePassword=${FeedTls.password.concatToString()}",
                    "-Djavax.net.ssl.trustStoreType=PKCS12",
                ) + options.clientArgs)
            .directory(workingDir.toFile())
            .inheritIO()
            .start()
            .waitFor()
      }
    } finally {
      feed.stop()
      logger = ::println
    }
  }

  private fun patches(
      feedPort: Int,
      loginHost: String,
      gamePublicKey: Path?,
  ): List<ClientPatcher.Patch> =
      listOf(
          ClientPatcher.Patch(
              "GamePubKeyPatch",
              POKEMMO_PUBKEY_GAME,
              loadPublicKey("/game.public.key", gamePublicKey)),
          ClientPatcher.Patch(
              "ChatPubKeyPatch", POKEMMO_PUBKEY_CHAT, loadPublicKey("/chat.public.key")),
          ClientPatcher.Patch(
              "LoginServerPatch", POKEMMO_LOGINSERVER, loginServerLiteral(loginHost)),
      ) +
          POKEMMO_PUBKEYS_FEED.mapIndexed { index, key ->
            ClientPatcher.Patch("FeedPubKeyPatch$index", key, loadPublicKey("/feed.public.key"))
          } +
          POKEMMO_FEED_HOSTS.map { host ->
            val origin = "https://$host"
            ClientPatcher.Patch("FeedPatch$host", origin, Loopback.origin(feedPort, origin.length))
          }

  private fun readRevision(workingDir: Path): Long =
      runCatching { Files.readString(workingDir.resolve("revision.txt")).trim().toLong() }
          .getOrDefault(0)

  private fun loginServerLiteral(loginHost: String): String =
      Ipv4Mapped.literal(loginHost, POKEMMO_LOGINSERVER.length)
          ?: Loopback.literal(POKEMMO_LOGINSERVER.length)

  private fun loadPublicKey(path: String, external: Path? = null): String =
      Base64.Default.encode(external?.let(::readPem) ?: readResourcePem(path))

  private fun loadPrivateKey(path: String): PrivateKey =
      KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(readResourcePem(path)))

  private fun readResourcePem(path: String): ByteArray {
    val stream =
        Launcher::class.java.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Key not found at path: $path")
    return stream.use { parsePem(it.readBytes().decodeToString()) }
  }

  private fun readPem(path: Path): ByteArray {
    require(Files.isRegularFile(path)) { "Public key not found: $path" }
    return parsePem(Files.readString(path))
  }

  private fun parsePem(value: String): ByteArray =
      PemReader(StringReader(value)).use { it.readPemObject().content }
}

internal data class LauncherOptions(
    val loginHost: String,
    val loginPort: Int,
    val gamePublicKey: Path?,
    val executable: Path?,
    val workingDir: Path?,
    val output: Path?,
    val clientArgs: List<String>,
)

internal fun parseOptions(args: Array<String>): LauncherOptions {
  var loginHost = System.getenv("OPENMMO_LOGIN_HOST") ?: LOOPBACK
  var loginPort = System.getenv("OPENMMO_LOGIN_PORT")?.toInt() ?: 2106
  var gamePublicKey = System.getenv("OPENMMO_GAME_PUBLIC_KEY_FILE")?.let(Path::of)
  var executable = System.getenv("OPENMMO_CLIENT")?.let(Path::of)
  var workingDir = System.getenv("OPENMMO_WORKING_DIR")?.let(Path::of)
  var output = System.getenv("OPENMMO_PATCHED_CLIENT")?.let(Path::of)
  val clientArgs = mutableListOf<String>()
  var index = 0

  fun value(name: String): String {
    require(index + 1 < args.size) { "$name requires a value" }
    index += 1
    return args[index]
  }

  while (index < args.size) {
    when (args[index]) {
      "--login-host" -> loginHost = value("--login-host")
      "--login-port" -> loginPort = value("--login-port").toInt()
      "--game-public-key" -> gamePublicKey = Path.of(value("--game-public-key"))
      "--client" -> executable = Path.of(value("--client"))
      "--working-dir" -> workingDir = Path.of(value("--working-dir"))
      "--output" -> output = Path.of(value("--output"))
      "--" -> {
        clientArgs += args.drop(index + 1)
        break
      }
      else -> clientArgs += args[index]
    }
    index += 1
  }

  require(loginHost.matches(Regex("[A-Za-z0-9.:-]+"))) { "Invalid login host" }
  require(loginPort in 1..65535) { "Invalid login port" }
  require(loginHost == LOOPBACK || gamePublicKey != null) {
    "--game-public-key is required for a remote server"
  }
  return LauncherOptions(
      loginHost, loginPort, gamePublicKey, executable, workingDir, output, clientArgs)
}
