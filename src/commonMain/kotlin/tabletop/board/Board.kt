/* (C)2023 */
package tabletop.board

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

@Serializable
data class Board(
    val width: UInt,
    val height: UInt,
    val coordinates: Map<Coordinate, Token>,
) : Component<Board> {

    override fun type(): ComponentType<Board> = Board

    companion object : ComponentType<Board>()
}

data class BoardBuilder(
    val width: UInt,
    val height: UInt,
) {
    private val tokens: MutableMap<Coordinate, Token> = mutableMapOf()

    operator fun plus(token: Token): BoardBuilder {
        val coordinate = token.coordinate
        tokens[coordinate] = token
        return this
    }
    operator fun plus(tokens: List<Token>): BoardBuilder {
        tokens.forEach {
            addToken(it)
        }
        return this
    }

    fun addToken(token: Token): BoardBuilder = plus(token)

    fun build(): Board {
        return Board(
            width = width,
            height = height,
            coordinates = tokens,
        )
    }
}

@Serializable
data class Coordinate(
    val x: Int,
    val y: Int,
)

fun allPositions(width: UInt, height: UInt): Sequence<Coordinate> {
    return (height - 1u downTo 0u).asSequence().flatMap { y ->
        (0u..<width).asSequence().map { x ->
            Coordinate(x.toInt(), y.toInt())
        }
    }
}
