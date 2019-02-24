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

    }

}

typealias UserQueryParameters = Set<UserQueryBuilder.UserQueryParameter>

data class UserQuery(val userQueryParameters: UserQueryParameters = setOf())

fun buildUserQuery(init: UserQueryBuilder.() -> Unit): UserQuery =
    UserQueryBuilder().apply(init).build()
