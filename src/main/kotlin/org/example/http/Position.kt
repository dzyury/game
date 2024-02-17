package org.example.http

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Position(@JsonValue val value: String) {
    WHITE("white"),
    BLACK("black");

    companion object {
        @JsonCreator
        fun of(value: String): Position = entries.find { it.value == value }!!
    }
}