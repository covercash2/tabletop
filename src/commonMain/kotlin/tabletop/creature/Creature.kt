/* (C)2023 */
package tabletop.creature

import kotlinx.serialization.Serializable
import tabletop.equipment.Equipment
import tabletop.stats.Health
import tabletop.stats.StatBlock

/**
 * https://rpg.stackexchange.com/questions/47254/what-is-the-definition-of-creature-and-is-it-used-consistently
 */
sealed interface Creature {
    val health: Health
    val stats: StatBlock
}

@Serializable
data class Character(
    val name: Name,
    val equipment: Equipment,
    override val health: Health,
    override val stats: StatBlock,
) : Creature

@Serializable
data class Humanoid(
    override val health: Health,
    override val stats: StatBlock,
) : Creature

@Serializable
data class Monster(
    override val health: Health,
    override val stats: StatBlock,
) : Creature
