package de.fiereu.openmmo.server.login.config

import com.github.maltalex.ineter.base.IPAddress
import com.github.maltalex.ineter.base.IPv4Address
import com.github.maltalex.ineter.base.IPv6Address

data class GameServerEndpointConfig(
    val ipv4Address: String = "127.0.0.1",
    val ipv6Address: String = "::1",
    val port: Int = 7777,
    val localAddress: String = "127.0.0.1",
    val localHostname: String = "localhost",
) {
  init {
    IPv4Address.of(ipv4Address)
    IPv6Address.of(ipv6Address)
    IPAddress.of(localAddress)
    require(port in 1..UShort.MAX_VALUE.toInt()) { "Game server port is out of range" }
    require(localHostname.isNotBlank()) { "Game server hostname is blank" }
  }
}
