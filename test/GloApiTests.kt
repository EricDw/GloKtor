package net.publicmethod

import dtos.GloBoardDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.client.engine.mock.responseError
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import net.publicmethod.dtos.GloUserDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GloApiTests
{

    private val userJson = """{"id":"some-gi-ber-ish","username":"Test User"}"""

    @Test
    fun `given user endpoint when getUser then return GloUserDTO`() =
        runBlocking {
            // Arrange
            val expected = GloUserDTO(id = "some-gi-ber-ish", name = "Test User")

            val client = HttpClient(MockEngine {
                when (url.encodedPath)
                {
                    "user" ->
                    {
                        MockHttpResponse(
                            call,
                            HttpStatusCode.OK,
                            ByteReadChannel(userJson.toByteArray(Charsets.UTF_8)),
                            headersOf("Content-Type", ContentType.Application.Json.toString())
                        )
                    }
                    else ->
                        responseError(HttpStatusCode.NotFound, "Not Found ${url.encodedPath}")
                }
            }) {
                install(JsonFeature) {
                    serializer = GsonSerializer()
                }
                expectSuccess = false
            }
            // Act
            val actual = client.get<GloUserDTO>("/user")


            // Assert
            assertEquals(expected, actual)
            assertEquals("application/json", client.call("/user").response.headers["Content-Type"])
            assertEquals("Not Found other/path", client.get("/other/path"))
        }


    @KtorExperimentalAPI
    @Test
    fun `given PAT when getUser then return GloUserDTO`() = runBlocking {

        // Arrange
        val client = HttpClient(MockEngine {
            when (url.encodedPath)
            {
                "/v1/glo/user" ->
                {
                    MockHttpResponse(
                        call,
                        HttpStatusCode.OK,
                        ByteReadChannel(userJson.toByteArray(Charsets.UTF_8)),
                        headersOf("Content-Type", ContentType.Application.Json.toString())
                    )
                }
                else ->
                    responseError(HttpStatusCode.NotFound, "Not Found ${url.encodedPath}")
            }
        }) {

            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            expectSuccess = false
        }

        val gloApi = GloApi(
            personalAuthenticationToken = "test-pat",
            logLevel = LogLevel.ALL,
            httpClient = client
        )
        val expected = GloUserDTO(id = "some-gi-ber-ish", name = "Test User")

        // Act
        val actual = gloApi.getUser()

        // Assert
        assertEquals(expected, actual)
    }

    val boardsJson =
        """[{"name":"Test Board1","id":"some-gi-ber-ish1"},{"name":"Test Board2","id":"some-gi-ber-ish2"}]"""

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getBoards then return GloBoardDTOs`() = runBlocking {

        // Arrange
        val client = HttpClient(MockEngine {
            when (url.encodedPath)
            {
                "/v1/glo/boards" ->
                {
                    MockHttpResponse(
                        call,
                        HttpStatusCode.OK,
                        ByteReadChannel(boardsJson.toByteArray(Charsets.UTF_8)),
                        headersOf("Content-Type", ContentType.Application.Json.toString())
                    )
                }
                else ->
                    responseError(HttpStatusCode.NotFound, "Not Found ${url.encodedPath}")
            }
        }) {

            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            expectSuccess = false
        }

        val gloApi = GloApi(
            personalAuthenticationToken = "test-pat",
            logLevel = LogLevel.ALL,
            httpClient = client
        )
        val expected = listOf(
            GloBoardDTO(id = "some-gi-ber-ish1", name = "Test Board1"),
            GloBoardDTO(id = "some-gi-ber-ish2", name = "Test Board2")
        )

        // Act
        val actual = gloApi.getBoards()

        // Assert
        assertEquals(expected, actual)
    }


    val boardJson =
        """{"name":"Test Board1","id":"some-gi-ber-ish1"}"""

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getBoard then return GloBoardDTO`() = runBlocking {

        // Arrange
        val expected = GloBoardDTO(id = "some-gi-ber-ish1", name = "Test Board1")
        val client = HttpClient(MockEngine {
            when (url.encodedPath)
            {
                "/v1/glo/boards/${expected.id}" ->
                {
                    MockHttpResponse(
                        call,
                        HttpStatusCode.OK,
                        ByteReadChannel(boardJson.toByteArray(Charsets.UTF_8)),
                        headersOf("Content-Type", ContentType.Application.Json.toString())
                    )
                }
                else ->
                    responseError(HttpStatusCode.NotFound, "Not Found ${url.encodedPath}")
            }
        }) {

            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            expectSuccess = false
        }

        val gloApi = GloApi(
            personalAuthenticationToken = "test-pat",
            logLevel = LogLevel.ALL,
            httpClient = client
        )

        // Act
        val actual = gloApi.getBoard(expected.id)

        // Assert
        assertEquals(expected, actual)
    }
}


