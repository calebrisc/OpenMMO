package de.fiereu.network

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class PipelineOptions(
    val checksumSize: Int = 16,
    val writeTimeout: Duration = 25.minutes,
    val maxFrameLength: Int = 0xFFFF,
    val compressionThreshold: Int = 256,
    val maxHelloSkew: Duration = 5.minutes,
    val frameLogging: Boolean = false,
)
