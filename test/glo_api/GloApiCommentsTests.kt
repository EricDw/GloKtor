package glo_api

import domain.data.Comment
import domain.data.CreatedBy
import domain.data.UpdatedBy
import io.ktor.client.features.logging.LogLevel
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import net.publicmethod.glo_api.GloApi
import kotlin.test.Test
import kotlin.test.assertEquals


class GloApiCommentsTests : GloApiTest
{

    private val commentsJson =
        """[{
              |"id": "$TEST_COMMENT_ID_1",
              |"card_id": "$TEST_CARD_ID_1",
              |"board_id": "$TEST_BOARD_ID_1",
              |"created_date": "Yesterday",
              |"updated_date": "Yesterday",
              |"created_by": {
              |"id": "$TEST_USER_ID_1"
             },"updated_by": {
              |"id": "$TEST_USER_ID_1"
             },"text":"$TEST_TEXT_1"
            },{
              |"id": "$TEST_COMMENT_ID_2",
              |"card_id": "$TEST_CARD_ID_1",
              |"board_id": "$TEST_BOARD_ID_1",
              |"created_date": "Yesterday",
              |"updated_date": "Yesterday",
              |"created_by": {
              |"id": "$TEST_USER_ID_2"
             },"updated_by": {
              |"id": "$TEST_USER_ID_2"
             },"text":"$TEST_TEXT_2"
            }]""".trimMargin()

    @KtorExperimentalAPI
    @Test
    fun `given PAT when getCommentsForCard then return correct Comments`() =
        runBlocking {

            // Arrange
            val expected = listOf(
                Comment(
                    id = TEST_COMMENT_ID_1,
                    cardId = TEST_CARD_ID_1,
                    boardId = TEST_BOARD_ID_1,
                    createdBy = CreatedBy(TEST_USER_ID_1),
                    createdDate = "Yesterday",
                    updatedDate = "Yesterday",
                    updatedBy = UpdatedBy(TEST_USER_ID_1),
                    text = TEST_TEXT_1
                ), Comment(
                    id = TEST_COMMENT_ID_2,
                    cardId = TEST_CARD_ID_1,
                    boardId = TEST_BOARD_ID_1,
                    createdBy = CreatedBy(TEST_USER_ID_2),
                    createdDate = "Yesterday",
                    updatedDate = "Yesterday",
                    updatedBy = UpdatedBy(TEST_USER_ID_2),
                    text = TEST_TEXT_2
                )
            )

            val client = generateHttpClientWithMockEngine {
                when (url.encodedPath)
                {
                    "/v1/glo/boards/$TEST_BOARD_ID_1/cards/$TEST_CARD_ID_1/comments" ->
                    {
                        generateMockHttpResponseFor(commentsJson)
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
            val actual = gloApi.getCommentsForCard(TEST_BOARD_ID_1, TEST_CARD_ID_1)

            // Assert
            assertEquals(expected, actual)
        }
//
//    @KtorExperimentalAPI
//    @Test
//    fun `given PAT when queryCommentsForCard then return correct Comments`() =
//        runBlocking {
//
//            // Arrange
//            val expected = listOf(
//                Attachment(
//                    id = TEST_ATTACHMENT_ID_1,
//                    filename = TEST_FILE_NAME_1,
//                    mimeType = "text/plain",
//                    createdBy = CreatedBy(TEST_USER_ID_1),
//                    createdDate = "Yesterday"
//                ), Attachment(
//                    id = TEST_ATTACHMENT_ID_2,
//                    filename = TEST_FILE_NAME_2,
//                    mimeType = "text/plain",
//                    createdBy = CreatedBy(TEST_USER_ID_2),
//                    createdDate = "Yesterday"
//                )
//            )
//
//            val client = generateHttpClientWithMockEngine {
//                when
//                {
//                    url.parameters.contains(QUERY_KEY_FIELDS, QUERY_VALUE_FILE_NAME) ->
//                    {
//                        generateMockHttpResponseFor(commentsJson)
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
//            // Act
//            val actual = gloApi.queryCommentsForCard(TEST_BOARD_ID_1, TEST_CARD_ID_1) {
//                addFileName()
//            }
//
//            // Assert
//            assertEquals(expected, actual)
//        }

}


