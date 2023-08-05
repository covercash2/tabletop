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
    val proficiencies: Set<AbilityType> = emptySet(),
) : Component<StatBlock> {
    val all: Map<AbilityType, UInt> = mapOf(
        AbilityType.Strength to strength,
        AbilityType.Dexterity to dexterity,
        AbilityType.Constitution to constitution,
        AbilityType.Intelligence to intelligence,
        AbilityType.Wisdom to wisdom,
        AbilityType.Charisma to charisma,
    )

    val baseArmorClass: UInt by lazy {
        (10 + dexterity.abilityModifier()).toUInt()
    }

    val baseProficiencyBonus: UInt by lazy {
        level.plus(1u).floorDiv(4u).plus(2u)
    }

    fun getModifierFor(abilityType: AbilityType): Int {
        return all[abilityType]!!.abilityModifier()
    }

    override fun type(): ComponentType<StatBlock> = StatBlock

    companion object : ComponentType<StatBlock>()
}

fun trivialStatBlock(
    proficiencies: Set<AbilityType> = emptySet(),
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
