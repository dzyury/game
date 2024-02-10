package org.example.register

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers

data class User(val name: String, val password: String)

fun main(args: Array<String>) {
    val name = args[0]
    val password = args[1]
    val host = if (args.size > 2) args[2] else "localhost:8080"

    val module = KotlinModule.Builder().build()
    val mapper = ObjectMapper().registerModule(module)
    val user = mapper.writeValueAsString(User(name, password))

    println("Sending $user to http://$host/user")

    val request = HttpRequest.newBuilder()
        .uri(URI.create("http://$host/user"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(user))
        .build()
    val response: HttpResponse<String> = HttpClient
        .newBuilder()
        .build()
        .send(request, BodyHandlers.ofString())

    println("Status: ${response.statusCode()}")
    println("Body: ${response.body()}")
}

