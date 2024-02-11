package org.example.register

fun main(args: Array<String>) {
    val name = args[0]
    val password = args[1]
    val host = if (args.size > 2) args[2] else "localhost:8080"

    println("send: $name $password to http://$host/register")
}