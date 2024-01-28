package org.example

import org.example.icon.Renderer
import org.example.quoridor.Quoridor
import org.example.tictactoe.TicTacToe
import java.awt.Image
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.WindowConstants.EXIT_ON_CLOSE

fun main() {
    val frame = JFrame("Игра")
    val panel = JPanel()
    addButton(panel, "quoridor.png", "Коридор", Quoridor())
    addButton(panel, "tactictoe.png", "Крестики Нолики", TicTacToe())

    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.contentPane = panel
    frame.pack()
    frame.isVisible = true
}

fun addButton(panel: JPanel, path: String, game: String, frame: JFrame) {
    val icon = Renderer.load(path)
    val image = icon.getScaledInstance(128, 128, Image.SCALE_FAST)
    val pane = JPanel()
    pane.layout = BoxLayout(pane, BoxLayout.PAGE_AXIS)

    val text = JLabel(game)
    val pic = JLabel(ImageIcon(image))
    pane.add(text)
    pane.add(pic)
    panel.add(pane)
    pane.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent?) {
            frame.isVisible = true
        }
    })
}

