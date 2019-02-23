package glo_api

import net.publicmethod.domain.Board
import net.publicmethod.dtos.BoardDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DtoTransformerTests
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

}