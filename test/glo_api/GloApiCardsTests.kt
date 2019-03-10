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

    private val cardJson =
        """{"id": "$TEST_CARD_ID_1",
            |"name": "$TEST_CARD_NAME_1"
            |"attachment_count": "5"}""".trimMargin()


    @KtorExperimentalAPI
    @Test
    fun `given PAT when getCards then return correct Cards`() =
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

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getCard then return correct Card`() =
        runBlocking {

            // Arrange
            val expected =
                Card(
                    id = TEST_CARD_ID_2,
                    name = TEST_CARD_NAME_2,
                    attachmentCount = 5
                )

            val client = generateHttpClientWithMockEngine {
                when (url.encodedPath)
                {
                    "/v1/glo/boards/$TEST_BOARD_ID_1/cards/$TEST_CARD_ID_1" ->
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
            val actual = gloApi.getCard(TEST_BOARD_ID_1, TEST_CARD_ID_1)

            // Assert
            assertEquals(expected, actual)
        }


//    @KtorExperimentalAPI
//    @Test
//    fun `given PAT when getCard with CardQuery then return correct Card`() =
//        runBlocking {
//
//            // Arrange
//            val client = generateHttpClientWithMockEngine {
//                when (url.parameters.contains(QUERY_KEY_FIELDS, QUERY_VALUE_ATTACHMENT_COUNT))
//                {
//                    true ->
//                    {
//                        generateMockHttpResponseFor(cardJson)
//                    }
//                    else ->
//                        generate404MockHttpResponse()
//                }
//            }
//
//            val gloApi = GloApi(
//                personalAuthenticationToken = TEST_PAT,
//                logLevel = LogLevel.ALL,
//                httpClient = client
//            )
//
//            val expected =
//                Board(
//                    id = TEST_BOARD_ID_1,
//                    name = TEST_BOARD_NAME_1
//                )
//
//
//            // Act
//            val actual = gloApi.queryBoard(TEST_BOARD_ID_1) {
//                addInvitedMembers()
//            }
//
//            // Assert
//            assertEquals(expected, actual)
//        }


}


