package org.example.ticTacToe.model

import org.example.ticTacToe.model.BoardType.*
import java.awt.Point
import java.awt.event.KeyEvent
import javax.swing.JComponent
import javax.swing.JOptionPane
import javax.swing.SwingUtilities
import kotlin.system.exitProcess


class BoardModel(private val n: Int) {
    var view: JComponent? = null
    val board = Array(n) { IntArray(n)}
    var was_turn = 0;
    private var turn = false;

    private fun check(x : Int, y: Int): Boolean{
        val x1 = (3 + x - 1) % 3
        val y1 = (3 + y - 1) % 3
        val x2 = (3 + x + 1) % 3
        val y2 = (3 + y + 1) % 3

        if (((board[x1][y] == board[x][y]) and (board[x2][y] == board[x][y])) or
            ((board[x][y1] == board[x][y]) and (board[x][y] == board[x][y2]))){
            return true
        }
        if ((x == y) and (board[x1][y1] == board[x][y]) and (board[x2][y2] == board[x][y])){
            return true
        }

        if ((x + y == 2) and (board[x1][y2] == board[x][y]) and (board[x2][y1] == board[x][y])){
            return true
        }
        return false
    }

    private fun turnOver(){
        turn = !turn;
    }

    fun start() {
        turn = false;
        was_turn = 0;
        board.forEach { it.fill(0) }
    }

    fun hintPawn(x: Int, y: Int): Boolean {
        if (board[x][y] != BoardType.DEFAULT.ordinal) return true

        val hint = (if (turn) BoardType.ZERO else BoardType.CROSS).ordinal
        board[x][y] = HINT + hint
        view?.repaint()
        return true
    }

    fun dropHintPawn(x: Int, y: Int) {
        if (board[x][y] > HINT) board[x][y] = BoardType.DEFAULT.ordinal
        view?.repaint()
    }

    fun setPawn(x: Int, y: Int) {
        val pawn = (if (turn) BoardType.ZERO else BoardType.CROSS).ordinal
        if (board[x][y] != HINT + pawn) return
        board[x][y] = pawn
        was_turn += 1
        turnOver()
        view?.repaint()
        if (check(x, y)) {
            val frame = SwingUtilities.windowForComponent(view!!)
            val winner = if (pawn == BoardType.ZERO.ordinal) "O" else "X"
            val options = arrayOf("Yes, please", "No, thanks")
            val answer = JOptionPane.showOptionDialog(
                frame,
                "<html>$winner won<br>Would you like to play again?",
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

        if (was_turn >= n * n){
            val frame = SwingUtilities.windowForComponent(view!!)
            val options = arrayOf("Yes, please", "No, thanks")
            val answer = JOptionPane.showOptionDialog(
                frame,
                "<html>DRAW<br>Would you like to play again?",
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
}