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

    open fun build(): QueryParameters = _parameters

}

typealias QueryParameters = Map<String, Set<String>>
