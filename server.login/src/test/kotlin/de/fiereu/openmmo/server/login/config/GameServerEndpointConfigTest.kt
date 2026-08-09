package de.fiereu.openmmo.server.login.config

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class GameServerEndpointConfigTest :
    FunSpec({
      test("rejects dotted IPv4 notation inside IPv6") {
        shouldThrow<IllegalArgumentException> {
          GameServerEndpointConfig(ipv6Address = "::ffff:203.0.113.10")
        }
      }

      test("accepts expanded mapped IPv6") {
        GameServerEndpointConfig(ipv6Address = "::ffff:cb00:710a")
      }
    })
