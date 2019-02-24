package glo_api

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpRequest
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.client.engine.mock.responseError
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import net.publicmethod.domain.Board
import net.publicmethod.domain.GloUser
import net.publicmethod.glo_api.GloApi
import net.publicmethod.glo_api.UserQueryBuilder
import kotlin.test.Test
import kotlin.test.assertEquals

private const val TEST_ID = "some-gi-ber-ish"
private const val TEST_USERNAME = "Test User"
private const val TEST_NAME = "test name"
private const val TEST_EMAIL = "test@test.com"
private const val TEST_CREATED_DATE = "Yesterday"
private const val TEST_PAT = "test-pat"

private const val QUERY_NAME_KEY = "name"
private const val QUERY_NAME_VALUE = "name"

private const val QUERY_EMAIL_KEY = "email"
private const val QUERY_EMAIL_VALUE = "email"

class GloApiTests
{

    private val userJson =
        """{"id":"$TEST_ID",
            |"username":"$TEST_USERNAME",
            |"name":"$TEST_NAME",
            |"created_date":"$TEST_CREATED_DATE",
            |"email":"$TEST_EMAIL"}""".trimMargin()

    private val partialUserJson =
        """{"id":"$TEST_ID",
            |"name":"$TEST_NAME"}""".trimMargin()

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getUser with params then return correct GloUserDTO`() = runBlocking {

        // Arrange
        val client = generateHttpClientWithMockEngine {
            when (url.parameters.contains(QUERY_NAME_KEY, QUERY_NAME_VALUE))
            {
                true ->
                {
                    generateMockHttpResponseFor(partialUserJson)
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

        val expected = GloUser(
            id = TEST_ID,
            name = TEST_NAME
        )
        val input = mapOf(
            QUERY_NAME_KEY to QUERY_NAME_VALUE
        )

        // Act
        val actual = gloApi.getUser(input)

        // Assert
        assertEquals(expected, actual)
    }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getUser with UserQuery then return correct GloUserDTO`() = runBlocking {

        // Arrange
        val client = generateHttpClientWithMockEngine {
            when (url.parameters.contains(QUERY_NAME_KEY, QUERY_NAME_VALUE))
            {
                true ->
                {
                    generateMockHttpResponseFor(partialUserJson)
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

        val expected = GloUser(
            id = TEST_ID,
            name = TEST_NAME
        )
        val input = UserQueryBuilder.UserQueryParameter.Name

        // Act
        val actual = gloApi.queryUser {
            addParameter(input)
        }

        // Assert
        assertEquals(expected, actual)
    }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when queryUser with all params then return correct GloUserDTO`() = runBlocking {

        // Arrange
        val client = generateHttpClientWithMockEngine {
            when (url.parameters.contains(QUERY_EMAIL_KEY, QUERY_EMAIL_VALUE))
            {
                true ->
                {
                    generateMockHttpResponseFor(userJson)
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

        val expected = GloUser(
            id = TEST_ID,
            userName = TEST_USERNAME,
            name = TEST_NAME,
            email = TEST_EMAIL,
            createdDate = TEST_CREATED_DATE
        )
        val name = UserQueryBuilder.UserQueryParameter.Name
        val userName = UserQueryBuilder.UserQueryParameter.UserName
        val createdDate = UserQueryBuilder.UserQueryParameter.CreatedDate
        val email = UserQueryBuilder.UserQueryParameter.Email

        // Act
        val actual = gloApi.queryUser {
            addParameter(name)
            addParameter(userName)
            addParameter(createdDate)
            addParameter(email)
        }

        // Assert
        assertEquals(expected, actual)
    }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getUser then return GloUserDTO`() = runBlocking {

        // Arrange
        val client = generateHttpClientWithMockEngine {
            when (url.encodedPath)
            {
                "/v1/glo/user" ->
                {
                    generateMockHttpResponseFor(userJson)
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

        val expected = GloUser(
            id = TEST_ID,
            userName = TEST_USERNAME,
            name = TEST_NAME,
            email = TEST_EMAIL,
            createdDate = TEST_CREATED_DATE
        )

        // Act
        val actual = gloApi.getUser()

        // Assert
        assertEquals(expected, actual)
    }

    private val boardsJson =
        """[{"name":"Test Board1","id":"some-gi-ber-ish1"},{"name":"Test Board2","id":"some-gi-ber-ish2"}]"""


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

    private val boardJson =
        """{"name":"Test Board1","id":"some-gi-ber-ish1"}"""

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
    fun `given PAT when getUserHttpResponse then return HttpResponse`() = runBlocking {

        // Arrange
        val client = generateHttpClientWithMockEngine {
            when (url.encodedPath)
            {
                "/v1/glo/user" ->
                {
                    generateMockHttpResponseFor(userJson)
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
        val actual = gloApi.getUserHttpResponse().status

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

private fun generateHttpClientWithMockEngine(block: MockHttpRequest.() -> MockHttpResponse): HttpClient
{
    return HttpClient(MockEngine {
        block()
    }) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        expectSuccess = false
    }
}

private fun MockHttpRequest.generateMockHttpResponseFor(json: String): MockHttpResponse
{
    return MockHttpResponse(
        call,
        HttpStatusCode.OK,
        ByteReadChannel(json.toByteArray(Charsets.UTF_8)),
        headersOf("Content-Type", ContentType.Application.Json.toString())
    )
}

private fun MockHttpRequest.generate404MockHttpResponse() =
    responseError(HttpStatusCode.NotFound, "Not Found ${url.encodedPath}")


