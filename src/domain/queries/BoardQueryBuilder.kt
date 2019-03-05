package domain.queries

import net.publicmethod.domain.queries.*

abstract class BoardQueryBuilder : QueryBuilder()
{

    private fun addFieldParameter(value: String) =
        addParameter(
            QUERY_KEY_FIELDS,
            value
        )

    fun addArchivedColumns() = addFieldParameter(
        QUERY_VALUE_ARCHIVED_COLUMNS
    )

    fun addArchivedDate() = addFieldParameter(
        QUERY_VALUE_ARCHIVED_DATE
    )

    fun addColumns() = addFieldParameter(
        QUERY_VALUE_COLUMNS
    )

    fun addCreatedBy() = addFieldParameter(
        QUERY_VALUE_CREATED_BY
    )

    fun addCreatedDate() = addFieldParameter(
        QUERY_VALUE_CREATED_DATE
    )

    fun addInvitedMembers() = addFieldParameter(
        QUERY_VALUE_INVITED_MEMBERS
    )

    fun addLabels() = addFieldParameter(
        QUERY_VALUE_LABELS
    )

    fun addMembers() = addFieldParameter(
        QUERY_VALUE_MEMBERS
    )

    fun addName() = addFieldParameter(
        QUERY_VALUE_NAME
    )

}