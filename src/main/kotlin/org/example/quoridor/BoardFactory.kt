package org.example.quoridor

import org.example.quoridor.model.BoardModel
import java.awt.GridBagConstraints
import java.awt.GridBagConstraints.BOTH
import java.awt.GridBagLayout
import javax.swing.JComponent

private const val THIN = 1.0
private const val THICK = 4.0

class BoardFactory(n: Int) {
    private val size = n * 2 - 1
    private val c = GridBagConstraints().also { it.fill = BOTH }

    private var x = 0
    private var y = 0
    private var isXThick = true
    private var isYThick = true
    private val model = BoardModel(size)

    private fun nextLine() {
        isXThick = true
        isYThick = !isYThick

        x = 0
        y++
    }

    fun createBoard(whitePanel: PlayerPane, blackPanel: PlayerPane): JComponent {
        val board = Board(model)
        model.view = board
        model.whitePanel = whitePanel
        model.blackPanel = blackPanel
        board.layout = GridBagLayout()

        for (i in 0..<size) {
            for (j in 0..<size) {
                board.add(createComponent(), c)
            }
            nextLine()
        }
        model.start()
        return board
    }

    private fun createComponent(): JComponent {
        val comp = if (isXThick && isYThick) Field(model, x, y) else when {
            isXThick -> HorizontalBarrier(model, x, y)
            isYThick -> VerticalBarrier(model, x, y)
            else -> Barrier(model, x, y)
        }

        c.gridx = x++
        c.gridy = y
        c.weightx = if (isXThick) THICK else THIN
        c.weighty = if (isYThick) THICK else THIN
        isXThick = !isXThick

        return comp
    }
}