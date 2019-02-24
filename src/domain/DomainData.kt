package net.publicmethod.domain

import net.publicmethod.dtos.CardDTO

sealed class DomainData

typealias Boards = List<Board>

data class Board(
    val id: String = "",
    val name: String = "",
    val archivedColumns: ArchivedColumns = emptyList(),
    val archivedDate: String = "",
    val columns: Columns = emptyList(),
    val createdBy: CreatedBy = CreatedBy(),
    val createdDate: String = "",
    val invitedMembers: InvitedMembers = emptyList(),
    val labels: Labels = emptyList(),
    val members: Members = emptyList()
) : DomainData()

typealias ArchivedColumns = List<ArchivedColumn>

data class ArchivedColumn(
    val id: String = "",
    val name: String = "",
    val archivedDate: String = "",
    val createdDate: String = ""
) : DomainData()

typealias Columns = List<Column>

data class Column(
    val id: String = "",
    val name: String = "",
    val archivedDate: String = "",
    val createdDate: String = ""
) : DomainData()

typealias InvitedMembers = List<InvitedMember>

data class InvitedMember(
    val id: String = "",
    val userName: String,
    val role: String = ""
) : DomainData()

typealias Labels = List<Label>

data class Label(
    val id: String = "",
    val name: String = "",
    val color: Color = Color(),
    val createdBy: CreatedBy = CreatedBy(),
    val createdDate: String = ""
) : DomainData()

typealias Members = List<Member>

data class Member(
    val id: String = "",
    val userName: String = "",
    val role: String = ""
) : DomainData()

data class CreatedBy(
    val id: String = ""
) : DomainData()

data class Color(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0,
    val alpha: Int = 0
) : DomainData()

data class GloUser(
    val id: String = "",
    val userName: String
) : DomainData()

internal typealias CardDTOs = List<CardDTO>

internal data class Card(
    val archived_date: String = "",
    val assignees: Assignees = emptyList(),
    val attachment_count: Int = 0,
    val board_id: String = "",
    val column_id: String = "",
    val comment_count: Int = 0,
    val completed_task_count: Int = 0,
    val created_by: CreatedBy = CreatedBy(),
    val created_date: String = "",
    val description: Description,
    val due_date: String = "",
    val id: String = "",
    val labels: Labels = emptyList(),
    val name: String = "",
    val total_task_count: Int = 0,
    val updated_date: String = ""
) : DomainData()

internal typealias Assignees = List<Assignee>

internal data class Assignee(
    val id: String = ""
) : DomainData()

internal data class Description(
    val created_by: CreatedBy = CreatedBy(),
    val created_date: String = "",
    val text: String = "",
    val updated_by: UpdatedBy = UpdatedBy(),
    val updated_date: String = ""
) : DomainData()

internal data class UpdatedBy(
    val id: String = ""
) : DomainData()

internal data class Comment(
    val board_id: String = "",
    val card_id: String = "",
    val created_by: CreatedBy = CreatedBy(),
    val created_date: String = "",
    val id: String = "",
    val text: String = "",
    val updated_by: UpdatedBy = UpdatedBy(),
    val updated_date: String = ""
)