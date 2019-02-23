package net.publicmethod

import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.client.engine.mock.responseError
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest
{
    @Test
    fun testClientMock()
    {
        runBlocking {
            val client = HttpClient(MockEngine {
                if (url.encodedPath == "/")
                {
                    MockHttpResponse(
                        call,
                        HttpStatusCode.OK,
                        ByteReadChannel(byteArrayOf(1, 2, 3)),
                        headersOf("X-MyHeader", "MyValue")
                    )
                } else
                {
                    responseError(HttpStatusCode.NotFound, "Not Found ${url.encodedPath}")
                }
            }) {
                expectSuccess = false
            }
            assertEquals(byteArrayOf(1, 2, 3).toList(), client.get<ByteArray>("/").toList())
            assertEquals("MyValue", client.call("/").response.headers["X-MyHeader"])
            assertEquals("Not Found other/path", client.get<String>("/other/path"))
        }
    }
}
