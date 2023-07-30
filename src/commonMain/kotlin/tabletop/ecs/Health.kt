/* (C)2023 */
package tabletop.ecs

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import kotlinx.serialization.Serializable

@Serializable
data class Health(
    var current: Int,
    val max: UInt,
) : Component<Health> {
    override fun type(): ComponentType<Health> = Health

    fun takeDamage(damage: Damage): Health {
        current -= damage.amount.toInt()
        return this
    }

    companion object : ComponentType<Health>()
}

class HealthSystem : IteratingSystem(
    family { all(Health) },
) {
    override fun onTickEntity(entity: Entity) {
        val health = entity[Health]
    }
}
