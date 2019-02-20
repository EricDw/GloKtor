package net.publicmethod

import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.client.engine.mock.responseError
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import net.publicmethod.dtos.GloUserDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class GloApiTests
{

    @Test
    fun `given user endpoint when getUser then return GloUserDTO`()
    {
        // Arrange
        val userJson = """{"id":"some-gi-ber-ish","username":"Test User"}"""
        val expected = GloUserDTO(id = "some-gi-ber-ish", userName = "Test User")

        runBlocking {
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
    }
}

