package org.example

import org.example.ticTacToe.TicTacToe
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    SwingUtilities.invokeLater {
        TicTacToe()
    }
}