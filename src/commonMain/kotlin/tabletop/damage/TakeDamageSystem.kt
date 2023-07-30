/* (C)2023 */
package tabletop.damage

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World
import tabletop.ecs.Health

class TakeDamageSystem : IteratingSystem(
    World.family { all(Health, Damage).none(Down, Dead) },
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
