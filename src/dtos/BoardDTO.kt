package dtos

import com.google.gson.annotations.SerializedName

typealias Boards = List<BoardDTO>

data class BoardDTO(
    val id: String,
    val name: String,
    @SerializedName("archived_columns")
    val archivedColumns: ArchivedColumns = emptyList(),
    @SerializedName("archived_date")
    val archivedDate: String = "",
    val columns: Columns = emptyList(),
    @SerializedName("created_by")
    val createdBy: CreatedByDTO = CreatedByDTO(),
    @SerializedName("created_date")
    val createdDate: String = "",
    @SerializedName("invited_members")
    val invitedMembers: InvitedMembers = emptyList(),
    val labels: Labels = emptyList(),
    val members: Members = emptyList()
)