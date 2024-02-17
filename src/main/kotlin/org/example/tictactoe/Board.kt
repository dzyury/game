package org.example.tictactoe

import org.example.tictactoe.model.Model
import java.awt.GridLayout
import javax.swing.JPanel

class Board(val model: Model) : JPanel() {
    val fields: Array<Array<Field>>

    init {
        layout = GridLayout(3, 3)

        fields = Array(3) { x -> Array(3) { y -> Field(model, x, y) } }
        fields.forEach { row -> row.forEach { add(it) } }
        model.fields = fields
    }
}
