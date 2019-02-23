package net.publicmethod.dtos

internal sealed class GloDTO

internal typealias BoardDTOs = List<BoardDTO>

internal data class BoardDTO(
    val id: String,
    val name: String,
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
    val id: String,
    val username: String
) : GloDTO()
