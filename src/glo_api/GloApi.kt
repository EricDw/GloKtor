package net.publicmethod.glo_api

import glo_api.anti_corruption.transform
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
import io.ktor.client.response.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.headersOf
import io.ktor.util.KtorExperimentalAPI
import net.publicmethod.domain.Board
import net.publicmethod.domain.Boards
import net.publicmethod.domain.GloUser
import net.publicmethod.dtos.BoardDTO
import net.publicmethod.dtos.BoardDTOs
import net.publicmethod.dtos.GloUserDTO

class GloApi @KtorExperimentalAPI constructor(
    private val personalAuthenticationToken: String,
    private val logLevel: LogLevel = LogLevel.NONE,
    private val httpClient: HttpClient = HttpClient(CIO) {
        configureCioClient(logLevel)
    }
)
{

    suspend fun getUserHttpResponse(): HttpResponse =
        httpClient.get { buildURLFor(USER_ENDPOINT) }

    suspend fun getBoardHttpResponse(boardId: String): HttpResponse =
        httpClient.get { buildURLFor(BOARD_ENDPOINT, boardId) }

    suspend fun getBoardsHttpResponse(): HttpResponse =
        httpClient.get { buildURLFor(BOARDS_ENDPOINT) }

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun getUser(parameters: Map<String, String>? = mapOf()): GloUser =
        getUserDTO(parameters).transform()

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun getBoards(): Boards =
        getBoardDTOs().map { it.transform<Board>() }

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun getBoard(boardId: String): Board =
        getBoardDTO(boardId).transform()

    private suspend fun getUserDTO(parameters: Map<String, String>? = mapOf()): GloUserDTO =
        httpClient.get { buildURLFor(endpoint = USER_ENDPOINT, parameters = parameters) }

    private suspend fun getBoardDTOs(): BoardDTOs =
        httpClient.get { buildURLFor(BOARDS_ENDPOINT) }

    private suspend fun getBoardDTO(boardId: String): BoardDTO =
        httpClient.get { buildURLFor(BOARD_ENDPOINT, boardId) }

    private fun HttpRequestBuilder.buildURLFor(
        endpoint: String,
        boardId: String? = "",
        parameters: Map<String, String>? = mapOf()
    )
    {
        url {
            protocol = URLProtocol.HTTPS
            host = HOST
            encodedPath = "$ENCODED_PATH$endpoint$boardId"
            parameters?.forEach {
                this.parameters.append(it.key, it.value)
            }
            this.parameters.append(QUERY_ACCESS_TOKEN, personalAuthenticationToken)
            headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
        }
    }

    companion object
    {
        const val HOST = "gloapi.gitkraken.com"
        const val ENCODED_PATH = "/v1/glo/"
        const val USER_ENDPOINT = "user"
        const val BOARDS_ENDPOINT = "boards"
        const val BOARD_ENDPOINT = "boards/"
        const val QUERY_ACCESS_TOKEN = "access_token"
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
