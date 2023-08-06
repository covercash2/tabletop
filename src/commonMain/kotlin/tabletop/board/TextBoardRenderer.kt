/* (C)2023 */
package tabletop.board

class TextBoardRenderer {
    fun render(
        board: Board,
    ) {
    }
}

fun buildBoardAsText(
    board: Board,
): String {
    return allPositions(board.width, board.height).map { coord ->
        buildString {
            if (coord.x == 0) append('\n')
            if (board.coordinates[coord] == null) {
                append(" ")
            } else {
                append("X")
            }
        }
    }.joinToString("|", postfix = "|")
}
