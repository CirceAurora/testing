package dcp.mc.pstp.config
@JvmRecord
data class ProtectedEntities(
    val tamable: Boolean = true,
    val fox: Boolean = true,
    val horses: Boolean = true,
    val players: Boolean = false,
    val blacklist: List<String> = emptyList()
)