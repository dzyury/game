package org.example.ticTacToe

import java.awt.*
import javax.swing.JFrame

class TicTacToe : JFrame("Tic tac toe") {
    init {
        val pane = contentPane
        pane.layout = GridLayout()

        val board = BoardFactory(3).createBoard()
        pane.add(board)

        iconImage = SymbolRenderer.cross
        setSize(800, 800)
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
    }
}