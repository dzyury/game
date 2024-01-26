package org.example.quoridor

import org.example.quoridor.model.BoardModel
import java.awt.GridLayout
import java.awt.Point

class HorizontalBarrier(model: BoardModel, x: Int, y: Int) : Barrier(model, x, y) {
    private val hb1 = HalfBarrier(model, Point(x - 2, y), Point(x - 1, y), Point(x, y))
    private val hb2 = HalfBarrier(model, Point(x, y), Point(x + 1, y), Point(x + 2, y))

    init {
        layout = GridLayout(1, 2)
        add(hb1)
        add(hb2)
    }
}