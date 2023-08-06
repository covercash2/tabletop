/* (C)2023 */
package tabletop.damage

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

@Serializable
data class Damage(
    val damage: List<TypedDamage>,
) : Component<Damage> {
    val amount: UInt by lazy {
        damage.fold(0u) { acc, damage -> acc + damage.amount }
    }

    override fun type(): ComponentType<Damage> = Damage

    companion object : ComponentType<Damage>()
}

@Serializable
data class TypedDamage(
    val amount: UInt,
    val type: DamageType,
)

sealed interface DamageType

data object Bludgeoning : DamageType
data object Piercing : DamageType
data object Slashing : DamageType

data object Acid : DamageType
data object Poison : DamageType

data object Cold : DamageType
data object Fire : DamageType
data object Lightning : DamageType
data object Thunder : DamageType

data object Force : DamageType
data object Psychic : DamageType

data object Necrotic : DamageType
data object Radiant : DamageType

@Serializable
data class Down(
    val saves: UInt,
    val fails: UInt,
) : Component<Down> {
    override fun type(): ComponentType<Down> = Down

    companion object : ComponentType<Down>()
}

@Serializable
class Dead : Component<Dead> {
    override fun type(): ComponentType<Dead> = Dead

    companion object : ComponentType<Dead>()
}
