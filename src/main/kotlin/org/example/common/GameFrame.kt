package org.example.common

import javax.swing.JFrame

open class GameFrame private constructor(game: String, val me: String) : JFrame(game) {
    constructor(game: String) : this(game, System.getProperty("user") ?: "")
}