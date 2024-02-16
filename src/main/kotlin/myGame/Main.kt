@file:Suppress("NAME_SHADOWING", "DEPRECATION")

package myGame

import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import kotlin.system.exitProcess

fun main() {
    val field: Array<Array<Int>> = arrayOf(
        arrayOf(0, 0, 0),
        arrayOf(0, 0, 0),
        arrayOf(0, 0, 0)
    )
    var turn = 1
    // 1 - крестик, 2 - нолик
    val frame = JFrame()
    frame.size = Dimension(1280, 800)

    val panel = JPanel()
    panel.layout = GridLayout(3, 3)
    val states: Array<Int> = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    for (i in 0..8) {

        val pane = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                super.paintComponent(g)

                when (states[i]) {
                    1 -> {
                        val g2d = g as Graphics2D
                        g2d.stroke = BasicStroke(10f)
                        g2d.color = Color(178, 16, 16)

                        g2d.drawLine(super.size().width/10, super.size().height/10,
                            super.size().width*9/10, super.size().height*9/10)
                        g2d.drawLine(super.size().width*9/10, super.size().height/10,
                            super.size().width/10, super.size().height*9/10)

                    }

                    2 -> {
                        val g2d = g as Graphics2D
                        g2d.stroke = BasicStroke(10f)
                        g2d.color = Color(23, 52, 141)

                        g2d.drawOval(super.size().width/6, super.size().height/10,
                            super.size().width*2/3, super.size().height*4/5)
                    }

                    -1 -> {
                        val g2d = g as Graphics2D
                        g2d.stroke = BasicStroke(10f)
                        g2d.color = Color(178, 16, 16, 50)

                        g2d.drawLine(super.size().width/10, super.size().height/10,
                            super.size().width*9/10, super.size().height*9/10)
                        g2d.drawLine(super.size().width*9/10, super.size().height/10,
                            super.size().width/10, super.size().height*9/10)
                    }

                    -2 -> {
                        val g2d = g as Graphics2D
                        g2d.stroke = BasicStroke(10f)
                        g2d.color = Color(23, 52, 141, 50)

                        g2d.drawOval(super.size().width/6, super.size().height/10,
                            super.size().width*2/3, super.size().height*4/5)
                    }
                }
            }
        }

        panel.add(pane.also {
            it.border = BorderFactory.createLineBorder(Color.DARK_GRAY)
            it.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    if (clicked(i, it, field, turn, frame, states)) {
                        turn = if (turn == 1) 2 else 1
                    }
                }

                override fun mouseEntered(e: MouseEvent?) {
                    entered(i, it, field, turn, states)
                }

                override fun mouseExited(e: MouseEvent?) {
                    exited(i, it, field, states)
                }
            }
            )
        })
    }
    frame.isVisible = true
    frame.contentPane = panel

    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
}

fun clicked(i: Int, panel: JPanel, field: Array<Array<Int>>, turn: Int, frame: JFrame, states: Array<Int>): Boolean {
    if (field[i / 3][i % 3] == 0) {


        if (turn == 1) {
            states[i] = 1
            panel.repaint()
            field[i / 3][i % 3] = 1
            for (h in 0..2) {
                if ((field[h][0] == field[h][1] && field[h][1] == field[h][2] && field[h][0] != 0) ||
                    (field[0][h] == field[1][h] && field[1][h] == field[2][h] && field[0][h] != 0) ||
                    (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[0][0] != 0) ||
                    (field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[0][2] != 0)
                ) {
                    val dialog = JDialog(frame, "x", true)
                    dialog.setSize(180, 90)
                    val button1 = JButton("EXIT")
                    val button2 = JButton("REPLAY")
                    val panelB = JPanel()
                    val label = JLabel("                " +
                            "                              The crosses won. Choose the option")
                    dialog.add(panelB)
                    panelB.layout = GridLayout(3, 1)
                    panelB.add(label)
                    dialog.isResizable = false
                    button1.addActionListener {
                        exitProcess(1)
                    }
                    button2.addActionListener {
                        for (i in 0..2) {
                            for (j in 0..2) {
                                field[i][j] = 0
                                states[i * 3 + j] = 0
                            }
                        }
                        panel.parent.repaint()
                        dialog.isVisible = false
                    }
                    panelB.add(button1)
                    panelB.add(button2)
                    dialog.location = Point(400, 200)
                    dialog.size = Dimension(500, 500)
                    dialog.isVisible = true

                }
            }
        } else if (turn == 2) {
            states[i] = 2
            panel.repaint()
            field[i / 3][i % 3] = 2
            for (h in 0..2) {
                if ((field[h][0] == field[h][1] && field[h][1] == field[h][2] && field[h][0] != 0) ||
                    (field[0][h] == field[1][h] && field[1][h] == field[2][h] && field[0][h] != 0) ||
                    (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[0][0] != 0) ||
                    (field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[0][2] != 0)
                ) {
                    val dialog = JDialog(frame, "o", true)
                    dialog.setSize(180, 90)
                    val button1 = JButton("EXIT")
                    val button2 = JButton("REPLAY")
                    val panelB = JPanel()
                    val label = JLabel("                     " +
                            "                         The noughts won. Choose the option")
                    dialog.add(panelB)
                    panelB.layout = GridLayout(3, 1)
                    panelB.add(label)
                    dialog.isResizable = false
                    button1.addActionListener {
                        exitProcess(1)
                    }
                    button2.addActionListener {
                        for (i in 0..2) {
                            for (j in 0..2) {
                                field[i][j] = 0
                                states[i * 3 + j] = 0
                            }
                        }
                        panel.parent.repaint()
                        dialog.isVisible = false
                    }
                    panelB.add(button1)
                    panelB.add(button2)
                    dialog.location = Point(400, 200)
                    dialog.size = Dimension(500, 500)
                    dialog.isVisible = true
                }
            }
        }
        var fieldIsFilled = true
        for (q in 0..2) {
            for (w in 0..2) {
                if (field[q][w] == 0) fieldIsFilled = false
            }
        }
        if (fieldIsFilled) {
            val dialog = JDialog(frame, "_", true)
            dialog.setSize(180, 90)
            val button1 = JButton("EXIT")
            val button2 = JButton("REPLAY")
            val panelB = JPanel()
            val label = JLabel("                                      " +
                    "                Draw. Choose the option")
            dialog.add(panelB)
            panelB.layout = GridLayout(3, 1)
            panelB.add(label)
            dialog.isResizable = false
            button1.addActionListener {
                exitProcess(1)
            }
            button2.addActionListener {
                for (i in 0..2) {
                    for (j in 0..2) {
                        field[i][j] = 0
                        states[i * 3 + j] = 0
                    }
                }
                panel.parent.repaint()
                dialog.isVisible = false
            }
            panelB.add(button1)
            panelB.add(button2)
            dialog.location = Point(400, 200)
            dialog.size = Dimension(500, 500)
            dialog.isVisible = true
        }
        return true
    }
    return false
}

fun entered(i: Int, panel: JPanel, field: Array<Array<Int>>, turn: Int, states: Array<Int>) {
    if (field[i / 3][i % 3] == 0) {
        if (turn == 1) {
            states[i] = -1
            panel.repaint()
        }
        if (turn == 2) {
            states[i] = -2
            panel.repaint()
        }
    }

}

fun exited(i: Int, panel: JPanel, field: Array<Array<Int>>, states: Array<Int>) {

    if (field[i / 3][i % 3] == 0) {
        states[i] = 0
        panel.repaint()
    }
}

