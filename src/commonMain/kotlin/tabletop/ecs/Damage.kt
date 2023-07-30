/* (C)2023 */
package tabletop.ecs

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import kotlinx.serialization.Serializable

@Serializable
data class Damage(
    val amount: UInt,
) : Component<Damage> {
    override fun type(): ComponentType<Damage> = Damage

    companion object : ComponentType<Damage>()
}

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

class TakeDamageSystem : IteratingSystem(
    family { all(Health, Damage).none(Down, Dead) },
) {
    override fun onTickEntity(entity: Entity) {
        val health = entity[Health]
        val damage = entity[Damage]

        val newHp = health.current - damage.amount.toInt()
        entity.configure {
            it[Health].current = newHp
            it -= Damage
        }
    }
}
