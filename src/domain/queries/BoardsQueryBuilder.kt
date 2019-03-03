package domain.queries

import domain.queries.BoardsQueryBuilder.BoardsQueryParameter
import net.publicmethod.domain.queries.*

class BoardsQueryBuilder : QueryBuilder<BoardsQueryParameter>()
{
    sealed class BoardsQueryParameter : QueryBuilder.QueryParameter()
    {

        /*
* archived_columns
* archived_date
* columns
* created_by
* created_date
* invited_members
* labels
* members
* name
*
*
*
*
*
* All params URL
* https://gloapi.gitkraken.com/v1/glo/boards
* ?fields=archived_columns
* &fields=archived_date
* &fields=columns
* &fields=created_by
* &fields=created_date
* &fields=invited_members
* &fields=labels
* &fields=members
* &fields=name
*
*
*
*
*
* */

        object ArchivedColumns : BoardsQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value = QUERY_VALUE_ARCHIVED_COLUMNS
        }

        object ArchivedDate : BoardsQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value: String = QUERY_VALUE_ARCHIVED_DATE
        }

        object Columns : BoardsQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value: String = QUERY_VALUE_COLUMNS
        }

        object CreatedBy : BoardsQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value: String = QUERY_VALUE_CREATED_BY
        }

        object CreatedDate : BoardsQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value: String = QUERY_VALUE_CREATED_DATE
        }

        object InvitedMembers : BoardsQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value: String = QUERY_VALUE_INVITED_MEMBERS
        }

        object Labels : BoardsQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value: String = QUERY_VALUE_LABELS
        }

        object Members : BoardsQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value: String = QUERY_VALUE_MEMBERS
        }

        object Name : BoardsQueryParameter()
        {
            override val key: String = QUERY_KEY_FIELDS
            override val value: String = QUERY_VALUE_NAME
        }

        object Archived : BoardsQueryParameter()
        {
            override val key: String = QUERY_KEY_ARCHIVED
            override val value: String = "true"

        }

        class Page
        @Throws(IllegalAccessException::class)
        constructor(
            pageNumber: Int = 1
        ) : BoardsQueryParameter()
        {

            init
            {
                if (pageNumber < 1)
                    throw IllegalArgumentException(
                        "The given page number: $pageNumber, must not be less then 1!"
                    )
            }

            override val key: String = QUERY_KEY_PAGE
            override val value: String = pageNumber.toString()

        }

        class PerPage
        @Throws(IllegalAccessException::class)
        constructor(
            resultsPerPage: Int = 50
        ) : BoardsQueryParameter()
        {
            init
            {
                if (resultsPerPage < 1 || resultsPerPage > 100)
                    throw IllegalArgumentException(
                        "The given results per page: $resultsPerPage, must be in the range of 1 to 100!"
                    )
            }

            override val key: String = QUERY_KEY_PER_PAGE
            override val value: String = resultsPerPage.toString()

        }

        class Sort(
            isAscending: Boolean = false
        ) : BoardsQueryParameter()
        {
            override val key: String = QUERY_KEY_SORT
            override val value: String = if (isAscending) "asc" else "desc"

        }

    }
}