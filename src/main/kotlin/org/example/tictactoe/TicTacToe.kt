package org.example.tictactoe

import org.example.icon.Renderer
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import javax.swing.*

val BACKGROUND = Color(160, 160, 160)
val X_COLOR: Color = Color.BLACK
val HINT_X_COLOR = Color(64, 64, 64)
val O_COLOR: Color = Color.WHITE
val HINT_O_COLOR = Color(224, 224, 224)

enum class Type {
    EMPTY, X, O
}

class TicTacToeModel {
    lateinit var cells: Array<Array<Cell>>
    private var isX = true
    private val board = Array(3) { Array(3) { Type.EMPTY } }

    private fun start() {
        isX = true
        board.forEach { row -> row.fill(Type.EMPTY) }
        cells.forEach { row ->
            row.forEach { cell ->
                cell.type = Type.EMPTY
                cell.isHint = false
                cell.repaint()
            }
        }
        val cell = cells[0][0]
        val frame = SwingUtilities.getWindowAncestor(cell) as JFrame
        frame.glassPane.isVisible = false
    }

    fun go(x: Int, y: Int) {
        if (board[x][y] == Type.EMPTY) {
            board[x][y] = if (isX) Type.X else Type.O
            val cell = cells[x][y]
            cell.type = board[x][y]
            cell.isHint = false
            cell.repaint()
            isX = !isX

            val winner = getWinner()
            val isFine = board.all { row -> row.all { it != Type.EMPTY } }
            if (isFine || winner.type != Type.EMPTY) {
                val frame = SwingUtilities.getWindowAncestor(cell) as JFrame
                val glass = (frame.glassPane as? Glass) ?: return
                glass.winner = winner
                glass.isVisible = true
                glass.repaint()

                val options = arrayOf("Yes, please", "No, thanks")
                val message = if (winner.type != Type.EMPTY) "${winner.type} won" else "Draw"
                val answer = JOptionPane.showOptionDialog(
                    null,
                    "<html>$message<br>Would you like to play again?",
                    "Game over",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
                )

                start()
                if (answer != 0) frame.isVisible = false
            }
        }
    }

    private fun getWinner(): Winner {
        return getWinner(Type.X) ?: getWinner(Type.O) ?: Winner.EMPTY
    }

    private fun getWinner(type: Type): Winner? {
        val winners = BooleanArray(3) { true }
        for (x in 0..<3) {
            for (y in 0..<3) {
                if (board[x][y] != type) winners[x] = false
            }
        }
        winners.forEachIndexed { idx, winner -> if (winner) return Winner(type, idx, 0, idx, 2) }

        winners.fill(true)
        for (x in 0..<3) {
            for (y in 0..<3) {
                if (board[x][y] != type) winners[y] = false
            }
        }
        winners.forEachIndexed { idx, winner -> if (winner) return Winner(type, 0, idx, 2, idx) }

        var winner1 = true
        for (x in 0..<3) {
            if (board[x][x] != type) winner1 = false
        }
        if (winner1) return Winner(type, 0, 0, 2, 2)

        var winner2 = true
        for (x in 0..<3) {
            if (board[x][2 - x] != type) winner2 = false
        }
        if (winner2) return Winner(type, 2, 0, 0, 2)
        return null
    }

    fun hint(x: Int, y: Int) {
        if (board[x][y] == Type.EMPTY) {
            val cell = cells[x][y]
            cell.type = if (isX) Type.X else Type.O
            cell.isHint = true
            cell.repaint()
        }
    }

    fun unhint(x: Int, y: Int) {
        if (board[x][y] == Type.EMPTY) {
            val cell = cells[x][y]
            cell.type = Type.EMPTY
            cell.isHint = false
            cell.repaint()
        }
    }
}

class Cell(var model: TicTacToeModel, val xm: Int, val ym: Int) : JPanel() {
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
                model.unhint(xm, ym)
            }
        })
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        val g2d = g as Graphics2D
        g2d.stroke = BasicStroke(minOf(size.height, size.width) / 10f)
        when (type) {
            Type.X -> {
                g2d.color = if (isHint) HINT_X_COLOR else X_COLOR
                val x = size.width * 0.3
                val y = size.height * 0.3
                val width = size.width * 0.4
                val height = size.height * 0.4
                g2d.drawLine(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt())
                g2d.drawLine((x + width).toInt(), y.toInt(), x.toInt(), (y + height).toInt())
            }

            Type.O -> {
                g2d.color = if (isHint) HINT_O_COLOR else O_COLOR
                val x = size.width * 0.3
                val y = size.height * 0.3
                val width = size.width * 0.4
                val height = size.height * 0.4
                g2d.drawOval(x.toInt(), y.toInt(), width.toInt(), height.toInt())
            }

            else -> Unit
        }
    }
}

data class Winner(val type: Type, val x1: Int = 0, val y1: Int = 0, val x2: Int = 0, val y2: Int = 0) {
    companion object {
        val EMPTY = Winner(Type.EMPTY)
    }
}

class Glass(private val cells: Array<Array<Cell>>, var winner: Winner) : JComponent() {
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

        val pic = if (winner.type == Type.X) "btext.png" else "wtext.png"
        val image = Renderer.load(pic) as BufferedImage
        val (point1, point2) = if (winner.y1 == winner.y2) {
            val cell1 = cells[winner.x1][winner.y1]
            val start = Point(cell1.size.width / 2, (cell1.size.height * 0.2).toInt())
            val point1 = SwingUtilities.convertPoint(cell1, start, this)

            val cell2 = cells[winner.x2][winner.y2]
            val end = Point(cell2.size.width / 2, (cell2.size.height * 0.8).toInt())
            val point2 = SwingUtilities.convertPoint(cell2, end, this)
            point1 to point2
        } else if (winner.x1 == winner.x2) {
            val cell1 = cells[winner.x1][winner.y1]
            val start = Point((cell1.size.width * 0.2).toInt(), cell1.size.height / 2)
            val point1 = SwingUtilities.convertPoint(cell1, start, this)

            val cell2 = cells[winner.x2][winner.y2]
            val end = Point((cell2.size.width * 0.8).toInt(), cell2.size.height / 2)
            val point2 = SwingUtilities.convertPoint(cell2, end, this)
            point1 to point2
        } else if (winner.x1 == 0) {
            val cell1 = cells[0][winner.y1]
            val start = Point((cell1.size.width * 0.2).toInt(), (cell1.size.height * 0.2).toInt())
            val point1 = SwingUtilities.convertPoint(cell1, start, this)

            val cell2 = cells[winner.x2][winner.y2]
            val end = Point((cell2.size.width * 0.8).toInt(), (cell2.size.height * 0.8).toInt())
            val point2 = SwingUtilities.convertPoint(cell2, end, this)
            point1 to point2
        } else {
            val cell1 = cells[winner.x1][winner.y1]
            val start = Point((cell1.size.width * 0.2).toInt(), (cell1.size.height * 0.8).toInt())
            val point1 = SwingUtilities.convertPoint(cell1, start, this)

            val cell2 = cells[winner.x2][winner.y2]
            val end = Point((cell2.size.width * 0.8).toInt(), (cell2.size.height * 0.2).toInt())
            val point2 = SwingUtilities.convertPoint(cell2, end, this)
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

class Field : JPanel() {
    val model: TicTacToeModel
    val cells: Array<Array<Cell>>

    init {
        layout = GridLayout(3, 3)

        model = TicTacToeModel()
        cells = Array(3) { x -> Array(3) { y -> Cell(model, x, y) } }
        cells.forEach { row -> row.forEach { add(it) } }
        model.cells = cells
    }
}

class TicTacToe : JFrame() {
    init {
        val field = Field()
        contentPane = field
        glassPane = Glass(field.cells, Winner.EMPTY)
        size = Dimension(400, 400)
    }
}

fun main() {
    TicTacToe()
}