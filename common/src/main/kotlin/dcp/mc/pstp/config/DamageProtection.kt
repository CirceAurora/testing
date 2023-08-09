package dcp.mc.pstp.config
@JvmRecord
data class DamageProtection(
    val projectiles: Boolean = true,
    val sweeping: Boolean = true,
    val direct: Boolean = true,
    val explosions: Boolean = true,
    val vanillaTeams: Boolean = false,
)