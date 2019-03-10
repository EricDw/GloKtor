package glo_api

import domain.data.Card
import io.ktor.client.features.logging.LogLevel
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import net.publicmethod.glo_api.GloApi
import kotlin.test.Test
import kotlin.test.assertEquals


class GloApiCardsTests : GloApiTest
{

    private val cardsJson =
        """[
            |{"id": "$TEST_CARD_ID_1",
            |"name": "$TEST_CARD_NAME_1"},
            |{"id": "$TEST_CARD_ID_2",
            |"name": "$TEST_CARD_NAME_2"}]""".trimMargin()


    @KtorExperimentalAPI
    @Test
    fun `given PAT when getCards then return Columns`() =
        runBlocking {

            // Arrange
            val expected = listOf(
                Card(
                    id = TEST_CARD_ID_1, name = TEST_CARD_NAME_1
                ),
                Card(
                    id = TEST_CARD_ID_2, name = TEST_CARD_NAME_2
                )
            )
            val client = generateHttpClientWithMockEngine {
                when (url.encodedPath)
                {
                    "/v1/glo/boards/$TEST_BOARD_ID_1/cards" ->
                    {
                        generateMockHttpResponseFor(cardsJson)
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
            val actual = gloApi.getCards(TEST_BOARD_ID_1)

            // Assert
            assertEquals(expected, actual)
        }


}


