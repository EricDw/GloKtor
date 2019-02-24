package glo_api.anti_corruption

import net.publicmethod.domain.*
import net.publicmethod.dtos.*


internal inline fun <reified T : DomainData> GloDTO.transform(): T = when (this)
{
    is BoardDTO -> transformToBoard()
    is ArchivedColumnDTO -> transformToArchivedColumn()
    is ColumnDTO -> transformToColumn()
    is InvitedMemberDTO -> transformToInvitedMember()
    is LabelDTO -> transformToLabel()
    is MemberDTO -> transformToMember()
    is CreatedByDTO -> transformToCreatedBy()
    is ColorDTO -> transformToColor()
    is GloUserDTO -> transformToGloUser()
    is CardDTO -> transformToCard()
    is AssigneeDTO -> transformToAssignee()
    is DescriptionDTO -> TODO()
    is UpdatedByDTO -> TODO()
} as T

private fun BoardDTO.transformToBoard(): Board =
    Board(
        id = id,
        name = name,
        archivedColumns = archived_columns?.map {
            it.transform<ArchivedColumn>()
        } ?: emptyList(),
        archivedDate = archived_date ?: "",
        columns = columns?.map {
            it.transform<Column>()
        } ?: emptyList(),
        createdBy = created_by?.transform() ?: CreatedBy(),
        createdDate = created_date ?: "",
        invitedMembers = invited_members?.map {
            it.transform<InvitedMember>()
        } ?: emptyList(),
        labels = labels?.map {
            it.transform<Label>()
        } ?: emptyList(),
        members = members?.map {
            it.transform<Member>()
        } ?: emptyList()
    )

private fun ArchivedColumnDTO.transformToArchivedColumn(): ArchivedColumn =
    ArchivedColumn(
        id = id,
        name = name,
        archivedDate = archived_date ?: "",
        createdDate = created_date ?: ""
    )

private fun ColumnDTO.transformToColumn(): Column =
    Column(
        id = id,
        name = name,
        archivedDate = archived_date ?: "",
        createdDate = created_date ?: ""
    )

private fun CreatedByDTO.transformToCreatedBy(): CreatedBy =
    CreatedBy(id = id ?: "")

private fun InvitedMemberDTO.transformToInvitedMember(): InvitedMember =
    InvitedMember(
        id = id,
        userName = username,
        role = role ?: ""
    )

private fun LabelDTO.transformToLabel(): Label =
    Label(
        id = id,
        name = name,
        color = color?.transform() ?: Color(),
        createdBy = created_by?.transform() ?: CreatedBy(),
        createdDate = created_date ?: ""
    )

private fun MemberDTO.transformToMember(): Member =
    Member(
        id = id,
        userName = username,
        role = role ?: ""
    )

private fun ColorDTO.transformToColor(): Color =
    Color(
        red = r ?: 0,
        blue = b ?: 0,
        green = g ?: 0,
        alpha = a ?: 0
    )

private fun GloUserDTO.transformToGloUser(): GloUser =
    GloUser(id, username)

private fun CardDTO.transformToCard() =
    Card(
        id = id ?: "",
        name = name ?: "",
        description = description?.transform() ?: Description(),
        assignees = assignees?.map { it.transform<Assignee>() } ?: emptyList(),
        labels = labels?.map { it.transform<Label>() } ?: emptyList(),
        total_task_count = total_task_count ?: 0,
        completed_task_count = completed_task_count ?: 0,
        due_date = due_date ?: "",
        archived_date = archived_date ?: "",
        attachment_count = attachment_count ?: 0,
        board_id = board_id ?: "",
        column_id = column_id ?: "",
        comment_count = comment_count ?: 0,
        created_by = created_by?.transform() ?: CreatedBy(),
        created_date = created_date ?: "",
        updated_date = updated_date ?: ""
    )

private fun AssigneeDTO.transformToAssignee(): Assignee =
    Assignee(id ?: "")

