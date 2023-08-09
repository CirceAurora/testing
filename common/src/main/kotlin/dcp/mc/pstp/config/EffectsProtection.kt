package dcp.mc.pstp.config

@JvmRecord
data class EffectsProtection (
    val enabled: Boolean = true,
    val livingEffects: List<String> = listOf("minecraft:instant_damage", "minecraft:poison"),
    val undeadEffects: List<String> = listOf("minecraft:instant_health", "minecraft:regeneration")
)