package de.fiereu.openmmo.server.login.catalog

import com.github.maltalex.ineter.base.IPAddress
import com.github.maltalex.ineter.base.IPv4Address
import com.github.maltalex.ineter.base.IPv6Address
import de.fiereu.openmmo.net.login.packets.GameServer
import de.fiereu.openmmo.net.login.packets.GameServerNode
import de.fiereu.openmmo.server.login.config.GameServerEndpointConfig
import javax.inject.Inject
import javax.inject.Singleton

data class GameServerEntry(
    val server: GameServer,
    val node: GameServerNode,
    val localAddress: IPAddress,
    val localHostname: String,
)

@Singleton
class GameServerCatalog @Inject constructor(config: GameServerEndpointConfig) {
  private val entries: List<GameServerEntry> =
      listOf(
          GameServerEntry(
              server =
                  GameServer(
                      id = 0x00u,
                      name = "OpenMMO",
                      currentPlayers = 0u,
                      maxPlayers = 1u,
                      joinable = true,
                  ),
              node =
                  GameServerNode(
                      iPv4Address = IPv4Address.of(config.ipv4Address),
                      iPv6Address = IPv6Address.of(config.ipv6Address),
                      port = config.port.toUShort(),
                      weight = 0x01u,
                  ),
              localAddress = IPAddress.of(config.localAddress),
              localHostname = config.localHostname,
          ),
      )

  fun list(): List<GameServer> = entries.map { it.server }

  fun find(id: UByte): GameServerEntry? = entries.firstOrNull { it.server.id == id }
}
