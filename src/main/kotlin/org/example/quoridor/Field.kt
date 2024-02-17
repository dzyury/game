package org.example.quoridor

import org.example.icon.Renderer
import org.example.quoridor.model.BoardModel
import org.example.quoridor.model.BoardType.BLACK_PAWN
import org.example.quoridor.model.BoardType.WHITE_PAWN
import org.example.quoridor.model.HINT
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel

private val BACKGROUND = Color(200, 120, 0)

class Field(private val model: BoardModel, private val x: Int, private val y: Int) : JPanel() {
    init {
        background = BACKGROUND

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
        when (model.data[x][y]) {
            WHITE_PAWN.ordinal -> Renderer.showWhite(g2d, width, height, false)
            BLACK_PAWN.ordinal -> Renderer.showBlack(g2d, width, height, false)
            HINT + WHITE_PAWN.ordinal -> Renderer.showWhite(g2d, width, height, true)
            HINT + BLACK_PAWN.ordinal -> Renderer.showBlack(g2d, width, height, true)
        }
    }
}