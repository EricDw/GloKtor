package net.publicmethod.glo_api

import domain.data.Board
import domain.data.Boards
import domain.data.GloUser
import domain.queries.*
import glo_api.anti_corruption.transform
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.CIOEngineConfig
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.host
import io.ktor.client.response.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.append
import io.ktor.http.headersOf
import io.ktor.util.KtorExperimentalAPI
import net.publicmethod.dtos.BoardDTO
import net.publicmethod.dtos.BoardDTOs
import net.publicmethod.dtos.GloUserDTO
import net.publicmethod.glo_api.GloApi.Companion.HOST

class GloApi @KtorExperimentalAPI constructor(
    private val personalAuthenticationToken: String,
    private val logLevel: LogLevel = LogLevel.NONE,
    private val httpClient: HttpClient = HttpClient(CIO) {
        configureCioClient(logLevel, personalAuthenticationToken)
    }
)
{


    suspend fun queryUserHttpResponse(init: UserQueryBuilder.() -> Unit = {}): HttpResponse =
        httpClient.get {
            buildURLFor(
                endpoint = USER_ENDPOINT,
                parameters = UserQueryBuilder().apply(init).build().userQueryParameters
            )
        }

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
    suspend fun queryUser2(init: UserQueryBuilder2.() -> Unit = {}): GloUser =
        getUserDTO2(UserQueryBuilder2().apply(init).build()).transform()

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun queryUser(init: UserQueryBuilder.() -> Unit = {}): GloUser =
        getUserDTO(UserQueryBuilder().apply(init).build()).transform()

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun getUser(): GloUser =
        getUserDTO().transform()

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

    private suspend fun getUserDTO2(userQuery: Query): GloUserDTO =
        httpClient.get {
            buildURLFor2(
                endpoint = USER_ENDPOINT,
                parameters = userQuery.userQueryParameters
            )
        }

    private suspend fun getUserDTO(userQuery: UserQuery): GloUserDTO =
        httpClient.get {
            buildURLFor(
                endpoint = USER_ENDPOINT,
                parameters = userQuery.userQueryParameters
            )
        }

    private suspend fun getUserDTO(): GloUserDTO =
        httpClient.get { buildURLFor(endpoint = USER_ENDPOINT) }

    private suspend fun getBoardDTOs(): BoardDTOs =
        httpClient.get { buildURLFor(BOARDS_ENDPOINT) }

    private suspend fun getBoardDTO(boardId: String): BoardDTO =
        httpClient.get { buildURLFor(BOARD_ENDPOINT, boardId) }

    private fun HttpRequestBuilder.buildURLFor(
        endpoint: String,
        boardId: String? = "",
        parameters: UserQueryParameters? = Pair("", setOf())
    )
    {
        url {
            protocol = URLProtocol.HTTPS
            host = HOST
            encodedPath = "$ENCODED_PATH$endpoint$boardId"
            parameters?.second?.forEach {
                this.parameters.append(parameters.first, it)
            }
        }
    }

    private fun HttpRequestBuilder.buildURLFor2(
        endpoint: String,
        boardId: String? = "",
        parameters: QueryParameters? = mapOf()
    )
    {
        url {
            protocol = URLProtocol.HTTPS
            host = HOST
            encodedPath = "$ENCODED_PATH$endpoint$boardId"
            parameters?.forEach { entry  ->
                entry.value.forEach {
                    this.parameters.append(entry.key, it)
                }
            }
        }
    }

    companion object
    {
        const val HOST = "gloapi.gitkraken.com"
        const val ENCODED_PATH = "/v1/glo/"
        const val USER_ENDPOINT = "user"
        const val BOARDS_ENDPOINT = "boards"
        const val BOARD_ENDPOINT = "boards/"
        const val HEADER_AUTHORIZATION = "Authorization"
    }

}

private fun HttpClientConfig<CIOEngineConfig>.configureCioClient(
    logLevel: LogLevel,
    personalAuthenticationToken: String
)
{
    defaultRequest {
        host = HOST
        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
        headers.append(GloApi.HEADER_AUTHORIZATION, "Bearer $personalAuthenticationToken")
    }

    install(JsonFeature) {
        serializer = GsonSerializer()
    }
    install(Logging) {
        level = logLevel
    }
}
