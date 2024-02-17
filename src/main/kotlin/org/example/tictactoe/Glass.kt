package org.example.tictactoe

import org.example.icon.Renderer
import org.example.tictactoe.model.Type
import java.awt.*
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import javax.swing.JComponent
import javax.swing.SwingUtilities

class Glass(private val fields: Array<Array<Field>>, var winner: Winner) : JComponent() {
    override fun paintComponent(g: Graphics?) {
        val g2d = g as Graphics2D
        if (winner.type == Type.EMPTY) {
            val message = "Draw"
            g2d.color = Color.GREEN
            val fontSize = size.width / 3f
            g2d.font = g2d.font.deriveFont(Font.BOLD, fontSize)
            g2d.drawString(
                message,
                (size.width - g2d.fontMetrics.stringWidth(message)) / 2f,
                (size.height + fontSize / 2) / 2f
            )
            return
        }

        val pic = if (winner.type == Type.CROSS) "bText.png" else "wText.png"
        val image = Renderer.load(pic) as BufferedImage
        val (point1, point2) = if (winner.y1 == winner.y2) {
            val field1 = fields[winner.x1][winner.y1]
            val start = Point(field1.size.width / 2, (field1.size.height * 0.2).toInt())
            val point1 = SwingUtilities.convertPoint(field1, start, this)

            val field2 = fields[winner.x2][winner.y2]
            val end = Point(field2.size.width / 2, (field2.size.height * 0.8).toInt())
            val point2 = SwingUtilities.convertPoint(field2, end, this)
            point1 to point2
        } else if (winner.x1 == winner.x2) {
            val field1 = fields[winner.x1][winner.y1]
            val start = Point((field1.size.width * 0.2).toInt(), field1.size.height / 2)
            val point1 = SwingUtilities.convertPoint(field1, start, this)

            val field2 = fields[winner.x2][winner.y2]
            val end = Point((field2.size.width * 0.8).toInt(), field2.size.height / 2)
            val point2 = SwingUtilities.convertPoint(field2, end, this)
            point1 to point2
        } else if (winner.x1 == 0) {
            val field1 = fields[0][winner.y1]
            val start = Point((field1.size.width * 0.2).toInt(), (field1.size.height * 0.2).toInt())
            val point1 = SwingUtilities.convertPoint(field1, start, this)

            val field2 = fields[winner.x2][winner.y2]
            val end = Point((field2.size.width * 0.8).toInt(), (field2.size.height * 0.8).toInt())
            val point2 = SwingUtilities.convertPoint(field2, end, this)
            point1 to point2
        } else {
            val field1 = fields[winner.x1][winner.y1]
            val start = Point((field1.size.width * 0.2).toInt(), (field1.size.height * 0.8).toInt())
            val point1 = SwingUtilities.convertPoint(field1, start, this)

            val field2 = fields[winner.x2][winner.y2]
            val end = Point((field2.size.width * 0.8).toInt(), (field2.size.height * 0.2).toInt())
            val point2 = SwingUtilities.convertPoint(field2, end, this)
            point1 to point2
        }

        val texture = TexturePaint(
            image,
            Rectangle2D.Float(0f, 0f, image.width.toFloat(), image.height.toFloat())
        )
        g2d.paint = texture
        g2d.stroke = BasicStroke(minOf(size.width, size.height) / 30f)
        g2d.drawLine(point1.x, point1.y, point2.x, point2.y)
    }
}


