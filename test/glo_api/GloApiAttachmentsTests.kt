package glo_api

import domain.data.Attachment
import domain.data.CreatedBy
import io.ktor.client.features.logging.LogLevel
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import net.publicmethod.glo_api.GloApi
import kotlin.test.Test
import kotlin.test.assertEquals


class GloApiAttachmentsTests : GloApiTest
{

    private val attachmentsJson =
        """[{
              |"id": "$TEST_ATTACHMENT_ID_1",
              |"filename": "$TEST_FILE_NAME_1",
              |"mime_type": "text/plain",
              |"created_date": "Yesterday",
              |"created_by": {
              |"id": "$TEST_USER_ID_1"
             }
            },{
              |"id": "$TEST_ATTACHMENT_ID_2",
              |"filename": "$TEST_FILE_NAME_2",
              |"mime_type": "text/plain",
              |"created_date": "Yesterday",
              |"created_by": {
              |"id": "$TEST_USER_ID_2"
             }
            }]""".trimMargin()

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getAttachments then return correct Attachments`() =
        runBlocking {

            // Arrange
            val expected = listOf(
                Attachment(
                    id = TEST_ATTACHMENT_ID_1,
                    filename = TEST_FILE_NAME_1,
                    mimeType = "text/plain",
                    createdBy = CreatedBy(TEST_USER_ID_1),
                    createdDate = "Yesterday"
                ), Attachment(
                    id = TEST_ATTACHMENT_ID_2,
                    filename = TEST_FILE_NAME_2,
                    mimeType = "text/plain",
                    createdBy = CreatedBy(TEST_USER_ID_2),
                    createdDate = "Yesterday"
                )
            )

            val client = generateHttpClientWithMockEngine {
                when (url.encodedPath)
                {
                    "/v1/glo/boards/$TEST_BOARD_ID_1/cards" ->
                    {
                        generateMockHttpResponseFor(attachmentsJson)
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
            val actual = gloApi.getAttachmentsForCard(TEST_BOARD_ID_1, TEST_CARD_ID_1)

            // Assert
            assertEquals(expected, actual)
        }

}


