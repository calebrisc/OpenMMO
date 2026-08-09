package de.fiereu.openmmo.patcher

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.nio.file.Files
import java.util.Comparator

class ClientConfigOverrideTest :
    FunSpec({
      test("writes and removes a temporary client configuration") {
        val directory = Files.createTempDirectory("openmmo-config-test")
        try {
          val configuration = directory.resolve("config/openmmo.properties")
          ClientConfigOverride.install(directory, "185.223.30.83", 2106).use {
            val content = Files.readString(configuration)
            content shouldContain "client.misc.ignore_feed=true"
            content shouldContain "loginserver.network.client.host=185.223.30.83"
            content shouldContain "loginserver.network.client.port=2106"
          }
          Files.exists(configuration) shouldBe false
        } finally {
          Files.walk(directory).use { paths ->
            paths.sorted(Comparator.reverseOrder()).forEach(Files::deleteIfExists)
          }
        }
      }

      test("restores an existing client configuration") {
        val directory = Files.createTempDirectory("openmmo-config-test")
        try {
          val configuration = directory.resolve("config/openmmo.properties")
          Files.createDirectories(configuration.parent)
          Files.writeString(configuration, "existing=value\n")
          ClientConfigOverride.install(directory, "login.openmmo.dev", 2216).use {}
          Files.readString(configuration) shouldBe "existing=value\n"
        } finally {
          Files.walk(directory).use { paths ->
            paths.sorted(Comparator.reverseOrder()).forEach(Files::deleteIfExists)
          }
        }
      }
    })
