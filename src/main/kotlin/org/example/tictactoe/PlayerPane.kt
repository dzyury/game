package org.example.tictactoe

import java.awt.*
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.Timer

class PlayerPane(name: String, image: Image) : JPanel() {
    private val nameLabel: JLabel
    private val timeLabel: JLabel
    private val picLabel: JLabel

    private var row = 0
    private var time = 0
    private val timer: Timer

    init {
        preferredSize = Dimension(80, 80)
        minimumSize = Dimension(80, 80)
        val icon = ImageIcon(image.getScaledInstance(40, 40, Image.SCALE_FAST))
        nameLabel = JLabel(name)
        timeLabel = JLabel("0:00.0").also { it.font = Font(Font.MONOSPACED, Font.BOLD, 18) }
        picLabel = JLabel(icon).also { it.isVisible = false; }

        layout = GridBagLayout()
        val c = GridBagConstraints()
        c.ipady = 10
        add(nameLabel, c)
        add(timeLabel, c)
        add(timeLabel, c)
        add(picLabel, c)

        c.weighty = 1.0
        c.fill = GridBagConstraints.VERTICAL
        add(JPanel(), c)

        timer = Timer(100) {
            time += 1
            updateTimerLabel()
        }
    }

    override fun add(comp: Component, constraints: Any) {
        (constraints as GridBagConstraints).gridy = row++
        super.add(comp, constraints)
    }

    private fun updateTimerLabel() {
        val millis = time % 10
        val second = time / 10 % 60
        val minute = time / 10 / 60
        timeLabel.text = "$minute:${"%02d".format(second)}.$millis"
    }

    fun start() {
        row = 0
        time = 0
        updateTimerLabel()
    }

    fun turn(on: Boolean) {
        if (on) timer.start() else timer.stop()
        val style = if (on) Font.BOLD else Font.PLAIN
        nameLabel.font = nameLabel.font.deriveFont(style, 16f)
        picLabel.isVisible = on
    }
}