package org.example.tictactoe

import org.example.tictactoe.model.Type

data class Winner(val type: Type, val x1: Int = 0, val y1: Int = 0, val x2: Int = 0, val y2: Int = 0) {
    companion object {
        val EMPTY = Winner(Type.EMPTY)
    }
}
