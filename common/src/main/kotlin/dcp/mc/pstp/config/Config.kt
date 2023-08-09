package dcp.mc.pstp.config
@JvmRecord
data class Config(
    val damage: DamageProtection = DamageProtection(),
    val environmental: EnvironmentalProtection = EnvironmentalProtection(),
    val effects: EffectsProtection = EffectsProtection(),
    val protected: ProtectedEntities = ProtectedEntities(),
    val revival: Revival = Revival(),
    val ownership: Ownership = Ownership(),
    val whitelist: List<Player> = emptyList(),
    val blacklist: List<Player> = emptyList(),
)