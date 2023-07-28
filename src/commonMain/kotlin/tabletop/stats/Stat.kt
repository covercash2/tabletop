/* (C)2023 */
package tabletop.stats

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

sealed interface Stat

@Serializable
@JvmInline
value class Strength(val value: UInt) : Stat

@Serializable
@JvmInline
value class Dexterity(val value: UInt) : Stat

@Serializable
@JvmInline
value class Constitution(val value: UInt) : Stat

@Serializable
@JvmInline
value class Intelligence(val value: UInt) : Stat

@Serializable
@JvmInline
value class Wisdom(val value: UInt) : Stat

@Serializable
@JvmInline
value class Charisma(val value: UInt) : Stat
