/* (C)2023 */
package tabletop

import kotlinx.serialization.Serializable
import tabletop.io.DirPath

@Serializable
data class Config(
    val dataDir: DirPath,
)
