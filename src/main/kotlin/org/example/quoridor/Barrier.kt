package org.example.quoridor

import org.example.quoridor.model.BoardModel
import org.example.quoridor.model.BoardType
import org.example.quoridor.model.HINT
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel

private val WHITE_BARRIER = Color.WHITE
private val BLACK_BARRIER = Color.BLACK
private val BACKGROUND = Color(160, 92, 0)

open class Barrier(private val model: BoardModel, private val x: Int, private val y: Int) : JPanel() {
    init {
        minimumSize = Dimension(0, 0)
        preferredSize = Dimension(0, 0)
    }

    override fun paintComponent(g: Graphics?) {
        background = when (model.board[x][y]) {
            BoardType.WHITE_FENCE.ordinal -> WHITE_BARRIER
            BoardType.BLACK_FENCE.ordinal -> BLACK_BARRIER
            HINT + BoardType.WHITE_FENCE.ordinal -> WHITE_BARRIER
            HINT + BoardType.BLACK_FENCE.ordinal -> BLACK_BARRIER
            else -> BACKGROUND
        }

        super.paintComponent(g)
    }
}