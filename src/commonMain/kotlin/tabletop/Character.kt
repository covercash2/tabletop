/* (C)2023 */
package tabletop

import kotlinx.serialization.Serializable
import tabletop.stats.StatBlock

@Serializable
data class Character(
    val name: String,
    val stats: StatBlock,
)
