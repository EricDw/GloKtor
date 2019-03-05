package domain.queries

import net.publicmethod.domain.queries.*

class UserQueryBuilder : QueryBuilder()
{
    private fun addFieldParameter(value: String) =
        addParameter(
            QUERY_KEY_FIELDS,
            value
        )

    fun addName() = addFieldParameter(
        QUERY_VALUE_NAME
    )

    fun addUserName() = addFieldParameter(
        QUERY_VALUE_USER_NAME
    )

    fun addCreatedDate() = addFieldParameter(
        QUERY_VALUE_CREATED_DATE
    )

    fun addEmail() = addFieldParameter(
        QUERY_VALUE_EMAIL
    )

}
