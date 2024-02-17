package org.example.quoridor

import org.example.quoridor.model.BoardModel
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComponent

class HalfBarrier(private val model: BoardModel, vararg points: Point) : JComponent() {
    private fun fix(points: Array<out Point>) {
        when {
            points[0].x < 0 -> points.forEach { it.x += 2 }
            points[0].y < 0 -> points.forEach { it.y += 2 }
            points[2].x > model.data.size -> points.forEach { it.x -= 2 }
            points[2].y > model.data.size -> points.forEach { it.y -= 2 }
        }
    }

    init {
//        border = BorderFactory.createLineBorder(Color.BLUE, 2)
        fix(points)
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                model.setFence(*points)
            }

            override fun mouseEntered(e: MouseEvent?) {
                model.hintFence(*points)
            }

            override fun mouseExited(e: MouseEvent?) {
                model.dropHintFence(*points)
            }
        })

    }
}