package net.publicmethod.dtos

internal sealed class GloDTO

internal typealias BoardDTOs = List<BoardDTO>

internal data class BoardDTO(
    val id: String?,
    val name: String?,
    val archived_columns: ArchivedColumnDTOs?,
    val archived_date: String?,
    val columns: ColumnDTOs?,
    val created_by: CreatedByDTO?,
    val created_date: String?,
    val invited_members: InvitedMemberDTOs?,
    val labels: LabelDTOs?,
    val members: MemberDTOs?
) : GloDTO()

internal typealias ArchivedColumnDTOs = List<ArchivedColumnDTO>

internal data class ArchivedColumnDTO(
    val id: String,
    val name: String,
    val archived_date: String?,
    val created_date: String?
) : GloDTO()

internal typealias ColumnDTOs = List<ColumnDTO>

internal data class ColumnDTO(
    val id: String,
    val name: String,
    val archived_date: String?,
    val created_date: String?
) : GloDTO()

internal typealias InvitedMemberDTOs = List<InvitedMemberDTO>

internal data class InvitedMemberDTO(
    val id: String,
    val username: String,
    val role: String?
) : GloDTO()

internal typealias LabelDTOs = List<LabelDTO>

internal data class LabelDTO(
    val id: String,
    val name: String,
    val color: ColorDTO?,
    val created_by: CreatedByDTO?,
    val created_date: String?
) : GloDTO()

internal typealias MemberDTOs = List<MemberDTO>

internal data class MemberDTO(
    val id: String,
    val username: String,
    val role: String?
) : GloDTO()

internal data class CreatedByDTO(
    val id: String?
) : GloDTO()

internal data class ColorDTO(
    val r: Int?,
    val g: Int?,
    val b: Int?,
    val a: Int?
) : GloDTO()

internal data class GloUserDTO(
    val created_date: String?,
    val id: String?,
    val username: String?,
    val name: String?,
    val email: String?
) : GloDTO()

internal typealias CardDTOs = List<CardDTO>

internal data class CardDTO(
    val archived_date: String?,
    val assignees: AssigneeDTOs?,
    val attachment_count: Int?,
    val board_id: String?,
    val column_id: String?,
    val comment_count: Int?,
    val completed_task_count: Int?,
    val created_by: CreatedByDTO?,
    val created_date: String?,
    val description: DescriptionDTO?,
    val due_date: String?,
    val id: String?,
    val labels: LabelDTOs?,
    val name: String?,
    val total_task_count: Int?,
    val updated_date: String?
) : GloDTO()

internal typealias AssigneeDTOs = List<AssigneeDTO>

internal data class AssigneeDTO(
    val id: String?
) : GloDTO()

internal data class DescriptionDTO(
    val created_by: CreatedByDTO?,
    val created_date: String?,
    val text: String?,
    val updated_by: UpdatedByDTO?,
    val updated_date: String?
) : GloDTO()

internal data class UpdatedByDTO(
    val id: String?
) : GloDTO()

internal data class CommentDTO(
    val board_id: String?,
    val card_id: String?,
    val created_by: CreatedByDTO?,
    val created_date: String?,
    val id: String?,
    val text: String?,
    val updated_by: UpdatedByDTO?,
    val updated_date: String?
) : GloDTO()

