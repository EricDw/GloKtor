package net.publicmethod

import dtos.GloBoardDTO
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.CIOEngineConfig
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.headersOf
import io.ktor.util.KtorExperimentalAPI
import net.publicmethod.dtos.GloUserDTO

class GloApi @KtorExperimentalAPI constructor(
    private val personalAuthenticationToken: String,
    private val logLevel: LogLevel = LogLevel.NONE,
    private val httpClient: HttpClient = HttpClient(CIO) {
        configureCioClient(logLevel)
    }
)
{
    suspend fun getUser(): GloUserDTO =
        httpClient.get { buildURLFor(USER_ENDPOINT) }

    suspend fun getBoards(): List<GloBoardDTO> =
        httpClient.get { buildURLFor(BOARDS_ENDPOINT) }

    private fun HttpRequestBuilder.buildURLFor(endpoint: String)
    {

            url {
            protocol = URLProtocol.HTTPS
            host = HOST
            encodedPath = "$ENCODED_PATH$endpoint"
            parameters.append(QUERY_ACCESS_TOKEN, personalAuthenticationToken)
            headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
        }
    }

    fun getBoard(): GloBoardDTO
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

private fun HttpClientConfig<CIOEngineConfig>.configureCioClient(logLevel: LogLevel)
{
    install(JsonFeature) {
        serializer = GsonSerializer()
    }
    install(Logging) {
        level = logLevel
    }
}

private const val HOST = "gloapi.gitkraken.com"
private const val ENCODED_PATH = "/v1/glo/"
private const val USER_ENDPOINT = "user"
private const val BOARDS_ENDPOINT = "boards"
private const val QUERY_ACCESS_TOKEN = "access_token"
