package de.fiereu.openmmo.server.login.catalog

import com.github.maltalex.ineter.base.IPAddress
import de.fiereu.openmmo.server.login.config.GameServerEndpointConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GameServerCatalogTest :
    FunSpec({
      test("uses the configured game server endpoint") {
        val config =
            GameServerEndpointConfig(
                ipv4Address = "203.0.113.10",
                ipv6Address = "2001:db8::10",
                port = 17777,
                localAddress = "10.0.0.10",
                localHostname = "game.openmmo.dev",
            )

        val entry = GameServerCatalog(config).find(0u)!!

        entry.node.iPv4Address shouldBe IPAddress.of("203.0.113.10")
        entry.node.iPv6Address shouldBe IPAddress.of("2001:db8::10")
        entry.node.port shouldBe 17777u
        entry.localAddress shouldBe IPAddress.of("10.0.0.10")
        entry.localHostname shouldBe "game.openmmo.dev"
      }
    })
