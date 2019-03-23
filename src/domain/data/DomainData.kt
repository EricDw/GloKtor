package domain.data

import com.google.gson.annotations.SerializedName

sealed class DomainData

typealias Boards = List<Board>

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class Board(
    val id: String = "",
    val name: String = "",
    @SerializedName("archived_columns")
    val archivedColumns: ArchivedColumns = emptyList(),
    @SerializedName("archived_date")
    val archivedDate: String = "",
    val columns: Columns = emptyList(),
    @SerializedName("created_by")
    val createdBy: CreatedBy = CreatedBy(),
    @SerializedName("created_date")
    val createdDate: String = "",
    @SerializedName("invited_members")
    val invitedMembers: InvitedMembers = emptyList(),
    val labels: Labels = emptyList(),
    val members: Members = emptyList()
) : DomainData()

typealias ArchivedColumns = List<ArchivedColumn>

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class ArchivedColumn(
    val id: String = "",
    val name: String = "",
    @SerializedName("archived_date")
    val archivedDate: String = "",
    @SerializedName("created_date")
    val createdDate: String = ""
) : DomainData()

typealias Columns = List<Column>

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class Column(
    val id: String = "",
    val name: String = "",
    @SerializedName("archived_date")
    val archivedDate: String = "",
    @SerializedName("created_date")
    val createdDate: String = ""
) : DomainData()

typealias InvitedMembers = List<InvitedMember>

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class InvitedMember(
    val id: String = "",
    @SerializedName("username")
    val userName: String,
    val role: String = ""
) : DomainData()

typealias Labels = List<Label>

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class Label(
    val id: String = "",
    val name: String = "",
    val color: Color = Color(),
    @SerializedName("created_by")
    val createdBy: CreatedBy = CreatedBy(),
    @SerializedName("created_date")
    val createdDate: String = ""
) : DomainData()

typealias Members = List<Member>

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class Member(
    val id: String = "",
    @SerializedName("username")
    val userName: String = "",
    val role: String = ""
) : DomainData()

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class CreatedBy(
    val id: String = ""
) : DomainData()

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class Color(
    @SerializedName("r")
    val red: Int = 0,
    @SerializedName("g")
    val green: Int = 0,
    @SerializedName("b")
    val blue: Int = 0,
    @SerializedName("a")
    val alpha: Int = 0
) : DomainData()

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class GloUser(
    val id: String = "",
    val name: String = "",
    @SerializedName("username")
    val userName: String = "",
    val email: String = "",
    @SerializedName("created_date")
    val createdDate: String = ""
) : DomainData()


typealias Cards = List<Card>

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class Card(
    val id: String = "",
    val name: String = "",
    val description: Description = Description(),
    val assignees: Assignees = emptyList(),
    val labels: Labels = emptyList(),
    @SerializedName("total_task_count")
    val totalTaskCount: Int = 0,
    @SerializedName("completed_task_count")
    val completedTaskCount: Int = 0,
    @SerializedName("due_date")
    val dueDate: String = "",
    @SerializedName("archived_date")
    val archivedDate: String = "",
    @SerializedName("attachment_count")
    val attachmentCount: Int = 0,
    @SerializedName("board_id")
    val boardId: String = "",
    @SerializedName("column_id")
    val columnId: String = "",
    @SerializedName("comment_count")
    val commentCount: Int = 0,
    @SerializedName("created_by")
    val createdBy: CreatedBy = CreatedBy(),
    @SerializedName("created_date")
    val createdDate: String = "",
    @SerializedName("updated_date")
    val updatedDate: String = ""
) : DomainData()

typealias Assignees = List<Assignee>

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class Assignee(
    val id: String = ""
) : DomainData()

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class Description(
    val text: String = "",
    @SerializedName("created_by")
    val createdBy: CreatedBy = CreatedBy(),
    @SerializedName("created_date")
    val createdDate: String = "",
    @SerializedName("updated_by")
    val updatedBy: UpdatedBy = UpdatedBy(),
    @SerializedName("updated_date")
    val updatedDate: String = ""
) : DomainData()

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class UpdatedBy(
    val id: String = ""
) : DomainData()

typealias Comments = List<Comment>

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class Comment(
    val id: String = "",
    val text: String = "",
    @SerializedName("board_id")
    val boardId: String = "",
    val cardId: String = "",
    @SerializedName("created_by")
    val createdBy: CreatedBy = CreatedBy(),
    @SerializedName("created_date")
    val createdDate: String = "",
    @SerializedName("updated_by")
    val updatedBy: UpdatedBy = UpdatedBy(),
    @SerializedName("updated_date")
    val updatedDate: String = ""
) : DomainData()

typealias Attachments = List<Attachment>

/**
 * Fields have been annotated with [SerializedName]
 * for serializing with Gson.
 */
data class Attachment(
    val id: String = "",
    val filename: String = "",
    @SerializedName("mime_type")
    val mimeType: String = "",
    @SerializedName("created_by")
    val createdBy: CreatedBy = CreatedBy(),
    @SerializedName("created_date")
    val createdDate: String = ""
) : DomainData()