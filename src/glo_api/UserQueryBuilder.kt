package net.publicmethod.glo_api

class UserQueryBuilder
{
    private val _parameters: MutableSet<UserQueryParameter> = mutableSetOf()

    fun addParameter(userQueryParameter: UserQueryParameter) =
        userQueryParameter.run {
            _parameters.add(userQueryParameter)
        }

    fun build(): UserQuery =
        UserQuery(_parameters)

    sealed class UserQueryParameter
    {
        object Name : UserQueryParameter()
        {
            const val key = "name"
            const val value = "name"
        }

        object UserName : UserQueryParameter()
        {
            const val key = "username"
            const val value = "username"
        }

        object CreatedDate : UserQueryParameter()
        {
            const val key = "created_date"
            const val value = "created_date"
        }

        object Email : UserQueryParameter()
        {
            const val key = "email"
            const val value = "email"
        }

    }

}

typealias UserQueryParameters = Set<UserQueryBuilder.UserQueryParameter>

data class UserQuery(val userQueryParameters: UserQueryParameters = setOf())
