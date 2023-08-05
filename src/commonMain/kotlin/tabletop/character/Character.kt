/* (C)2023 */
package tabletop.character

import kotlinx.serialization.Serializable
import tabletop.stats.StatBlock

@Serializable
data class Character(
    val name: Name,
    val stats: StatBlock,
    val equipment: List<String> = emptyList(),
)
