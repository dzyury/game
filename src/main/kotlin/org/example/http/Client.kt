package org.example.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import java.util.*

object Client {
    val mapper: ObjectMapper
    private val user: String? = System.getProperty("user")
    private val password: String? = System.getProperty("password")
    val auth: String = Base64.getEncoder().encodeToString("$user:$password".toByteArray())

    init {
        val module = KotlinModule.Builder().build()
        mapper = ObjectMapper().registerModule(module)
    }

    inline fun <reified T> read(path: String): T {
        return send("", path) { builder, _ -> builder.GET() }
    }

    inline fun <reified T> change(data: Any, path: String): T {
        return send(data, path) { builder, body -> builder.method("PATCH", body) }
    }

    inline fun <reified T> send(
        data: Any,
        path: String,
        block: (HttpRequest.Builder, HttpRequest.BodyPublisher) -> HttpRequest.Builder
    ): T {
        val body = mapper.writeValueAsString(data)

        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/$path"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Basic $auth")
            .let { block(it, HttpRequest.BodyPublishers.ofString(body)) }
            .build()

        val response: HttpResponse<String> = HttpClient
            .newBuilder()
            .build()
            .send(request, BodyHandlers.ofString())

        val out = response.body()
        if (response.statusCode() != 200) {
            println("Error: ${response.statusCode()} $out")
        }
        return mapper.readValue(out, T::class.java)
    }
}