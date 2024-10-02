package gg.rsmod.plugins.content.mechanics.multi

data class MultiAreaData(
    val regions: List<Int>,
    val chunks: List<ChunkData>,
)

data class ChunkData(
    val x: Int,
    val z: Int,
)
