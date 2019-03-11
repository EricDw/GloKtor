package domain.queries

import net.publicmethod.domain.queries.*

abstract class CardQueryBuilder : QueryBuilder()
{

    private fun addFieldParameter(value: String) =
        addParameter(
            QUERY_KEY_FIELDS,
            value
        )

    fun addAssignees() = addFieldParameter(
        QUERY_VALUE_ASSIGNEES
    )

    fun addArchivedDate() = addFieldParameter(
        QUERY_VALUE_ARCHIVED_DATE
    )

    fun addAttachmentCount() = addFieldParameter(
        QUERY_VALUE_ATTACHMENT_COUNT
    )

    fun addBoardId() = addFieldParameter(
        QUERY_VALUE_BOARD_ID
    )

    fun addColumnId() = addFieldParameter(
        QUERY_VALUE_COLUMN_ID
    )

    fun addCommentCount() = addFieldParameter(
        QUERY_VALUE_COMMENT_COUNT
    )

    fun addCompletedTaskCount() = addFieldParameter(
        QUERY_VALUE_COMPLETED_TASK_COUNT
    )

    fun addCreatedBy() = addFieldParameter(
        QUERY_VALUE_CREATED_BY
    )

    fun addCreatedDate() = addFieldParameter(
        QUERY_VALUE_CREATED_DATE
    )

    fun addDueDate() = addFieldParameter(
        QUERY_VALUE_DUE_DATE
    )

    fun addDescription() = addFieldParameter(
        QUERY_VALUE_DESCRIPTION
    )

    fun addLabels() = addFieldParameter(
        QUERY_VALUE_LABELS
    )

    fun addName() = addFieldParameter(
        QUERY_VALUE_NAME
    )

    fun addTotalTaskCount() = addFieldParameter(
        QUERY_VALUE_TOTAL_TASK_COUNT
    )

    fun addUpdatedDate() = addFieldParameter(
        QUERY_VALUE_UPDATED_DATE
    )

}