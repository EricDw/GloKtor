package domain.queries

import net.publicmethod.domain.queries.*

class AttachmentsQueryBuilder : PluralQueryBuilder()
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

    fun addFileName() = addFieldParameter(
        QUERY_VALUE_FILE_NAME
    )

    fun addMimeType() = addFieldParameter(
        QUERY_VALUE_MIME_TYPE
    )

}