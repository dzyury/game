package org.example.quoridor.model

import org.example.quoridor.PlayerPane
import org.example.quoridor.model.BoardType.*
import org.example.quoridor.model.BoardType.Companion.fences
import java.awt.Point
import java.awt.event.KeyEvent
import javax.swing.JComponent
import javax.swing.JOptionPane
import javax.swing.SwingUtilities


class BoardModel(private val n: Int) {
    private val yWhiteGoal = 0
    private val yBlackGoal = n - 1
    private val tmp = Array(n) { BooleanArray(n) }

    private var isWhite = false
    private val white = Point(n / 2, n - 1)
    private val black = Point(n / 2, 0)
    private val pawns = listOf(white, black)

    var view: JComponent? = null
    var whitePanel: PlayerPane? = null
    var blackPanel: PlayerPane? = null
    val board = Array(n) { IntArray(n) }

    private fun Point.isFree(xDiff: Int, yDiff: Int) = x + xDiff in 0..<n && y + yDiff in 0..<n && board[x + xDiff][y + yDiff] == DEFAULT.ordinal
    private fun Point.isPawn(xDiff: Int, yDiff: Int) = x + xDiff in 0..<n && y + yDiff in 0..<n && Point(x + xDiff, y + yDiff) in pawns
    private fun Point.isFence(xDiff: Int, yDiff: Int) = x + xDiff !in 0..<n || y + yDiff !in 0..<n || board[x + xDiff][y + yDiff] in fences

    fun start() {
        isWhite = false
        whitePanel?.start()
        blackPanel?.start()
        turnOver()
        board.forEach { it.fill(0) }
        board[n / 2][0] = BLACK_PAWN.ordinal
        black.x = n / 2
        black.y = 0

        board[n / 2][n - 1] = WHITE_PAWN.ordinal
        white.x = n / 2
        white.y = n - 1
    }

    fun move(dir: Int) {
        val pawn = if (isWhite) white else black
        val diffs = when (dir) {
            KeyEvent.VK_UP -> listOf(Point(0, -2), Point(0, -4))
            KeyEvent.VK_DOWN -> listOf(Point(0, 2), Point(0, 4))
            KeyEvent.VK_LEFT -> listOf(Point(-2, 0), Point(-4, 0))
            KeyEvent.VK_RIGHT -> listOf(Point(2, 0), Point(4, 0))

            KeyEvent.VK_HOME -> listOf(Point(-2, -2))
            KeyEvent.VK_PAGE_UP -> listOf(Point(2, 2))
            KeyEvent.VK_END -> listOf(Point(-2, 2))
            KeyEvent.VK_PAGE_DOWN -> listOf(Point(2, 2))

            else -> listOf()
        }
        diffs.forEach {
            if (hintPawn(pawn.x + it.x, pawn.y + it.y)) {
                setPawn(pawn.x + it.x, pawn.y + it.y)
                return
            }
        }
    }

    fun dropHintFence(vararg points: Point) {
        points.forEach {
            if (board[it.x][it.y] > HINT) board[it.x][it.y] = DEFAULT.ordinal
        }
        view?.repaint()
    }

    fun hintFence(vararg points: Point) {
        val fenceModel = (if (isWhite) whitePanel else blackPanel)?.model ?: return
        if (fenceModel.count <= 0 || points.any { board[it.x][it.y] in fences }) return

        val fence = HINT + if (isWhite) WHITE_FENCE.ordinal else BLACK_FENCE.ordinal
        points.forEach {
            board[it.x][it.y] = fence
        }
        if (isBlocking()) {
            dropHintFence(*points)
            return
        }
        view?.repaint()
    }

    private fun isBlocking(): Boolean {
        fun isBlocking(from: Point, yGoal: Int): Boolean {
            if (from.y == yGoal) return false

            val queue = ArrayDeque(listOf(from))
            tmp.forEach { it.fill(true) }
            while (queue.isNotEmpty()) {
                val point = queue.removeFirst()
                if (point.y == yGoal) return false

                tmp[point.x][point.y] = false
                if (point.x > 0 && point.isFree(-1, 0) && tmp[point.x - 2][point.y]) queue.addLast(Point(point.x - 2, point.y))
                if (point.x < n - 1 && point.isFree(1, 0) && tmp[point.x + 2][point.y]) queue.addLast(Point(point.x + 2, point.y))
                if (point.y > 0 && point.isFree(0, -1) && tmp[point.x][point.y - 2]) queue.addLast(Point(point.x, point.y - 2))
                if (point.y < n - 1 && point.isFree(0, 1) && tmp[point.x][point.y + 2]) queue.addLast(Point(point.x, point.y + 2))
            }
            return true
        }

        return isBlocking(white, yWhiteGoal) || isBlocking(black, yBlackGoal)
    }

    fun setFence(vararg points: Point) {
        val fenceModel = (if (isWhite) whitePanel else blackPanel)?.model ?: return
        if (points.any { it.isFence(0, 0) }) return

        val fence = if (isWhite) WHITE_FENCE.ordinal else BLACK_FENCE.ordinal
        if (points.any { board[it.x][it.y] != HINT + fence }) return
        points.forEach {
            board[it.x][it.y] = fence
        }

        fenceModel.count--
        turnOver()
        view?.repaint()
    }

    fun dropHintPawn(x: Int, y: Int) {
        if (board[x][y] > HINT) board[x][y] = DEFAULT.ordinal
        view?.repaint()
    }

    fun hintPawn(x: Int, y: Int): Boolean {
        if (!isStep(x, y) && !isJump(x, y) && !isJumpNearFence(x, y)) return false

        val hint = (if (isWhite) WHITE_PAWN else BLACK_PAWN).ordinal
        board[x][y] = HINT + hint
        view?.repaint()
        return true
    }

    private fun isStep(x: Int, y: Int): Boolean {
        val pawn = if (isWhite) white else black
        val steps = listOf(Point(0, 2), Point(0, -2), Point(2, 0), Point(-2, 0))
        steps.forEach {
            if (x == pawn.x + it.x && y == pawn.y + it.y
                && pawn.isFree(it.x, it.y)
                && pawn.isFree(it.x / 2, it.y / 2)
            ) return true
        }
        return false
    }

    private fun isJump(x: Int, y: Int): Boolean {
        val pawn = if (isWhite) white else black
        val steps = listOf(Point(0, 1), Point(0, -1), Point(1, 0), Point(-1, 0))
        steps.forEach {
            if (x == pawn.x + it.x * 4 && y == pawn.y + it.y * 4
                && pawn.isPawn(it.x * 2, it.y * 2)
                && pawn.isFree(it.x * 1, it.y * 1)
                && pawn.isFree(it.x * 3, it.y * 3)
            ) return true
        }
        return false
    }

    private fun isJumpNearFence(x: Int, y: Int): Boolean {
        val pawn = if (isWhite) white else black
        val steps = listOf(Point(1, 1), Point(1, -1), Point(-1, 1), Point(-1, -1))
        steps.forEach {
            if (x == pawn.x + it.x * 2 && y == pawn.y + it.y * 2 && listOf(
                    pawn.isFree(0, it.y) && pawn.isFree(it.x, it.y * 2) && pawn.isFence(0, it.y * 3) && pawn.isPawn(0, it.y * 2),
                    pawn.isFree(it.x, 0) && pawn.isFree(it.x * 2, it.y) && pawn.isFence(it.x * 3, 0) && pawn.isPawn(it.x * 2, 0)
                ).any { condition -> condition }
            ) return true
        }
        return false
    }

    fun setPawn(x: Int, y: Int) {
        val pawn = (if (isWhite) WHITE_PAWN else BLACK_PAWN).ordinal
        if (board[x][y] != HINT + pawn) return

        board[x][y] = pawn
        if (isWhite) {
            board[white.x][white.y] = DEFAULT.ordinal
            white.x = x
            white.y = y
        } else {
            board[black.x][black.y] = DEFAULT.ordinal
            black.x = x
            black.y = y
        }

        turnOver()
        view?.repaint()

        if (white.y == yWhiteGoal || black.y == yBlackGoal) {
            whitePanel?.turn(false)
            blackPanel?.turn(false)

            val frame = SwingUtilities.windowForComponent(view!!)
            val winner = if (white.y == yWhiteGoal) "White" else "Black"

            val options = arrayOf("Yes, please", "No, thanks")
            val answer = JOptionPane.showOptionDialog(
                frame,
                "<html>$winner won<br>Would you like to play again?",
                "Game over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            )

            start()
            view?.repaint()
            if (answer != 0) SwingUtilities.getWindowAncestor(view!!)?.isVisible = false
        }
    }

    private fun turnOver() {
        isWhite = !isWhite
        whitePanel?.turn(isWhite)
        blackPanel?.turn(!isWhite)
    }
}
