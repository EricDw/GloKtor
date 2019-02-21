package dtos

import com.google.gson.annotations.SerializedName

typealias Columns = List<ColumnDTO>

data class ColumnDTO(
    val id: String = "",
    val name: String = "",
    @SerializedName("archived_date")
    val archivedDate: String = "",
    @SerializedName("created_date")
    val createdDate: String = ""
)