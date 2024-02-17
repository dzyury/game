package org.example.tictactoe

import org.example.common.GameFrame
import org.example.icon.Renderer
import org.example.tictactoe.model.Model
import java.awt.BorderLayout
import java.awt.Dimension

class TicTacToe : GameFrame("Крестики-нолики") {
    val model = Model(me)
//    val manager = Manager(model)

    init {
        val cp = contentPane
        cp.layout = BorderLayout()
        val cross = PlayerPane("Cross", Renderer.cross)
        cp.add(cross, BorderLayout.WEST)
        val nought = PlayerPane("Nought", Renderer.nought)
        cp.add(nought, BorderLayout.EAST)

        val board = Board(model)
        cp.add(board, BorderLayout.CENTER)
        model.board = board
        model.crossPane = cross
        model.noughtPane = nought
        model.start()

        iconImage = Renderer.cross
        glassPane = Glass(board.fields, Winner.EMPTY)
        size = Dimension(600, 500)
    }
}
