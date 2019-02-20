package net.publicmethod

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.util.KtorExperimentalAPI
import net.publicmethod.dtos.GloUserDTO

class GloApi @KtorExperimentalAPI constructor(
    private val personalAuthenticationToken: String,
    private val logLevel: LogLevel = LogLevel.NONE,
    private val httpClient: HttpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            level = logLevel
        }
    }
)
{
    suspend fun getUser(): GloUserDTO =
        httpClient.get {
            url(buildGetUserURL())
        }


    private fun buildGetUserURL(): String =
        "$BASE_URL$USER_ENDPOINT$QUERY_ACCESS_TOKEN$personalAuthenticationToken"

}

private const val BASE_URL = "https://gloapi.gitkraken.com/v1/glo/"
private const val USER_ENDPOINT = "user"
private const val QUERY_ACCESS_TOKEN = "?access_token="
