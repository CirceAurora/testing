package dcp.mc.pstp.config

@JvmRecord
data class Revival(
    val enabled: Boolean = true,
    val named: Boolean = true,
    val blocks: List<String> = listOf("minecraft:copper_block"),
    val blacklist: List<String> = emptyList(),
    val whitelist: List<String> = emptyList(),
)