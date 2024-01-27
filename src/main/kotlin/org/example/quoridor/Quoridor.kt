package org.example.quoridor

import org.example.icon.Renderer
import java.awt.BorderLayout
import java.awt.BorderLayout.EAST
import java.awt.BorderLayout.WEST
import javax.swing.JFrame

class Quoridor : JFrame("Quoridor") {
    init {
        val cp = contentPane
        cp.layout = BorderLayout()
        val white = PlayerPane("White", Renderer.white)
        cp.add(white, WEST)
        val black = PlayerPane("Black", Renderer.black)
        cp.add(black, EAST)

        val board = BoardFactory(9).createBoard(white, black)
        cp.add(board, BorderLayout.CENTER)

        iconImage = Renderer.white
        setSize(870, 670)
    }
}