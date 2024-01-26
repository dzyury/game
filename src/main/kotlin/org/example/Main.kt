package org.example

import org.example.quoridor.Quoridor
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        Quoridor()
    }
}