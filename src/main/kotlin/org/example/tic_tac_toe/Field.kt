package org.example.tic_tac_toe

import org.example.tic_tac_toe.model.BoardModel
import org.example.tic_tac_toe.model.BoardType
import org.example.tic_tac_toe.model.HINT
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.JPanel

private val BACKGROUND = Color(245, 245, 245)

class Field(private val model: BoardModel, private val x: Int, private val y: Int) : JPanel() {
    init {
        background = BACKGROUND
        border = BorderFactory.createLineBorder(Color.BLACK)

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                model.setSymbol(x, y)
            }

            override fun mouseEntered(e: MouseEvent?) {
                model.hintSymbol(x, y)
            }

            override fun mouseExited(e: MouseEvent?) {
                model.dropHintSymbol(x, y)
            }
        })
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        val g2d = g as Graphics2D
        when (model.board[x][y]) {
            BoardType.ZERO.ordinal -> SymbolRenderer.showZero(g2d, width, height, false)
            BoardType.CROSS.ordinal -> SymbolRenderer.showCross(g2d, width, height, false)
            HINT + BoardType.ZERO.ordinal -> SymbolRenderer.showZero(g2d, width, height, true)
            HINT + BoardType.CROSS.ordinal -> SymbolRenderer.showCross(g2d, width, height, true)
        }
    }
}