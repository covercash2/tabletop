/* (C)2023 */
package tabletop.stats

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

sealed interface Ability

@Serializable
@JvmInline
value class Acrobatics(val value: UInt) : Ability

@Serializable
@JvmInline
value class AnimalHandling(val value: UInt) : Ability

@Serializable
@JvmInline
value class Arcana(val value: UInt) : Ability

@Serializable
@JvmInline
value class Athletics(val value: UInt) : Ability

@Serializable
@JvmInline
value class Deception(val value: UInt) : Ability

@Serializable
@JvmInline
value class History(val value: UInt) : Ability

@Serializable
@JvmInline
value class Insight(val value: UInt) : Ability

@Serializable
@JvmInline
value class Intimidation(val value: UInt) : Ability

@Serializable
@JvmInline
value class Investigation(val value: UInt) : Ability

@Serializable
@JvmInline
value class Medicine(val value: UInt) : Ability

@Serializable
@JvmInline
value class Nature(val value: UInt) : Ability

@Serializable
@JvmInline
value class Perception(val value: UInt) : Ability

@Serializable
@JvmInline
value class Performance(val value: UInt) : Ability

@Serializable
@JvmInline
value class Persuasion(val value: UInt) : Ability

@Serializable
@JvmInline
value class Religion(val value: UInt) : Ability

@Serializable
@JvmInline
value class SleightOfHand(val value: UInt) : Ability

@Serializable
@JvmInline
value class Stealth(val value: UInt) : Ability

@Serializable
@JvmInline
value class Survival(val value: UInt) : Ability
