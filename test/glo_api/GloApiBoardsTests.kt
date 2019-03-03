package glo_api

import domain.data.Board
import io.ktor.client.features.logging.LogLevel
import io.ktor.http.HttpStatusCode
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import net.publicmethod.glo_api.GloApi
import kotlin.test.Test
import kotlin.test.assertEquals


private const val QUERY_VALUE_INVITED_MEMBERS = "invited_members"


class GloApiBoardsTests : GloApiTest
{
/*
* archived_columns
* archived_date
* columns
* created_by
* created_date
* invited_members
* labels
* members
* name
* archived: Boolean
* page: Int
* per_page: Int 1..99
* sort: String
*
* All params URL
* https://gloapi.gitkraken.com/v1/glo/boards
* ?fields=archived_columns
* &fields=archived_date
* &fields=columns
* &fields=created_by
* &fields=created_date
* &fields=invited_members
* &fields=labels
* &fields=members
* &fields=name
* &archived=true
* &page=1
* &per_page=99
* &sort=asc
*
* */

    private val boardJson =
        """{"name":"$TEST_BOARD_NAME_1","id":"$TEST_BOARD_ID_1"}"""

    private val boardsJson =
        """[{"name":"$TEST_BOARD_NAME_1",
            |"id":"$TEST_BOARD_ID_1",
            |"invited_members":[]},
            |{"name":"$TEST_BOARD_NAME_2",
            |"id":"$TEST_BOARD_ID_2"}]""".trimMargin()


    @KtorExperimentalAPI
    @Test
    fun `given PAT when getBoards with BoardsQuery then return correct GloBoardDTOs`() = runBlocking {

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
        val input = BoardsQueryBuilder.BoardsQueryParameter.InvitedMembers

        // Act
        val actual = gloApi.queryBoards {
            addParameter(input)
        }

        // Assert
        assertEquals(expected, actual)
    }


    @KtorExperimentalAPI
    @Test
    fun `given PAT when getBoards then return GloBoardDTOs`() = runBlocking {

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
            Board(id = "some-gi-ber-ish1", name = "Test Board1"),
            Board(id = "some-gi-ber-ish2", name = "Test Board2")
        )

        // Act
        val actual = gloApi.getBoards()

        // Assert
        assertEquals(expected, actual)
    }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getBoard then return GloBoardDTO`() = runBlocking {

        // Arrange
        val expected = Board(id = "some-gi-ber-ish1", name = "Test Board1")
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
    fun `given PAT when getBoardHttpResponse then return HttpResponse`() = runBlocking {

        // Arrange
        val client = generateHttpClientWithMockEngine {
            when (url.encodedPath)
            {
                "/v1/glo/boards/${"some-gi-ber-ish1"}" ->
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
        val actual = gloApi.getBoardHttpResponse("some-gi-ber-ish1").status

        // Assert
        assertEquals(expected, actual)
    }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getBoardsHttpResponse then return HttpResponse`() = runBlocking {

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


