/* (C)2023 */
package tabletop.stats

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

sealed interface Ability {
    val value: UInt
}

@Serializable
@JvmInline
value class Strength(override val value: UInt) : Ability

@Serializable
@JvmInline
value class Dexterity(override val value: UInt) : Ability

@Serializable
@JvmInline
value class Constitution(override val value: UInt) : Ability

@Serializable
@JvmInline
value class Intelligence(override val value: UInt) : Ability

@Serializable
@JvmInline
value class Wisdom(override val value: UInt) : Ability

@Serializable
@JvmInline
value class Charisma(override val value: UInt) : Ability

/**
 * To determine an ability modifier without consulting the table,
 * subtract 10 from the ability score and then divide the total by 2 (round down).
 * See: https://roll20.net/compendium/dnd5e/Ability%20Scores#content
 */
fun Ability.modifier(): Int = (value.toInt() - 10).floorDiv(2)
