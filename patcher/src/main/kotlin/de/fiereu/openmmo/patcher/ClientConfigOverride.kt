package de.fiereu.openmmo.patcher

import java.nio.file.Files
import java.nio.file.Path

internal class ClientConfigOverride
private constructor(
    private val path: Path,
    private val original: ByteArray?,
) : AutoCloseable {

  override fun close() {
    if (original == null) Files.deleteIfExists(path) else Files.write(path, original)
  }

  companion object {
    fun install(workingDir: Path, loginHost: String, loginPort: Int): ClientConfigOverride {
      val path = workingDir.resolve("config").resolve("openmmo.properties")
      val original = if (Files.isRegularFile(path)) Files.readAllBytes(path) else null
      var content = original?.decodeToString().orEmpty()
      content = content.withProperty("client.misc.ignore_feed", "true")
      content = content.withProperty("loginserver.network.client.host", loginHost)
      content = content.withProperty("loginserver.network.client.port", loginPort.toString())
      Files.createDirectories(path.parent)
      Files.writeString(path, content)
      Launcher.log { "Using login server $loginHost:$loginPort" }
      return ClientConfigOverride(path, original)
    }
  }
}

private fun String.withProperty(key: String, value: String): String {
  val line = "$key=$value"
  val pattern = Regex("^${Regex.escape(key)}=.*$", RegexOption.MULTILINE)
  if (pattern.containsMatchIn(this)) return pattern.replace(this, line)
  val separator = if (isEmpty() || endsWith("\n")) "" else System.lineSeparator()
  return this + separator + line + System.lineSeparator()
}
