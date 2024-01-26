package org.example.quoridor

import org.example.quoridor.model.FenceModel
import java.awt.*
import javax.swing.*

private const val FENCE_COUNT = 10
private val FENCE_COLOR = Color(192, 192, 0)

class PlayerPane(name: String, image: Image, val model: FenceModel = FenceModel(FENCE_COUNT)) : JPanel() {
    private val icon: Icon
    private val pawnLabel: JLabel
    private val fenceLabel: JLabel
    private val timeLabel: JLabel
    private val picLabel: JLabel

    private var row = 0
    private var time = 0
    private val timer: Timer

    init {
        preferredSize = Dimension(80, 80)
        minimumSize = Dimension(80, 80)
        icon = ImageIcon(image.getScaledInstance(40, 40, Image.SCALE_FAST))
        pawnLabel = JLabel(name)
        fenceLabel = JLabel("${model.count}").also {
            it.font = it.font.deriveFont(Font.BOLD, 32f)
            it.foreground = FENCE_COLOR
        }
        timeLabel = JLabel("0:00.0").also { it.font = Font(Font.MONOSPACED, Font.BOLD, 18) }
        picLabel = JLabel(icon).also { it.isVisible = false; }

        layout = GridBagLayout()
        val c = GridBagConstraints()
        c.ipady = 10
        add(pawnLabel, c)
        add(fenceLabel, c)
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

    fun updateFenceLabel() {
        fenceLabel.text = "${model.count}"
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
        model.count = FENCE_COUNT
        model.pane = this
        updateTimerLabel()
    }

    fun turn(on: Boolean) {
        if (on) timer.start() else {
            timer.stop()
            fenceLabel
        }
        val style = if (on) Font.BOLD else Font.PLAIN
        pawnLabel.font = pawnLabel.font.deriveFont(style, 16f)
        picLabel.isVisible = on
    }
}