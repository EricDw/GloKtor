package net.publicmethod.dtos

sealed class GloDTO

typealias BoardDTOs = List<BoardDTO>

data class BoardDTO(
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

typealias ArchivedColumnDTOs = List<ArchivedColumnDTO>

data class ArchivedColumnDTO(
    val id: String,
    val name: String,
    val archived_date: String?,
    val created_date: String?
) : GloDTO()

typealias ColumnDTOs = List<ColumnDTO>

data class ColumnDTO(
    val id: String,
    val name: String,
    val archived_date: String?,
    val created_date: String?
) : GloDTO()

typealias InvitedMemberDTOs = List<InvitedMemberDTO>

data class InvitedMemberDTO(
    val id: String,
    val role: String?,
    val username: String
) : GloDTO()

typealias LabelDTOs = List<LabelDTO>

data class LabelDTO(
    val id: String,
    val name: String,
    val color: ColorDTO?,
    val created_by: CreatedByDTO?,
    val created_date: String?
) : GloDTO()

typealias MemberDTOs = List<MemberDTO>

data class MemberDTO(
    val id: String,
    val username: String,
    val role: String?
) : GloDTO()

data class CreatedByDTO(
    val id: String?
) : GloDTO()

data class ColorDTO(
    val r: Int?,
    val g: Int?,
    val b: Int?,
    val a: Int?
) : GloDTO()

data class GloUserDTO(
    val id: String,
    val username: String
) : GloDTO()
