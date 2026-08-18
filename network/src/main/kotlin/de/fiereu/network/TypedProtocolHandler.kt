package de.fiereu.network

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.reflect.KClass

private val log = KotlinLogging.logger {}

abstract class TypedProtocolHandler<P : Protocol>(
    protocol: P,
    side: Side,
) : ProtocolHandler(protocol, side) {

  @Volatile private var sealed = false

  private val handlers = mutableMapOf<KClass<*>, (PacketEvent<*>) -> Unit>()

  protected inline fun <reified T : Any> on(noinline handler: (PacketEvent<T>) -> Unit) {
    register(T::class, handler)
  }

  @PublishedApi
  internal fun <T : Any> register(type: KClass<T>, handler: (PacketEvent<T>) -> Unit) {
    if (sealed) {
      throw HandlerRegistrationException(
          "Cannot register handler for ${type.simpleName} after the first packet",
      )
    }
    if (handlers.containsKey(type)) {
      throw HandlerRegistrationException(
          "Duplicate handler registration for ${type.simpleName}",
      )
    }
    @Suppress("UNCHECKED_CAST")
    handlers[type] = handler as (PacketEvent<*>) -> Unit
  }

  open fun isRegistered(type: KClass<*>): Boolean = handlers.containsKey(type)

  protected open fun onUnhandled(event: PacketEvent<*>) {
    // The contents are the whole point of the line: an unhandled packet is one we are still
    // working out, and its field values are what identify what the client was asking for.
    log.error { "Unhandled packet ${event.packet::class.simpleName} on $side: ${event.packet}" }
  }

  final override fun onPacket(event: PacketEvent<*>) {
    sealed = true
    val handler = handlers[event.packet::class]
    if (trace) {
      // Says whether a packet arrived at all and whether anything was listening, which is the one
      // question three separate dead features have turned on: a box drag, a machine and a tutor
      // each looked broken while the real answer was that the client's reply went nowhere.
      log.info { "TRACE $side ${event.packet::class.simpleName} handled=${handler != null}" }
    }
    if (handler != null) {
      handler(event)
    } else if (!tryHandleAlternate(event)) {
      onUnhandled(event)
    }
  }

  protected open fun tryHandleAlternate(event: PacketEvent<*>): Boolean = false

  companion object {
    /**
     * Logs every packet as it arrives, and whether a handler took it.
     *
     * Off by default: it is one line per packet and movement alone is five a second. Turn it on for
     * the few seconds it takes to reproduce something, with /probe trace on.
     */
    @Volatile @JvmStatic var trace: Boolean = false
  }
}
