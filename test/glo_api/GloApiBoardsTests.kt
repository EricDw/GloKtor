package glo_api

import domain.data.Board
import io.ktor.client.features.logging.LogLevel
import io.ktor.http.HttpStatusCode
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import net.publicmethod.domain.queries.QUERY_KEY_FIELDS
import net.publicmethod.domain.queries.QUERY_VALUE_INVITED_MEMBERS
import net.publicmethod.glo_api.GloApi
import kotlin.test.Test
import kotlin.test.assertEquals


class GloApiBoardsTests : GloApiTest
{

    private val boardJson =
        """{"name":"$TEST_BOARD_NAME_1",
            |"id":"$TEST_BOARD_ID_1"}""".trimMargin()

    private val boardsJson =
        """[{"name":"$TEST_BOARD_NAME_1",
            |"id":"$TEST_BOARD_ID_1",
            |"invited_members":[]},
            |{"name":"$TEST_BOARD_NAME_2",
            |"id":"$TEST_BOARD_ID_2"}]""".trimMargin()

    @KtorExperimentalAPI
    @Test
    fun `given PAT when createBoard with BoardName then return new Board`() =
        runBlocking {

            // Arrange
            val client = generateHttpClientWithMockEngine {
                when (url.encodedPath)
                {
                    "/v1/glo/boards" ->
                    {
                        generateMockHttpResponseFor(boardJson)
                    }
                    else ->
                        generate404MockHttpResponse()
                }
            }

            val gloApi = GloApi(
                personalAuthenticationToken = TEST_PAT,
                logLevel = LogLevel.ALL,
                httpClient = client
            )

            val expected = listOf(
                Board(
                    id = TEST_BOARD_ID_1,
                    name = TEST_BOARD_NAME_1
                ),
                Board(
                    id = TEST_BOARD_ID_2,
                    name = TEST_BOARD_NAME_2
                )
            )

            // Act
            val actual = gloApi.createBoard(boardName = TEST_BOARD_NAME_1)

            // Assert
            assertEquals(expected, actual)
        }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when queryBoards with BoardsQuery then return correct Boards`() =
        runBlocking {

            // Arrange
            val client = generateHttpClientWithMockEngine {
                when (url.parameters.contains(QUERY_KEY_FIELDS, QUERY_VALUE_INVITED_MEMBERS))
                {
                    true ->
                    {
                        generateMockHttpResponseFor(boardsJson)
                    }
                    else ->
                        generate404MockHttpResponse()
                }
            }

            val gloApi = GloApi(
                personalAuthenticationToken = TEST_PAT,
                logLevel = LogLevel.ALL,
                httpClient = client
            )

            val expected = listOf(
                Board(
                    id = TEST_BOARD_ID_1,
                    name = TEST_BOARD_NAME_1
                ),
                Board(
                    id = TEST_BOARD_ID_2,
                    name = TEST_BOARD_NAME_2
                )
            )

            // Act
            val actual = gloApi.queryBoards {
                addInvitedMembers()
            }

            // Assert
            assertEquals(expected, actual)
        }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getBoard with BoardQuery then return correct Board`() =
        runBlocking {

            // Arrange
            val client = generateHttpClientWithMockEngine {
                when (url.parameters.contains(QUERY_KEY_FIELDS, QUERY_VALUE_INVITED_MEMBERS))
                {
                    true ->
                    {
                        generateMockHttpResponseFor(boardJson)
                    }
                    else ->
                        generate404MockHttpResponse()
                }
            }

            val gloApi = GloApi(
                personalAuthenticationToken = TEST_PAT,
                logLevel = LogLevel.ALL,
                httpClient = client
            )

            val expected =
                Board(
                    id = TEST_BOARD_ID_1,
                    name = TEST_BOARD_NAME_1
                )


            // Act
            val actual = gloApi.queryBoard(TEST_BOARD_ID_1) {
                addInvitedMembers()
            }

            // Assert
            assertEquals(expected, actual)
        }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when queryBoardHttpResponse then return HttpResponse`() =
        runBlocking {

            // Arrange
            val client = generateHttpClientWithMockEngine {
                when
                {
                    url.parameters.contains("fields", "name") ->
                    {
                        generateMockHttpResponseFor(boardJson)
                    }
                    else ->
                        generate404MockHttpResponse()
                }
            }

            val gloApi = GloApi(
                personalAuthenticationToken = TEST_PAT,
                logLevel = LogLevel.ALL,
                httpClient = client
            )

            val expected = HttpStatusCode.OK

            // Act
            val actual = gloApi.queryBoardHttpResponse(TEST_BOARD_ID_1) {
                addName()
            }.status

            // Assert
            assertEquals(expected, actual)
        }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getBoards then return Boards`() =
        runBlocking {

            // Arrange
            val client = generateHttpClientWithMockEngine {
                when (url.encodedPath)
                {
                    "/v1/glo/boards" ->
                    {
                        generateMockHttpResponseFor(boardsJson)
                    }
                    else ->
                        generate404MockHttpResponse()
                }
            }

            val gloApi = GloApi(
                personalAuthenticationToken = TEST_PAT,
                logLevel = LogLevel.ALL,
                httpClient = client
            )
            val expected = listOf(
                Board(id = TEST_BOARD_ID_1, name = TEST_BOARD_NAME_1),
                Board(id = TEST_BOARD_ID_2, name = TEST_BOARD_NAME_2)
            )

            // Act
            val actual = gloApi.getBoards()

            // Assert
            assertEquals(expected, actual)
        }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getBoard then return Board`() =
        runBlocking {

            // Arrange
            val expected = Board(id = TEST_BOARD_ID_1, name = TEST_BOARD_NAME_1)
            val client = generateHttpClientWithMockEngine {
                when (url.encodedPath)
                {
                    "/v1/glo/boards/${expected.id}" ->
                    {
                        generateMockHttpResponseFor(boardJson)
                    }
                    else ->
                        generate404MockHttpResponse()
                }
            }

            val gloApi = GloApi(
                personalAuthenticationToken = TEST_PAT,
                logLevel = LogLevel.ALL,
                httpClient = client
            )

            // Act
            val actual = gloApi.getBoard(expected.id)

            // Assert
            assertEquals(expected, actual)
        }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getBoardHttpResponse then return HttpResponse`() =
        runBlocking {

            // Arrange
            val client = generateHttpClientWithMockEngine {
                when (url.encodedPath)
                {
                    "/v1/glo/boards/$TEST_BOARD_ID_1" ->
                    {
                        generateMockHttpResponseFor(boardJson)
                    }
                    else ->
                        generate404MockHttpResponse()
                }
            }

            val gloApi = GloApi(
                personalAuthenticationToken = TEST_PAT,
                logLevel = LogLevel.ALL,
                httpClient = client
            )

            val expected = HttpStatusCode.OK

            // Act
            val actual = gloApi.getBoardHttpResponse(TEST_BOARD_ID_1).status

            // Assert
            assertEquals(expected, actual)
        }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getBoardsHttpResponse then return HttpResponse`() =
        runBlocking {

            // Arrange
            val client = generateHttpClientWithMockEngine {
                when (url.encodedPath)
                {
                    "/v1/glo/boards" ->
                    {
                        generateMockHttpResponseFor(boardsJson)
                    }
                    else ->
                        generate404MockHttpResponse()
                }
            }

            val gloApi = GloApi(
                personalAuthenticationToken = TEST_PAT,
                logLevel = LogLevel.ALL,
                httpClient = client
            )

            val expected = HttpStatusCode.OK

            // Act
            val actual = gloApi.getBoardsHttpResponse().status

            // Assert
            assertEquals(expected, actual)
        }

}


