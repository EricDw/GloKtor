package domain.queries

import domain.queries.UserQueryBuilder.UserQueryParameter

class UserQueryBuilder : QueryBuilder<UserQueryParameter>()
{
    sealed class UserQueryParameter : QueryBuilder.QueryParameter()
    {
        object Name : UserQueryParameter()
        {
            override val key: String = "fields"
            override val value = "name"
        }

        object UserName : UserQueryParameter()
        {
            override val key: String = "fields"
            override val value = "username"
        }

        object CreatedDate : UserQueryParameter()
        {
            override val key: String = "fields"
            override val value = "created_date"
        }

        object Email : UserQueryParameter()
        {
            override val key: String = "fields"
            override val value = "email"
        }

    }
}