/* (C)2023 */
package tabletop

import kotlinx.serialization.Serializable

@Serializable
data class StatBlock(
    val maxHitPoints: UInt,
    val armorClass: UInt,
)
