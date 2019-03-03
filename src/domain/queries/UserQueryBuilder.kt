package domain.queries

import domain.queries.UserQueryBuilder.UserQueryParameter
import net.publicmethod.domain.queries.*

class UserQueryBuilder : QueryBuilder<UserQueryParameter>()
{
    sealed class UserQueryParameter : QueryBuilder.QueryParameter()
    {
        object Name : UserQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value = QUERY_VALUE_NAME
        }

        object UserName : UserQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value = QUERY_VALUE_USER_NAME
        }

        object CreatedDate : UserQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value = QUERY_VALUE_CREATED_DATE
        }

        object Email : UserQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value = QUERY_VALUE_EMAIL
        }

    }
}