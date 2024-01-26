package org.example.quoridor

import org.example.quoridor.model.BoardModel
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent.*
import javax.swing.AbstractAction
import javax.swing.JComponent
import javax.swing.KeyStroke

class Board(val model: BoardModel) : JComponent() {
    init {
        register("up", VK_U, VK_UP, VK_8) { model.move(VK_UP) }
        register("down", VK_D, VK_DOWN, VK_2) { model.move(VK_DOWN) }
        register("left", VK_L, VK_LEFT, VK_4) { model.move(VK_LEFT) }
        register("right", VK_R, VK_RIGHT, VK_6) { model.move(VK_RIGHT) }

        register("left-up", VK_HOME, VK_7) { model.move(VK_HOME) }
        register("right-up", VK_PAGE_UP, VK_9) { model.move(VK_PAGE_UP) }
        register("left-down", VK_END, VK_1) { model.move(VK_END) }
        register("right-down", VK_PAGE_DOWN, VK_3) { model.move(VK_PAGE_DOWN) }

//        addKeyListener(object : KeyAdapter() {
//            override fun keyPressed(e: KeyEvent) {
//                when (e.keyCode) {
//                    KeyEvent.VK_U, KeyEvent.VK_UP -> model.move(KeyEvent.VK_UP)
//                    KeyEvent.VK_L, KeyEvent.VK_LEFT -> model.move(KeyEvent.VK_LEFT)
//                    KeyEvent.VK_D, KeyEvent.VK_DOWN -> model.move(KeyEvent.VK_DOWN)
//                    KeyEvent.VK_R, KeyEvent.VK_RIGHT -> model.move(KeyEvent.VK_RIGHT)
//                }
//            }
//        })
    }

    private fun register(name: String, vararg keys: Int, block: () -> Unit) {
        keys.forEach { key -> inputMap.put(KeyStroke.getKeyStroke(key, 0), name) }
        actionMap.put(name, object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                block()
            }
        })
    }
}