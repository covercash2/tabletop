/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.componentTypeOf
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
data class Ability(
    val value: UInt,
    val type: AbilityType,
) : Component<Ability> {
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

sealed interface AbilityInterface {
    val value: UInt
    val abilityType: AbilityType
}

@Serializable
@JvmInline
value class Strength(override val value: UInt) : AbilityInterface {
    override val abilityType: AbilityType
        get() = AbilityType.Strength
}

@Serializable
@JvmInline
value class Dexterity(override val value: UInt) : AbilityInterface {
    override val abilityType: AbilityType
        get() = AbilityType.Dexterity
}

@Serializable
@JvmInline
value class Constitution(override val value: UInt) : AbilityInterface {
    override val abilityType: AbilityType
        get() = AbilityType.Constitution
}

@Serializable
@JvmInline
value class Intelligence(override val value: UInt) : AbilityInterface {
    override val abilityType: AbilityType
        get() = AbilityType.Intelligence
}

@Serializable
@JvmInline
value class Wisdom(override val value: UInt) : AbilityInterface {
    override val abilityType: AbilityType
        get() = AbilityType.Wisdom
}

@Serializable
@JvmInline
value class Charisma(override val value: UInt) : AbilityInterface {
    override val abilityType: AbilityType
        get() = AbilityType.Charisma
}

/**
 * To determine an ability modifier without consulting the table,
 * subtract 10 from the ability score and then divide the total by 2 (round down).
 * See: https://roll20.net/compendium/dnd5e/Ability%20Scores#content
 */
fun AbilityInterface.modifier(): Int = (value.toInt() - 10).floorDiv(2)
