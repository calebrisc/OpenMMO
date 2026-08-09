package de.fiereu.openmmo.patcher

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.nio.file.Path

class LauncherOptionsTest :
    FunSpec({
      test("parses a remote tester launch") {
        val options =
            parseOptions(
                arrayOf(
                    "--login-host",
                    "login.openmmo.dev",
                    "--login-port",
                    "2216",
                    "--game-public-key",
                    "game.public.pem",
                    "--client",
                    "PokeMMO.exe",
                    "--",
                    "-Dexample=true",
                ))

        options.loginHost shouldBe "login.openmmo.dev"
        options.loginPort shouldBe 2216
        options.gamePublicKey shouldBe Path.of("game.public.pem")
        options.executable shouldBe Path.of("PokeMMO.exe")
        options.clientArgs.shouldContainExactly("-Dexample=true")
      }

      test("requires the deployed public key for remote servers") {
        shouldThrow<IllegalArgumentException> {
          parseOptions(arrayOf("--login-host", "login.openmmo.dev"))
        }
      }
    })
