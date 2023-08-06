/* (C)2023 */
package tabletop.ecs

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable
import tabletop.damage.Damage

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
