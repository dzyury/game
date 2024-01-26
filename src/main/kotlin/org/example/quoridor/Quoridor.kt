package org.example.quoridor

import java.awt.BorderLayout
import java.awt.BorderLayout.EAST
import java.awt.BorderLayout.WEST
import javax.swing.JFrame

class Quoridor : JFrame("Quoridor") {
    init {
        val cp = contentPane
        cp.layout = BorderLayout()
        val white = PlayerPane("White", PawnRenderer.white)
        cp.add(white, WEST)
        val black = PlayerPane("Black", PawnRenderer.black)
        cp.add(black, EAST)

        val board = BoardFactory(9).createBoard(white, black)
        cp.add(board, BorderLayout.CENTER)

        iconImage = PawnRenderer.white
        setSize(870, 670)
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
    }
}