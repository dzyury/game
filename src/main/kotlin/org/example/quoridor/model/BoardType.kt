package org.example.quoridor.model

const val HINT = 32

enum class BoardType {
    DEFAULT, WHITE_PAWN, BLACK_PAWN, WHITE_FENCE, BLACK_FENCE;

    companion object {
        val fences: IntArray by lazy { listOf(WHITE_FENCE, BLACK_FENCE).map { it.ordinal }.toIntArray() }
    }
}