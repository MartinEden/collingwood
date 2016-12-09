package eden.martin.collingwood.tests

import eden.martin.collingwood.*
import org.testng.annotations.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when` as mockWhen
import org.testng.Assert
import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.internal.junit.ArrayAsserts

class GameboardTests {
    val size = 3
    lateinit var board : IMutableGameBoard

    @BeforeMethod
    fun createBoard() {
        board = GameBoard(size)
    }

    @Test
    fun allSpacesReturnsEverySpace() {
        val expectedSpaces = inBoundsSpaces()
        ArrayAsserts.assertArrayEquals(expectedSpaces.toList().toTypedArray(),
                                       board.allSpaces().toList().toTypedArray())
    }

    @Test
    fun squaresAreEmptyByDefault() {
        for (space in board.allSpaces()) {
            Assert.assertNull(board.get(space))
        }
    }

    @Test
    fun canPlaceAndRetrieveShipsFromGameBoard() {
        val ship = mock(IShip::class.java)
        val space = Space(2, 1)
        board.put(space, ship)
        Assert.assertEquals(ship, board.get(space))
        assertBoardIsEmptyExcept(board, listOf(space))
    }

    @Test(dataProvider = "outOfBounds")
    fun cannotPutShipBeyondRangeOfBoard(space: Space) {
        Assert.assertThrows { board.put(space, mock(IShip::class.java)) }
    }

    @Test(dataProvider = "outOfBounds")
    fun cannotGetShipBeyondRangeOfBoard(space: Space) {
        Assert.assertThrows { board.get(space) }
    }

    @Test(dataProvider = "outOfBounds")
    fun inBoundsReturnsFalseForIndicesOutsideOfBounds(space: Space) {
        Assert.assertFalse(board.inBounds(space))
    }

    @Test(dataProvider = "inBounds")
    fun inBoundsReturnsTrueForIndicesInsideOfBounds(space: Space) {
        Assert.assertTrue(board.inBounds(space))
    }

    @Test
    fun hasShipsReturnsFalseForEmptyBoard() {
        Assert.assertFalse(board.hasShips)
    }

    @Test
    fun hasShipsReturnsTrueIfShipHasBeenAdded() {
        val ship = mock(IShip::class.java)
        board.put(Space(0, 0), ship)
        Assert.assertTrue(board.hasShips)
    }

    @Test
    fun hasShipsReturnsFalseIfAllShipsHaveBeenSunk() {
        val ship = mock(IShip::class.java)
        mockWhen(ship.sunk).thenReturn(true)
        board.put(Space(0, 0), ship)
        Assert.assertFalse(board.hasShips)
    }

    private fun assertBoardIsEmptyExcept(board : IGameBoard, exceptions : Iterable<Space>) {
        for (space in board.allSpaces().filter { it !in exceptions }) {
            Assert.assertNull(board.get(space))
        }
    }

    @DataProvider(name = "outOfBounds")
    fun outOfBoundsSpacesDataPro(): Array<Array<Any>> {
        val spaces = listOf(
                Space(-1, 0),
                Space(0, -1),
                Space(size, 0),
                Space(0, size)
        )
        return spaces.map { arrayOf<Any>(it) }.toTypedArray()
    }

    @DataProvider(name = "inBounds")
    fun inBoundsSpacesDataProvider(): Array<Array<Any>> {
        return inBoundsSpaces().map { arrayOf<Any>(it) }.toList().toTypedArray()
    }
    fun inBoundsSpaces(): Sequence<Space> = sequenceOf(
            Space(0, 0),
            Space(1, 0),
            Space(2, 0),
            Space(0, 1),
            Space(1, 1),
            Space(2, 1),
            Space(0, 2),
            Space(1, 2),
            Space(2, 2)
    )
}