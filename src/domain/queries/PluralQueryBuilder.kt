package domain.queries

import net.publicmethod.domain.queries.QUERY_KEY_ARCHIVED
import net.publicmethod.domain.queries.QUERY_KEY_PAGE
import net.publicmethod.domain.queries.QUERY_KEY_PER_PAGE
import net.publicmethod.domain.queries.QUERY_KEY_SORT

abstract class PluralQueryBuilder : QueryBuilder()
{

    fun addArchived() = addParameter(
        QUERY_KEY_ARCHIVED,
        "true"
    )

    @Throws(IllegalAccessException::class)
    fun addPage(
        pageNumber: Int = 1
    )
    {

        if (pageNumber < 1)
            throw IllegalArgumentException(
                "The given page number: $pageNumber, must not be less then 1!"
            )

        addParameter(
            QUERY_KEY_PAGE,
            pageNumber.toString()
        )

    }

    @Throws(IllegalAccessException::class)
    fun addPerPage(
        resultsPerPage: Int = 50
    )
    {

        if (resultsPerPage < 1 || resultsPerPage > 100)
            throw IllegalArgumentException(
                "The given results per page: $resultsPerPage, must be in the range of 1 to 100!"
            )

        addParameter(
            QUERY_KEY_PER_PAGE,
            resultsPerPage.toString()
        )
    }

    fun addSort(
        isAscending: Boolean = false
    )
    {
        addParameter(
            QUERY_KEY_SORT,
            if (isAscending) "asc" else "desc"
        )
    }
}