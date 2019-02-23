package glo_api.anti_corruption

import net.publicmethod.domain.*
import net.publicmethod.dtos.*
import kotlin.test.Test
import kotlin.test.assertEquals

class DtoTransformerTests
{

    @Test
    fun `given BoardDTO when transform then return Board`()
    {
        // Arrange
        val expected = Board("some-id", "Test Board")
        val input = BoardDTO(
            "some-id",
            "Test Board",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        // Act
        val actual = input.transform<Board>()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `given ArchivedColumnDTO when transform then return ArchivedColumn`()
    {
        // Arrange
        val expected = ArchivedColumn(
            "some-id",
            "Test Board",
            "today",
            "yesterday"
        )

        val input = ArchivedColumnDTO(
            "some-id",
            "Test Board",
            "today",
            "yesterday"
        )

        // Act
        val actual = input.transform<ArchivedColumn>()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `given ColumnDTO when transform then return Column`()
    {
        // Arrange
        val expected = Column(
            "some-id",
            "Test Board",
            "today",
            "yesterday"
        )

        val input = ColumnDTO(
            "some-id",
            "Test Board",
            "today",
            "yesterday"
        )

        // Act
        val actual = input.transform<Column>()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `given CreatedByDTO when transform then return CreatedBy`()
    {
        // Arrange
        val expected = CreatedBy(
            "some-id"
        )

        val input = CreatedByDTO(
            "some-id"
        )

        // Act
        val actual = input.transform<CreatedBy>()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `given InvitedMemberDTO when transform then return InvitedMember`()
    {
        // Arrange
        val expected = InvitedMember(
            "some-id",
            "Test User",
            "Owner"
        )

        val input = InvitedMemberDTO(
            "some-id",
            "Test User",
            "Owner"
        )

        // Act
        val actual = input.transform<InvitedMember>()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `given LabelDTO when transform then return Label`()
    {
        // Arrange
        val expected = Label(
            "some-id",
            "Test User",
            Color(0, 1, 2, 3),
            CreatedBy("some-id"),
            "yesterday"
        )

        val input = LabelDTO(
            "some-id",
            "Test User",
            ColorDTO(0, 1, 2, 3),
            CreatedByDTO("some-id"),
            "yesterday"
        )

        // Act
        val actual = input.transform<Label>()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `given MemberDTO when transform then return Member`()
    {
        // Arrange
        val expected = Member(
            "some-id",
            "Test User",
            "Owner"
        )

        val input = MemberDTO(
            "some-id",
            "Test User",
            "Owner"
        )

        // Act
        val actual = input.transform<Member>()

        // Assert
        assertEquals(expected, actual)
    }

}