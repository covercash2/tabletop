/* (C)2023 */
package tabletop

import kotlinx.serialization.Serializable
import tabletop.stats.StatBlock

@Serializable
data class Player(
    val name: String,
    val statBlock: StatBlock,
)
