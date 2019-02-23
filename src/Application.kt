package net.publicmethod

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)
//
//@KtorExperimentalAPI
//@Suppress("unused") // Referenced in application.conf
//@kotlin.jvm.JvmOverloads
//fun Application.module(testing: Boolean = false) {
//    val client = HttpClient(CIO) {
//        install(JsonFeature) {
//            serializer = GsonSerializer()
//        }
//        install(Logging) {
//            level = LogLevel.HEADERS
//        }
//    }
//    runBlocking {
//        // Sample for making a HTTP Client request
//        val message = client.post<GloUserDTO> {
//            url(URL("http://127.0.0.1:8080/path/to/endpoint"))
//            contentType(ContentType.Application.Json)
//            body = GloUserDTO(id = "hello", username = "world")
//        }
//    }
//
//}

