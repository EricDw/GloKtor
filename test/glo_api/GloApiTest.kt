package glo_api

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpRequest
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.client.engine.mock.responseError
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.io.ByteReadChannel

interface GloApiTest
{

    fun generateHttpClientWithMockEngine(block: MockHttpRequest.() -> MockHttpResponse): HttpClient
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

    fun MockHttpRequest.generateMockHttpResponseFor(json: String): MockHttpResponse
    {
        return MockHttpResponse(
            call,
            HttpStatusCode.OK,
            ByteReadChannel(json.toByteArray(Charsets.UTF_8)),
            headersOf("Content-Type", ContentType.Application.Json.toString())
        )
    }

    fun MockHttpRequest.generate404MockHttpResponse() =
        responseError(HttpStatusCode.NotFound, "Not Found ${url.encodedPath}")

}