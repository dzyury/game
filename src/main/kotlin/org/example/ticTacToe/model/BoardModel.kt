package org.example.ticTacToe.model

import org.example.ticTacToe.model.BoardType.*
import javax.swing.JComponent
import javax.swing.JOptionPane
import javax.swing.SwingUtilities
import kotlin.system.exitProcess


class BoardModel(private val n: Int) {
    private var isZero = false
    private var steps = 0

    var view: JComponent? = null
    val board = Array(n) { IntArray(n) }

    fun start() {
        isZero = false
        steps = 0
        board.forEach { it.fill(0) }
    }

    fun dropHintSymbol(x: Int, y: Int) {
        if (board[x][y] > HINT) board[x][y] = DEFAULT.ordinal
        view?.repaint()
    }

    fun hintSymbol(x: Int, y: Int): Boolean {
        if (board[x][y] != DEFAULT.ordinal) return false

        val hint = (if (isZero) ZERO else CROSS).ordinal
        board[x][y] = HINT + hint
        view?.repaint()
        return true
    }

    fun setSymbol(x: Int, y: Int) {
        val symbol = (if (isZero) ZERO else CROSS).ordinal
        if (board[x][y] != HINT + symbol) return

        board[x][y] = symbol
        steps++

        turnOver()
        view?.repaint()

        if (check(x, y)) {
            val frame = SwingUtilities.windowForComponent(view!!)
            val winner = if (symbol == ZERO.ordinal) "O" else "X"

            val options = arrayOf("Yes, please", "No, thanks")
            val answer = JOptionPane.showOptionDialog(
                frame,
                "<html>$winner won!<br>Would you like to play again?",
                "Game over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            )

            if (answer != 0) exitProcess(1)
            start()
            view?.repaint()
        }
        if (steps >= n * n) {
            val frame = SwingUtilities.windowForComponent(view!!)

            val options = arrayOf("Yes, please", "No, thanks")
            val answer = JOptionPane.showOptionDialog(
                frame,
                "<html>Draw!<br>Would you like to play again?",
                "Game over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            )

            if (answer != 0) exitProcess(1)
            start()
            view?.repaint()
        }
    }

    private fun check(x: Int, y: Int): Boolean {
        val x1 = (3 + x - 1) % 3
        val y1 = (3 + y - 1) % 3
        val x2 = (3 + x + 1) % 3
        val y2 = (3 + y + 1) % 3

        return (((board[x1][y] == board[x][y] && board[x][y] == board[x2][y]) ||
                (board[x][y1] == board[x][y] && board[x][y] == board[x][y2]) ||
                (x == y && board[x1][y1] == board[x][y] && board[x][y] == board[x2][y2]) ||
                (x == 2 - y && board[x1][y2] == board[x][y] && board[x][y] == board[x2][y1])))
    }

    private fun turnOver() {
        isZero = !isZero
    }
}
