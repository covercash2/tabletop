/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.componentTypeOf
import kotlinx.serialization.Serializable

@Serializable
sealed class Ability : Component<Ability> {
    abstract val value: UInt
    abstract val type: AbilityType

    override fun type(): ComponentType<Ability> = when (type) {
        AbilityType.Strength -> Strength
        AbilityType.Dexterity -> Dexterity
        AbilityType.Constitution -> Constitution
        AbilityType.Wisdom -> Wisdom
        AbilityType.Intelligence -> Intelligence
        AbilityType.Charisma -> Charisma
    }

    companion object {
        val Strength = componentTypeOf<Ability>()
        val Dexterity = componentTypeOf<Ability>()
        val Constitution = componentTypeOf<Ability>()
        val Wisdom = componentTypeOf<Ability>()
        val Intelligence = componentTypeOf<Ability>()
        val Charisma = componentTypeOf<Ability>()
    }
}

@Serializable
data class Strength(override val value: UInt) : Ability() {
    override val type: AbilityType
        get() = AbilityType.Strength
}

@Serializable
data class Dexterity(override val value: UInt) : Ability() {
    override val type: AbilityType
        get() = AbilityType.Dexterity
}

@Serializable
data class Constitution(override val value: UInt) : Ability() {
    override val type: AbilityType
        get() = AbilityType.Constitution
}

@Serializable
data class Intelligence(override val value: UInt) : Ability() {
    override val type: AbilityType
        get() = AbilityType.Intelligence
}

@Serializable
data class Wisdom(override val value: UInt) : Ability() {
    override val type: AbilityType
        get() = AbilityType.Wisdom
}

@Serializable
data class Charisma(override val value: UInt) : Ability() {
    override val type: AbilityType
        get() = AbilityType.Charisma
}

/**
 * To determine an ability modifier without consulting the table,
 * subtract 10 from the ability score and then divide the total by 2 (round down).
 * See: https://roll20.net/compendium/dnd5e/Ability%20Scores#content
 */
fun Ability.modifier(): Int = (value.toInt() - 10).floorDiv(2)
