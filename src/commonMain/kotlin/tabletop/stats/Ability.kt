/* (C)2023 */
package tabletop.stats

sealed interface Ability : Proficiency

data object Strength : Ability
data object Dexterity : Ability
data object Constitution : Ability
data object Wisdom : Ability
data object Intelligence : Ability
data object Charisma : Ability
