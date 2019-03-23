package domain.queries

import net.publicmethod.domain.queries.*

class CommentsQueryBuilder : PluralQueryBuilder()
{

    private fun addFieldParameter(value: String) =
        addParameter(
            QUERY_KEY_FIELDS,
            value
        )

    fun addCreatedBy() = addFieldParameter(
        QUERY_VALUE_CREATED_BY
    )

    fun addCreatedDate() = addFieldParameter(
        QUERY_VALUE_CREATED_DATE
    )

    fun addUpdatedDate() = addFieldParameter(
        QUERY_VALUE_UPDATED_DATE
    )

    fun addBoardId() = addFieldParameter(
        QUERY_VALUE_BOARD_ID
    )

    fun addUpdatedBy() = addFieldParameter(
        QUERY_VALUE_UPDATED_BY
    )

    fun addText() = addFieldParameter(
        QUERY_VALUE_TEXT
    )

    fun addCardId() = addFieldParameter(
        QUERY_VALUE_CARD_ID
    )

}