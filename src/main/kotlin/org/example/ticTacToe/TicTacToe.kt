package org.example.ticTacToe


import java.awt.GridLayout
import javax.swing.JFrame

class TicTacToe : JFrame("TicTacToe") {
    init {
        val cp = contentPane
        cp.layout = GridLayout()
        val board = BoardFactory(3).createBoard()
        cp.add(board)

        iconImage = PawnRenderer.zero
        setSize(870, 670)
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
    }
}