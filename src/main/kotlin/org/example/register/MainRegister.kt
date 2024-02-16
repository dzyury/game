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
    request_creator(args)
    println("send: $name $password to http://$host/register")
}



fun request_creator(args: Array<String>) {
    val module = KotlinModule.Builder().build()
    val mapper = ObjectMapper().registerModule(module)
    val user = mapper.writeValueAsString(User(args[0], args[1]))

    val request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/user"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(user))
        .build()
    val response: HttpResponse<String> = HttpClient
        .newBuilder()
        .build()
        .send(request, BodyHandlers.ofString())

    println("status: ${response.statusCode()}")
    println("body: ${response.body()}")
}