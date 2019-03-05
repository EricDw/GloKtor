package domain.queries

abstract class QueryBuilder
{
    private val _parameters: MutableMap<String, MutableSet<String>> = mutableMapOf()

    protected fun addParameter(key: String, value: String)
    {
        if (_parameters.containsKey(key))
            _parameters[key]?.add(value)
        else
            _parameters[key] = mutableSetOf(value)
    }

    fun build(): QueryParameters2 = _parameters

}

typealias QueryParameters2 = Map<String, Set<String>>
