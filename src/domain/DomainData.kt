package net.publicmethod.domain

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
