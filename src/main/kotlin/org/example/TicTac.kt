import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.JOptionPane.*

class TicTacToe : JFrame("Крестики-нолики") {
    private val buttons = Array(3) { arrayOfNulls<JButton>(3) }
    private var currentPlayer = 'X'

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(300, 300)
        layout = GridLayout(3, 3)
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j] = JButton()
                buttons[i][j]!!.font = Font("Arial", Font.BOLD, 100)
                buttons[i][j]!!.addActionListener(ButtonClickListener())
                add(buttons[i][j])
            }
        }
    }

    internal inner class ButtonClickListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            val button = e.source as JButton
            if (button.text == "") {
                button.text = currentPlayer.toString()
                if (isGameOver()) {
                    showMessageDialog(this@TicTacToe, "$currentPlayer выиграл!", "Победа", INFORMATION_MESSAGE)
                    resetGame()
                } else if (isBoardFull()) {
                    showMessageDialog(this@TicTacToe, "Ничья!", "Ничья", INFORMATION_MESSAGE)
                    resetGame()
                } else {
                    currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
                }
            }
        }
    }

    private fun isGameOver(): Boolean {
        // Проверка по горизонталям, вертикалям и диагоналям
        for (i in 0..2) {
            if (buttons[i][0]!!.text == currentPlayer.toString() && buttons[i][1]!!.text == currentPlayer.toString() && buttons[i][2]!!.text == currentPlayer.toString()
                || buttons[0][i]!!.text == currentPlayer.toString() && buttons[1][i]!!.text == currentPlayer.toString() && buttons[2][i]!!.text == currentPlayer.toString()
            ) return true
        }
        return buttons[0][0]!!.text == currentPlayer.toString() && buttons[1][1]!!.text == currentPlayer.toString() && buttons[2][2]!!.text == currentPlayer.toString()
                || buttons[0][2]!!.text == currentPlayer.toString() && buttons[1][1]!!.text == currentPlayer.toString() && buttons[2][0]!!.text == currentPlayer.toString()
    }

    private fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (buttons[i][j]!!.text == "") return false
            }
        }
        return true
    }

    private fun resetGame() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]!!.text = ""
            }
        }
        currentPlayer = 'X'
    }
}

fun main() {
    SwingUtilities.invokeLater { TicTacToe().isVisible = true }
}