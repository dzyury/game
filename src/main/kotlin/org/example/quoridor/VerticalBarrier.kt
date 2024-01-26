package org.example.quoridor

import org.example.quoridor.model.BoardModel
import java.awt.GridLayout
import java.awt.Point

class VerticalBarrier(model: BoardModel, x: Int, y: Int) : Barrier(model, x, y) {
    private val hb1 = HalfBarrier(model, Point(x, y - 2), Point(x, y - 1), Point(x, y))
    private val hb2 = HalfBarrier(model, Point(x, y), Point(x, y + 1), Point(x, y + 2))

    init {
        layout = GridLayout(2, 1)
        add(hb1)
        add(hb2)
    }
}