/* (C)2023 */
package tabletop.board

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

@Serializable
data class Tokens(
    val tokens: List<Token>,
) : Component<Tokens> {
    override fun type(): ComponentType<Tokens> = Tokens

    companion object : ComponentType<Tokens>()
}

@Serializable
data class Token(
    val position: Position,
) : Component<Token> {
    val coordinate: Coordinate by lazy {
        Coordinate(position.x, position.y)
    }

    override fun type(): ComponentType<Token> = Token

    companion object : ComponentType<Token>()
}
