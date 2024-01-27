package org.example

import org.example.icon.Renderer
import org.example.quoridor.Quoridor
import org.example.tictactoe.TicTacToe
import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants.EXIT_ON_CLOSE

fun main() {
    val frame = JFrame("Игра")
    val panel = JPanel()
    addButton(panel, "quoridor.png", Quoridor())
    addButton(panel, "tactictoe.png", TicTacToe())

    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.contentPane = panel
    frame.pack()
    frame.isVisible = true
}

fun addButton(panel: JPanel, path: String, frame: JFrame) {
    val icon = Renderer.load(path)
    val image = icon.getScaledInstance(128, 128, Image.SCALE_FAST)
    val button = JButton(ImageIcon(image))
    panel.add(button)
    button.addActionListener {
        frame.isVisible = true
    }
}

