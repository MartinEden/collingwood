package eden.martin.collingwood.tests

import eden.martin.collingwood.GameBoard
import eden.martin.collingwood.IGameBoard
import eden.martin.collingwood.IShip
import eden.martin.collingwood.Space
import org.testng.annotations.Test
import org.mockito.Mockito.*
import org.testng.Assert
import org.testng.annotations.DataProvider

class GameboardTests {
    val size = 4

    @Test
    fun squaresAreEmptyByDefault() {
        val board = GameBoard(size)
        for (space in allSpaces(size)) {
            Assert.assertNull(board.get(space))
        }
    }

    @Test
    fun canPlaceAndRetrieveShipsFromGameBoard() {
        val board = GameBoard(size)
        val ship = mock(IShip::class.java)
        val space = Space(2, 1)
        board.put(space, ship)
        Assert.assertEquals(ship, board.get(space))
        assertBoardIsEmptyExcept(board, listOf(space))
    }

    @DataProvider(name = "outOfBounds")
    fun outOfBoundsSpaces(): Array<Array<Any>> {
        val spaces = listOf(
                Space(-1, 0),
                Space(0, -1),
                Space(size, 0),
                Space(0, size)
        )
        return spaces.map { arrayOf<Any>(it) }.toTypedArray()
    }

    @Test(dataProvider = "outOfBounds")
    fun cannotPutShipBeyondRangeOfBoard(space: Space) {
        val board = GameBoard(size)
        Assert.assertThrows { board.put(space, mock(IShip::class.java)) }
    }

    @Test(dataProvider = "outOfBounds")
    fun cannotGetShipBeyondRangeOfBoard(space: Space) {
        val board = GameBoard(size)
        Assert.assertThrows { board.get(space) }
    }

    private fun assertBoardIsEmptyExcept(board : IGameBoard, exceptions : Iterable<Space>) {
        for (space in allSpaces(size).filter { it !in exceptions }) {
            Assert.assertNull(board.get(space))
        }
    }

    private fun allSpaces(size: Int): Sequence<Space> {
        var x = -1;
        var y = 0
        return generateSequence {
            x++
            if (x >= size) {
                x = 0
                y++
            }
            if (y >= size) {
                null
            } else {
                Space(x, y)
            }
        }
    }
}