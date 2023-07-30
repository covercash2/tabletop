/* (C)2023 */
package tabletop.ecs

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import tabletop.board.Position

class MoveComponent : Component<MoveComponent> {
    override fun type(): ComponentType<MoveComponent> = MoveComponent

    companion object : ComponentType<MoveComponent>()
}

class MoveSystem : IteratingSystem(
    family { all(Position) },
) {
    override fun onTickEntity(entity: Entity) {
        val position = entity[Position]
        TODO("Not yet implemented")
    }
}
