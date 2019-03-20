package net.publicmethod.glo_api

import domain.data.*
import domain.queries.*
import glo_api.anti_corruption.transform
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.CIOEngineConfig
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.host
import io.ktor.client.response.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.headersOf
import io.ktor.util.KtorExperimentalAPI
import net.publicmethod.dtos.*

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
                endpoint = { USER_ENDPOINT },
                parameters = UserQueryBuilder().apply(init).build()
            )
        }

    suspend fun queryBoardHttpResponse(boardId: String, init: BoardQueryBuilder.() -> Unit = {}): HttpResponse =
        httpClient.get {
            buildURLFor(
                endpoint = { "$BOARD_ENDPOINT$boardId" },
                parameters = object : BoardQueryBuilder()
                {}.apply(init).build()
            )
        }

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun queryCard(
        boardId: String,
        cardId: String,
        init: CardQueryBuilder.() -> Unit = {}
    ): Card = getCardDTO(
        boardId,
        cardId,
        object : CardQueryBuilder()
        {}.apply(init).build()
    ).transform()

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun queryCards(
        boardId: String,
        init: CardsQueryBuilder.() -> Unit = {}
    ): Cards = getCardDTOs(
        boardId,
        CardsQueryBuilder().apply(init).build()
    ).map { it.transform<Card>() }

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun queryCardsForColumn(
        boardId: String,
        columnId: String,
        init: ColumnCardsQueryBuilder.() -> Unit = {}
    ): Cards = getCardDTOsForColumn(
        boardId,
        columnId,
        ColumnCardsQueryBuilder().apply(init).build()
    ).map { it.transform<Card>() }

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun queryBoard(
        boardId: String,
        init: BoardQueryBuilder.() -> Unit = {}
    ): Board = getBoardDTO(
        boardId,
        object : BoardQueryBuilder()
        {}.apply(init).build()
    ).transform()

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun queryBoards(init: BoardsQueryBuilder.() -> Unit = {}): Boards =
        getBoardDTOs(BoardsQueryBuilder().apply(init).build()).map { it.transform<Board>() }

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun queryUser(init: UserQueryBuilder.() -> Unit = {}): GloUser =
        getUserDTO(UserQueryBuilder().apply(init).build()).transform()

    suspend fun getUserHttpResponse(): HttpResponse =
        httpClient.get { buildURLFor({ USER_ENDPOINT }) }

    suspend fun getBoardHttpResponse(boardId: String): HttpResponse =
        httpClient.get { buildURLFor({ "$BOARD_ENDPOINT$boardId" }) }

    suspend fun getBoardsHttpResponse(): HttpResponse =
        httpClient.get { buildURLFor({ BOARDS_ENDPOINT }) }

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun getAttachmentsForCard(boardId: String, cardId: String): Attachments =
        getAttachmentDTOsForCard(
            boardId, cardId
        ).map { it.transform<Attachment>() }

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

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun getColumns(boardId: String): Columns =
        getBoard(boardId).columns

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun getCardsForColumn(boardId: String, columnId: String): Cards =
        getCardDTOsForColumn(boardId, columnId).map { it.transform<Card>() }

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun getCards(boardId: String): Cards =
        getCardDTOs(boardId).map { it.transform<Card>() }

    /**
     * Potentially unsafe operation
     * and can throw a plethora of exceptions.
     */
    @Throws
    suspend fun getCard(boardId: String, cardId: String): Card =
        getCardDTO(boardId, cardId).transform()

    private suspend fun getAttachmentDTOsForCard(
        boardId: String,
        cardId: String,
        queryParameters: QueryParameters = mapOf()
    ): AttachmentDTOs =
        httpClient.get {
            buildURLFor(
                { "$BOARD_ENDPOINT$boardId$CARD_ENDPOINT$cardId$ATTACHMENTS_ENDPOINT" },
                queryParameters
            )
        }

    private suspend fun getUserDTO(queryParameters: QueryParameters): GloUserDTO =
        httpClient.get {
            buildURLFor(
                endpoint = { USER_ENDPOINT },
                parameters = queryParameters
            )
        }

    private suspend fun getUserDTO(): GloUserDTO =
        httpClient.get { buildURLFor(endpoint = { USER_ENDPOINT }) }

    private suspend fun getBoardDTOs(queryParameters: QueryParameters): BoardDTOs =
        httpClient.get {
            buildURLFor(
                endpoint = { BOARDS_ENDPOINT },
                parameters = queryParameters
            )
        }

    private suspend fun getCardDTOs(
        boardId: String,
        queryParameters: QueryParameters = mapOf()
    ): CardDTOs =
        httpClient.get { buildURLFor({ "$BOARD_ENDPOINT$boardId$CARDS_ENDPOINT" }, queryParameters) }

    private suspend fun getCardDTOsForColumn(
        boardId: String,
        columnId: String,
        queryParameters: QueryParameters = mapOf()
    ): CardDTOs =
        httpClient.get {
            buildURLFor(
                { "$BOARD_ENDPOINT$boardId$CARDS_FOR_COLUMN_ENDPOINT$columnId$CARDS_ENDPOINT" },
                queryParameters
            )
        }

    private suspend fun getCardDTOs(boardId: String): CardDTOs =
        httpClient.get { buildURLFor({ "$BOARD_ENDPOINT$boardId$CARDS_ENDPOINT" }) }

    private suspend fun getCardDTO(
        boardId: String,
        cardId: String,
        queryParameters: QueryParameters = EMPTY_PARAMETERS
    ): CardDTO =
        httpClient.get { buildURLFor({ "$BOARD_ENDPOINT$boardId$CARD_ENDPOINT$cardId" }, queryParameters) }

    private suspend fun getBoardDTOs(): BoardDTOs =
        httpClient.get { buildURLFor({ BOARDS_ENDPOINT }) }

    private suspend fun getBoardDTO(boardId: String): BoardDTO =
        httpClient.get { buildURLFor({ "$BOARD_ENDPOINT$boardId" }) }

    private suspend fun getBoardDTO(
        boardId: String,
        queryParameters: QueryParameters
    ): BoardDTO =
        httpClient.get { buildURLFor({ "$BOARD_ENDPOINT$boardId" }, queryParameters) }

    private fun HttpRequestBuilder.buildURLFor(
        endpoint: () -> String,
        parameters: QueryParameters? = mapOf()
    )
    {
        url {
            protocol = URLProtocol.HTTPS
            host = HOST
            encodedPath = "$ENCODED_PATH${endpoint()}"
            parameters?.forEach { entry ->
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
        const val CARDS_FOR_COLUMN_ENDPOINT = "/columns/"
        const val ATTACHMENTS_ENDPOINT = "/attachments"
        const val CARDS_ENDPOINT = "/cards"
        const val CARD_ENDPOINT = "/cards/"
        const val BOARD_ENDPOINT = "boards/"
        const val HEADER_AUTHORIZATION = "Authorization"
    }
}

private val EMPTY_PARAMETERS: QueryParameters = mapOf()

private fun HttpClientConfig<CIOEngineConfig>.configureCioClient(
    logLevel: LogLevel,
    personalAuthenticationToken: String
)
{
    defaultRequest {
        host = net.publicmethod.glo_api.GloApi.HOST
        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
        headers.append(GloApi.HEADER_AUTHORIZATION, "Bearer $personalAuthenticationToken")
    }

    install(JsonFeature) {
        serializer = GsonSerializer()
    }
    install(Logging) {
        level = logLevel
        logger = Logger.DEFAULT
    }
}
