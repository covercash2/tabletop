/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

@Serializable
data class StatBlock(
    val strength: Strength,
    val dexterity: Dexterity,
    val constitution: Constitution,
    val intelligence: Intelligence,
    val wisdom: Wisdom,
    val charisma: Charisma,
    val proficiencies: Set<Proficiency> = emptySet(),
) : Component<StatBlock> {
    val all: List<AbilityInterface> = listOf(
        strength,
        dexterity,
        constitution,
        intelligence,
        wisdom,
        charisma,
    )

    fun getValueByAbilityType(abilityType: AbilityType): UInt {
        return all.find { it.abilityType == abilityType }!!.value
    }

    override fun type(): ComponentType<StatBlock> = StatBlock

    companion object : ComponentType<StatBlock>()
}

fun trivialStatBlock() = StatBlock(
    strength = Strength(10u),
    dexterity = Dexterity(10u),
    constitution = Constitution(10u),
    wisdom = Wisdom(10u),
    intelligence = Intelligence(10u),
    charisma = Charisma(10u),
)
