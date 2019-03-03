package glo_api.anti_corruption

import domain.data.*
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
    is DescriptionDTO -> transformToDescription()
    is UpdatedByDTO -> transformToUpdatedBy()
    is CommentDTO -> transformToComment()
} as T

private fun BoardDTO.transformToBoard(): Board =
    Board(
        id = id ?: "",
        name = name ?: "",
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
    GloUser(
        id = id ?: "",
        name = name ?: "",
        email = email ?: "",
        userName = username ?: "",
        createdDate = created_date ?: ""
    )

private fun CardDTO.transformToCard() =
    Card(
        id = id ?: "",
        name = name ?: "",
        description = description?.transform() ?: Description(),
        assignees = assignees?.map { it.transform<Assignee>() } ?: emptyList(),
        labels = labels?.map { it.transform<Label>() } ?: emptyList(),
        totalTaskCount = total_task_count ?: 0,
        completedTaskCount = completed_task_count ?: 0,
        dueDate = due_date ?: "",
        archivedDate = archived_date ?: "",
        attachmentCount = attachment_count ?: 0,
        boardId = board_id ?: "",
        columnId = column_id ?: "",
        commentCount = comment_count ?: 0,
        createdBy = created_by?.transform() ?: CreatedBy(),
        createdDate = created_date ?: "",
        updatedDate = updated_date ?: ""
    )

private fun AssigneeDTO.transformToAssignee(): Assignee =
    Assignee(id ?: "")

private fun DescriptionDTO.transformToDescription() =
    Description(
        createdBy = created_by?.transform() ?: CreatedBy(),
        createdDate = created_date ?: "",
        text = text ?: "",
        updatedBy = updated_by?.transform() ?: UpdatedBy(),
        updatedDate = updated_date ?: ""
    )

private fun UpdatedByDTO.transformToUpdatedBy(): UpdatedBy =
    UpdatedBy(id ?: "")

private fun CommentDTO.transformToComment() =
    Comment(
        id = id ?: "",
        text = text ?: "",
        boardId = board_id ?: "",
        cardId = card_id ?: "",
        createdBy = created_by?.transform() ?: CreatedBy(),
        createdDate = created_date ?: "",
        updatedBy = updated_by?.transform() ?: UpdatedBy(),
        updatedDate = updated_date ?: ""
    )

