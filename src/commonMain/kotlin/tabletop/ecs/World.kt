/* (C)2023 */
package tabletop.ecs

import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.configureWorld
import tabletop.board.Position
import tabletop.damage.Damage
import tabletop.damage.Dead
import tabletop.damage.Down
import tabletop.damage.TakeDamageSystem

fun getWorld(): World = configureWorld(entityCapacity = 1000) {
    families {
        val positionFamily = family {
            all(Position)
        }
        val healthFamily = family {
            all(Health, Damage).none(Dead, Down)
        }
    }

    systems {
        add(MoveSystem())
        add(TakeDamageSystem())
    }
}
