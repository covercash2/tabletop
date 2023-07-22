package tabletop

import kotlinx.serialization.Serializable
import tabletop.io.DirPath
import tabletop.io.Directory

@Serializable
data class Config(
    val dataDir: DirPath,
)