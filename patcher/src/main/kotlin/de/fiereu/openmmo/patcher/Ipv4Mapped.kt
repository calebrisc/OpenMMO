package de.fiereu.openmmo.patcher

object Ipv4Mapped {
  fun literal(address: String, width: Int): String? {
    val octets = address.split('.').map { it.toIntOrNull() ?: return null }
    if (octets.size != 4 || octets.any { it !in 0..255 }) return null
    val canonical = octets.joinToString(".")
    val base = "[::ffff:$canonical]"
    val padding = width - base.length
    if (padding !in 0..4) return null
    if (padding == 0) return base
    return "[${"0".repeat(padding)}::ffff:$canonical]"
  }
}
