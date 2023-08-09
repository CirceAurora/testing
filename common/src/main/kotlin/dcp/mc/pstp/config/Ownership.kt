package dcp.mc.pstp.config
@JvmRecord
data class Ownership(
    val removal: Boolean = true,
    val adding: Boolean = true,
    val transfer: Boolean = false,
    val defaultShears: Boolean = true,
    val extraShears: List<String> = emptyList(),
    val defaultStick: Boolean = true,
    val extraSticks: List<String> = emptyList(),
)