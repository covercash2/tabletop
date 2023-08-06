/* (C)2023 */
package tabletop.equipment

import tabletop.damage.TypedDamage
import tabletop.stats.Proficiency

data class Weapon(
    val damage: TypedDamage,
    val type: WeaponType,
) : Item

sealed interface WeaponType : Proficiency

sealed interface SimpleWeapon : WeaponType
sealed interface MartialWeapon : WeaponType

data object Club : SimpleWeapon
data object Mace : SimpleWeapon

data object ShortSword : MartialWeapon
data object LongSword : MartialWeapon
