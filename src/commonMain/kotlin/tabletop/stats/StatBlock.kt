/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

@Serializable
data class StatBlock(
    val level: UInt,
    val strength: UInt,
    val dexterity: UInt,
    val constitution: UInt,
    val intelligence: UInt,
    val wisdom: UInt,
    val charisma: UInt,
    val proficiencies: Set<Proficiency> = emptySet(),
) : Component<StatBlock> {
    val all: Map<Ability, UInt> = mapOf(
        Strength to strength,
        Dexterity to dexterity,
        Constitution to constitution,
        Intelligence to intelligence,
        Wisdom to wisdom,
        Charisma to charisma,
    )

    val baseArmorClass: UInt by lazy {
        (10 + dexterity.abilityModifier()).toUInt()
    }

    val baseProficiencyBonus: UInt by lazy {
        level.plus(1u).floorDiv(4u).plus(2u)
    }

    fun getModifierFor(ability: Ability): Int {
        return all[ability]!!.abilityModifier()
    }

    override fun type(): ComponentType<StatBlock> = StatBlock

    companion object : ComponentType<StatBlock>()
}

fun trivialStatBlock(
    proficiencies: Set<Proficiency> = emptySet(),
) = StatBlock(
    level = 1u,
    strength = 10u,
    dexterity = 10u,
    constitution = 10u,
    wisdom = 10u,
    intelligence = 10u,
    charisma = 10u,
    proficiencies = proficiencies,
)

/**
 * To determine an ability modifier without consulting the table,
 * subtract 10 from the ability score and then divide the total by 2 (round down).
 * See: https://roll20.net/compendium/dnd5e/Ability%20Scores#content
 */
fun UInt.abilityModifier(): Int = (toInt() - 10).floorDiv(2)
