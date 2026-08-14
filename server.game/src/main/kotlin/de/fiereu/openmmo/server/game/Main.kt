package de.fiereu.openmmo.server.game

import de.fiereu.openmmo.server.game.config.ConfigLoader
import de.fiereu.openmmo.server.game.di.DaggerGameServerComponent
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

private val log = KotlinLogging.logger {}

private val SHUTDOWN_FLUSH_TIMEOUT = 10.seconds

fun main() {
  val config = ConfigLoader.load()
  val component = DaggerGameServerComponent.factory().create(config)
  component.databaseBootstrap().migrate()
  val characterStore = component.characterStore()
  characterStore.startPeriodicFlush()
  runBlocking {
    // Guilds have to be in memory before the first player can ask which one they are in.
    component.guildStore().loadAll()
    component.devCharacterSeeder().seed()
  }
  Runtime.getRuntime()
      .addShutdownHook(
          Thread {
            runCatching {
                  runBlocking { withTimeout(SHUTDOWN_FLUSH_TIMEOUT) { characterStore.shutdown() } }
                }
                .onFailure { log.warn(it) { "Final character flush did not complete" } }
          })
  component.server().start()
}
