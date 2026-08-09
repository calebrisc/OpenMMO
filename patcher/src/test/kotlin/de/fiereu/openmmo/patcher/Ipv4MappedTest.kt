package de.fiereu.openmmo.patcher

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.net.InetAddress

class Ipv4MappedTest :
    FunSpec({
      test("writes the test server address at the login hostname width") {
        val literal = Ipv4Mapped.literal("185.223.30.83", 23)!!

        literal.length shouldBe 23
        InetAddress.getByName(literal.removeSurrounding("[", "]")).hostAddress shouldBe
            "185.223.30.83"
      }

      test("rejects hostnames and addresses that cannot fit") {
        Ipv4Mapped.literal("login.openmmo.dev", 23) shouldBe null
        Ipv4Mapped.literal("255.255.255.255", 23) shouldBe null
      }
    })
