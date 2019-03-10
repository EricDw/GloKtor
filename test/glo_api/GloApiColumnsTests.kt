package glo_api

import domain.data.Column
import io.ktor.client.features.logging.LogLevel
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import net.publicmethod.glo_api.GloApi
import kotlin.test.Test
import kotlin.test.assertEquals


private const val QUERY_VALUE_INVITED_MEMBERS = "invited_members"


class GloApiColumnsTests : GloApiTest
{

    private val boardJson =
        """{"name":"$TEST_BOARD_NAME_1",
            |"id":"$TEST_BOARD_ID_1"}""".trimMargin()

    private val columnsJson =
        """[{"name":"$TEST_COLUMN_NAME_1",
            |"id":"$TEST_COLUMN_ID_1",
            |{"name":"$TEST_COLUMN_NAME_2",
            |"id":"$TEST_COLUMN_ID_2"}]""".trimMargin()

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getColumns then return ColumnsDTO`() =
        runBlocking {

            // Arrange
            val expected = listOf(
                Column(
                    id = TEST_COLUMN_ID_1, name = TEST_COLUMN_NAME_1
                )
            )
            val client = generateHttpClientWithMockEngine {
                when (url.encodedPath)
                {
                    "/v1/glo/boards/$TEST_BOARD_ID_1" ->
                    {
                        generateMockHttpResponseFor(columnsJson)
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
            val actual = gloApi.getColumns(TEST_BOARD_ID_1)

            // Assert
            assertEquals(expected, actual)
        }


}


