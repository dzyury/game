package org.example.tictactoe.model

import org.example.common.Dialog
import org.example.common.Startable
import org.example.tictactoe.Field
import org.example.tictactoe.Glass
import org.example.tictactoe.PlayerPane
import org.example.tictactoe.Winner
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.SwingUtilities

class Model(val me: String) : Startable {
    // Каким цветом играем мы
//    private var isWhite: Boolean = true
//    lateinit var manager: Manager
    lateinit var fields: Array<Array<Field>>
    lateinit var board: JComponent
    lateinit var crossPane: PlayerPane
    lateinit var noughtPane: PlayerPane

    private var isXTurn = true
    private val data = Array(3) { Array(3) { Type.EMPTY } }


    override fun start() {
        isXTurn = false
        crossPane.start()
        noughtPane.start()
        turnOver()
        data.forEach { row -> row.fill(Type.EMPTY) }
        fields.forEach { row ->
            row.forEach { field ->
                field.type = Type.EMPTY
                field.isHint = false
                field.repaint()
            }
        }
        val field = fields[0][0]
        val frame = SwingUtilities.getWindowAncestor(field) as JFrame
        frame.glassPane.isVisible = false
    }

    fun go(x: Int, y: Int) = SwingUtilities.invokeLater {
        if (data[x][y] == Type.EMPTY) {
            data[x][y] = if (isXTurn) Type.CROSS else Type.NOUGHT
            val field = fields[x][y]
            field.type = data[x][y]
            field.isHint = false
            field.repaint()
            turnOver()

            val winner = getWinner()
            val isFine = data.all { row -> row.all { it != Type.EMPTY } }
            if (isFine || winner.type != Type.EMPTY) {
                val frame = SwingUtilities.getWindowAncestor(field) as JFrame
                val glass = (frame.glassPane as? Glass) ?: return@invokeLater

                crossPane.turn(false)
                noughtPane.turn(false)
                glass.winner = winner
                glass.isVisible = true
                glass.repaint()

                val winMessage = if (winner.type != Type.EMPTY) "${winner.type} won" else "Draw"
                val message = "<html>$winMessage<br>Would you like to play again?"
                val dialog = Dialog(board, this)
                dialog.show(message)
            }
        }
    }

    private fun turnOver() {
        isXTurn = !isXTurn
        crossPane.turn(isXTurn)
        noughtPane.turn(!isXTurn)
    }

    private fun getWinner(): Winner {
        return getWinner(Type.CROSS) ?: getWinner(Type.NOUGHT) ?: Winner.EMPTY
    }

    private fun getWinner(type: Type): Winner? {
        val winners = BooleanArray(3) { true }
        for (x in 0..<3) {
            for (y in 0..<3) {
                if (data[x][y] != type) winners[x] = false
            }
        }
        winners.forEachIndexed { idx, winner -> if (winner) return Winner(type, idx, 0, idx, 2) }

        winners.fill(true)
        for (x in 0..<3) {
            for (y in 0..<3) {
                if (data[x][y] != type) winners[y] = false
            }
        }
        winners.forEachIndexed { idx, winner -> if (winner) return Winner(type, 0, idx, 2, idx) }

        var winner1 = true
        for (x in 0..<3) {
            if (data[x][x] != type) winner1 = false
        }
        if (winner1) return Winner(type, 0, 0, 2, 2)

        var winner2 = true
        for (x in 0..<3) {
            if (data[x][2 - x] != type) winner2 = false
        }
        if (winner2) return Winner(type, 2, 0, 0, 2)
        return null
    }

    fun hint(x: Int, y: Int) {
        if (data[x][y] == Type.EMPTY) {
            val field = fields[x][y]
            field.type = if (isXTurn) Type.CROSS else Type.NOUGHT
            field.isHint = true
            field.repaint()
        }
    }

    fun unHint(x: Int, y: Int) {
        if (data[x][y] == Type.EMPTY) {
            val field = fields[x][y]
            field.type = Type.EMPTY
            field.isHint = false
            field.repaint()
        }
    }
}
