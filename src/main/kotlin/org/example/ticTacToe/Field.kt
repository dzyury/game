package org.example.ticTacToe

import org.example.ticTacToe.model.BoardModel
import org.example.ticTacToe.model.BoardType.ZERO
import org.example.ticTacToe.model.BoardType.CROSS
import org.example.ticTacToe.model.HINT
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.JPanel

private val BACKGROUND = Color(255, 255, 255)

class Field(private val model: BoardModel, private val x: Int, private val y: Int) : JPanel() {
    init {
        background = BACKGROUND
        border = BorderFactory.createLineBorder(Color.BLACK)

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                model.setPawn(x, y)
            }

            override fun mouseEntered(e: MouseEvent?) {
                model.hintPawn(x, y)
            }

            override fun mouseExited(e: MouseEvent?) {
                model.dropHintPawn(x, y)
            }
        })
    }
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        val g2d = g as Graphics2D
        when (model.board[x][y]) {
            ZERO.ordinal -> PawnRenderer.showWhite(g2d, width, height, false)
            CROSS.ordinal -> PawnRenderer.showBlack(g2d, width, height, false)
            HINT + ZERO.ordinal -> PawnRenderer.showWhite(g2d, width, height, true)
            HINT + CROSS.ordinal -> PawnRenderer.showBlack(g2d, width, height, true)
        }
    }

}

