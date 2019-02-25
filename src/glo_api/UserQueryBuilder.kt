package net.publicmethod.glo_api

class UserQueryBuilder
{
    private val _parameters: MutableSet<UserQueryParameter> = mutableSetOf()
    private val _parameters2: Pair<String, MutableSet<String>> = Pair("fields", mutableSetOf())

    fun addParameter(userQueryParameter: UserQueryParameter) =
        userQueryParameter.run {
            _parameters2.second.add(userQueryParameter.value)
            _parameters.add(userQueryParameter)
        }

    fun build(): UserQuery =
        UserQuery(_parameters2)

    sealed class UserQueryParameter
    {
        abstract val value: String

        object Name : UserQueryParameter()
        {
            override val value = "name"
        }

        object UserName : UserQueryParameter()
        {
            override val value = "username"
        }

        object CreatedDate : UserQueryParameter()
        {
            override val value = "created_date"
        }

        object Email : UserQueryParameter()
        {
            override val value = "email"
        }

    }

}

typealias UserQueryParameters = Pair<String, Set<String>>

data class UserQuery(val userQueryParameters: UserQueryParameters = Pair("", setOf()))
