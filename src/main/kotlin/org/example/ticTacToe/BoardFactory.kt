package org.example.ticTacToe

import org.example.ticTacToe.model.BoardModel
import java.awt.GridBagConstraints
import java.awt.GridLayout
import javax.swing.JComponent

class BoardFactory(n: Int) {
    private val size = n
    private val c = GridBagConstraints().also { it.fill = GridBagConstraints.BOTH }

    private var x = 0
    private var y = 0
    private val model = BoardModel(size)


    fun createBoard(): JComponent {
        val board = Board(model)
        model.view = board
        board.layout = GridLayout(size, size)

        for (i in 0..<size) {
            for (j in 0..<size) {
                y = i
                x = j
                board.add(createComponent(), c)
            }
        }
        model.start()
        return board
    }

    private fun createComponent(): JComponent {
        return Field(model, x, y)
    }
}