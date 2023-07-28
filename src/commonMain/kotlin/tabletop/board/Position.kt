/* (C)2023 */
package tabletop.board

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

/**
 * A position on the board. Start with the origin (0, 0, 0) at the bottom southwest of the board
 */
@Serializable
data class Position(
    val x: Int,
    val y: Int,
    val z: Int,
) : Component<Position> {
    override fun type(): ComponentType<Position> = Position

    companion object : ComponentType<Position>()
}
