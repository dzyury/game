package org.example.tictactoe

import org.example.http.Board
import org.example.http.Client
import org.example.http.Position.WHITE
import org.example.http.Role.PLAYER
import org.example.http.User
import org.example.tictactoe.model.Model
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Manager(val model: Model) {
    fun start() {
        val name = model.me
        val start = User(name, PLAYER, WHITE)
        val board: Board = Client.change(start, "game/1/room/1/board/any")
        println("board: $board")

//        model.manager = this
//        model.go(board)
        repeatReadingState(board.id)
    }

    private fun repeatReadingState(id: Int) {
        val executor = Executors.newSingleThreadScheduledExecutor()
        executor.scheduleAtFixedRate(
            {
                val board: Board = Client.read("game/1/room/1/board/$id")
//                model.go(board)
            },
            0L,
            100,
            TimeUnit.MILLISECONDS
        )
    }

    fun go(x: Int, y: Int) {
        TODO("Not yet implemented")
    }
}
