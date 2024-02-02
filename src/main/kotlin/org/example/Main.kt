package org.example

import org.example.quoridor.Quoridor
import org.example.tic_tac_toe.TicTacToe
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        TicTacToe()
    }
}