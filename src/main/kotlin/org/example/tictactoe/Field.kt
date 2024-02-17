package org.example.tictactoe

import org.example.icon.Renderer
import org.example.tictactoe.model.Model
import org.example.tictactoe.model.Type
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.JPanel

private val BACKGROUND = Color(160, 160, 160)

class Field(var model: Model, val xm: Int, val ym: Int) : JPanel() {
    var type = Type.EMPTY
    var isHint = false

    init {
        background = BACKGROUND
        border = BorderFactory.createLineBorder(Color.BLACK)

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                model.go(xm, ym)
            }

            override fun mouseEntered(e: MouseEvent?) {
                model.hint(xm, ym)
            }

            override fun mouseExited(e: MouseEvent?) {
                model.unHint(xm, ym)
            }
        })
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        val g2d = g as Graphics2D
        when (type) {
            Type.CROSS -> Renderer.showCross(g2d, width, height, isHint)
            Type.NOUGHT -> Renderer.showNought(g2d, width, height, isHint)
            else -> Unit
        }
    }
}
