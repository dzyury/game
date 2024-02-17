package org.example.common

import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

class Dialog(
    private val board: JComponent,
    private val model: Startable
) {
    private val frame = SwingUtilities.windowForComponent(board) as JFrame

    fun show(message: String) {
        val options = arrayOf("Yes, please", "No, thanks")
        val answer = JOptionPane.showOptionDialog(
            frame,
            message,
            "Game over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        )

        model.start()
        board.repaint()
        if (answer != 0) frame.isVisible = false
    }
}