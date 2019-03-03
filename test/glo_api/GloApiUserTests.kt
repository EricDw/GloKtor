package glo_api

import domain.data.GloUser
import domain.queries.UserQueryBuilder
import domain.queries.UserQueryBuilder.UserQueryParameter.*
import io.ktor.client.features.logging.LogLevel
import io.ktor.http.HttpStatusCode
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import net.publicmethod.glo_api.GloApi
import kotlin.test.Test
import kotlin.test.assertEquals

private const val TEST_USERNAME = "Test User"
private const val TEST_EMAIL = "test@test.com"
private const val TEST_CREATED_DATE = "Yesterday"

private const val QUERY_VALUE_NAME = "name"

private const val QUERY_EMAIL_VALUE = "email"

class GloApiUserTests : GloApiTest
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
    fun `given PAT when getUser with UserQuery then return correct GloUserDTO`() = runBlocking {

        // Arrange
        val client = generateHttpClientWithMockEngine {
            when (url.parameters.contains(QUERY_KEY_FIELDS, QUERY_VALUE_NAME))
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
        val input = Name

        // Act
        val actual = gloApi.queryUser {
            addParameter(input)
        }

        // Assert
        assertEquals(expected, actual)
    }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when queryUser with Query then return correct GloUserDTO`() = runBlocking {

        // Arrange
        val client = generateHttpClientWithMockEngine {
            when (url.parameters.contains(QUERY_KEY_FIELDS, QUERY_VALUE_NAME))
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
            when (url.parameters.contains(QUERY_KEY_FIELDS, QUERY_EMAIL_VALUE))
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
        val name = Name
        val userName = UserName
        val createdDate = CreatedDate
        val email = Email

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

    @KtorExperimentalAPI
    @Test
    fun `given PAT when queryUserHttpResponse then return HttpResponse`() = runBlocking {

        // Arrange
        val client = generateHttpClientWithMockEngine {
            when (url.encodedPath)
            {
                "/v1/glo/user" ->
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

        val expected = HttpStatusCode.OK

        // Act
        val actual = gloApi.queryUserHttpResponse {
            addParameter(Name)
        }.status

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
}



