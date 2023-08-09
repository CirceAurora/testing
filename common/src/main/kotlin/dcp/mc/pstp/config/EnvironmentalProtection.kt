package dcp.mc.pstp.config
@JvmRecord
data class EnvironmentalProtection(
    val explosions: Boolean = false,
    val bushes: Boolean = false,
    val fires: Boolean = false,
    val freezing: Boolean = false,
    val drowning: Boolean = false,
)