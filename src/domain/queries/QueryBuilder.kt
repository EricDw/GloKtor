package domain.queries

abstract class QueryBuilder<T : QueryBuilder.QueryParameter>
{
    private val _parameters: MutableMap<String, MutableSet<String>> = mutableMapOf()

    fun addParameter(userQueryParameter: T): Unit =
        userQueryParameter.run {
            if (_parameters.containsKey(key))
            {
                _parameters[key]?.add(userQueryParameter.value)
            } else
            {
                _parameters[key] = mutableSetOf(userQueryParameter.value)
            }
        }

    fun build(): Query = Query(_parameters)

    abstract class QueryParameter
    {
        abstract val key: String
        abstract val value: String
    }
}

typealias QueryParameters = Map<String, Set<String>>

data class Query(val queryParameters: QueryParameters = mapOf())
