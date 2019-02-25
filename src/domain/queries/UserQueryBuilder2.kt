package domain.queries

import domain.queries.UserQueryBuilder2.UserQueryParameter2

class UserQueryBuilder2 : QueryBuilder<UserQueryParameter2>()
{
    sealed class UserQueryParameter2 : QueryBuilder.QueryParameter()
    {
        object Name2 : UserQueryParameter2()
        {
            override val key: String = "fields"
            override val value = "name"
        }

        object UserName2 : UserQueryParameter2()
        {
            override val key: String = "fields"
            override val value = "username"
        }

        object CreatedDate2 : UserQueryParameter2()
        {
            override val key: String = "fields"
            override val value = "created_date"
        }

        object Email2 : UserQueryParameter2()
        {
            override val key: String = "fields"
            override val value = "email"
        }

    }
}