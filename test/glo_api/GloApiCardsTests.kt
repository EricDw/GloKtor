package glo_api

import domain.data.Card
import io.ktor.client.features.logging.LogLevel
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import net.publicmethod.domain.queries.QUERY_KEY_FIELDS
import net.publicmethod.domain.queries.QUERY_KEY_PAGE
import net.publicmethod.domain.queries.QUERY_VALUE_ATTACHMENT_COUNT
import net.publicmethod.glo_api.GloApi
import kotlin.test.Test
import kotlin.test.assertEquals


class GloApiCardsTests : GloApiTest
{

    private val cardsJson =
        """[
            |{"id": "$TEST_CARD_ID_1",
            |"name": "$TEST_CARD_NAME_1",
            |"attachment_count: "5"},
            |{"id": "$TEST_CARD_ID_2",
            |"name": "$TEST_CARD_NAME_2",
            |"attachment_count: "2"}]""".trimMargin()

    private val cardJson =
        """{"id": "$TEST_CARD_ID_1",
            |"name": "$TEST_CARD_NAME_1",
            |"attachment_count": "5"}""".trimMargin()


    @KtorExperimentalAPI
    @Test
    fun `given PAT when getCards then return correct Cards`() =
        runBlocking {

            // Arrange
            val expected = listOf(
                Card(
                    id = TEST_CARD_ID_1,
                    name = TEST_CARD_NAME_1,
                    attachmentCount = 5
                ),
                Card(
                    id = TEST_CARD_ID_2,
                    name = TEST_CARD_NAME_2,
                    attachmentCount = 2
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
                    id = TEST_CARD_ID_1,
                    name = TEST_CARD_NAME_1,
                    attachmentCount = 5
                )

            val client = generateHttpClientWithMockEngine {
                when (url.encodedPath)
                {
                    "/v1/glo/boards/$TEST_BOARD_ID_1/cards/$TEST_CARD_ID_1" ->
                    {
                        generateMockHttpResponseFor(cardJson)
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


    @KtorExperimentalAPI
    @Test
    fun `given PAT when getCard with CardQuery then return correct Card`() =
        runBlocking {

            // Arrange
            val client = generateHttpClientWithMockEngine {
                when (url.parameters.contains(QUERY_KEY_FIELDS, QUERY_VALUE_ATTACHMENT_COUNT))
                {
                    true ->
                    {
                        generateMockHttpResponseFor(cardJson)
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

            val expected =
                Card(
                    id = TEST_CARD_ID_1,
                    name = TEST_CARD_NAME_1,
                    attachmentCount = 5
                )


            // Act
            val actual = gloApi.queryCard(TEST_BOARD_ID_1, TEST_CARD_ID_1) {
                addAttachmentCount()
            }

            // Assert
            assertEquals(expected, actual)
        }

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getCards with CardsQuery then return correct Cards`() =
        runBlocking {

            // Arrange
            val client = generateHttpClientWithMockEngine {
                when (url.parameters.contains(QUERY_KEY_PAGE))
                {
                    true ->
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

            val expected =
                arrayOf(
                    Card(
                        id = TEST_CARD_ID_1,
                        name = TEST_CARD_NAME_1,
                        attachmentCount = 5
                    ),
                    Card(
                        id = TEST_CARD_ID_2,
                        name = TEST_CARD_NAME_2,
                        attachmentCount = 2
                    )
                )


            // Act
            val actual = gloApi.queryCards(TEST_BOARD_ID_1) {
                addPage(2)
            }

            // Assert
            assertEquals(expected, actual)
        }

}


