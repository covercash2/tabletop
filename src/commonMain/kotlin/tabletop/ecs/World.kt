/* (C)2023 */
package tabletop.ecs

import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.configureWorld
import tabletop.board.Position

fun getWorld(): World = configureWorld(entityCapacity = 1000) {
    families {
        val positionFamily = family {
            all(Position)
            all(Health, Damage)
        }
    }

    systems {
        add(MoveSystem())
        add(TakeDamageSystem())
    }
}
