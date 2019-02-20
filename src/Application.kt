package net.publicmethod

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import java.net.URL
import kotlinx.coroutines.*
import io.ktor.client.features.logging.*
import io.ktor.http.ContentType
import io.ktor.http.contentType

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            level = LogLevel.HEADERS
        }
    }
    runBlocking {
        // Sample for making a HTTP Client request
        val message = client.post<JsonSampleClass> {
            url(URL("http://127.0.0.1:8080/path/to/endpoint"))
            contentType(ContentType.Application.Json)
            body = JsonSampleClass(hello = "world")
        }
    }

}

data class JsonSampleClass(val hello: String)

