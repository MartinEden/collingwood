package eden.martin.collingwood.tests

import eden.martin.collingwood.*
import eden.martin.collingwood.tests.eden.martin.collingwood.tests.helpers.PredictableRandomSource
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import org.mockito.Mockito.`when` as mockWhen
import org.testng.Assert
import org.testng.annotations.Test

class RandomFleetPlacerTests {
    @Test
    fun fleetPlacerThrowsExceptionIfThereIsNotEnoughRoom() {
        val fleet = listOf(ShipTemplate("A", 8))
        val board = GameBoard(5)
        val placer = RandomFleetPlacer()
        Assert.assertThrows { placer.placeShips(fleet, board) }
    }

    @Test
    fun fleetPlacerDoesNotOverlapShips() {
        // In a 2x2 grid, this is room for two ships of length two. Trying to add a third one should cause an error
        val oneShip = listOf(ShipTemplate("A", 2))
        val board = GameBoard(2)
        val placer = RandomFleetPlacer()

        // Place two ships
        placer.placeShips(oneShip, board)
        placer.placeShips(oneShip, board)

        // At this point the board should be full
        for (space in board.allSpaces()) {
            Assert.assertNotNull(board.get(space))
        }

        Assert.assertThrows { placer.placeShips(oneShip, board) }
    }

    @Test
    fun fleetPlacerAsksTemplateToMakeShip() {
        val template = mock(IShipTemplate::class.java)
        val ship = Ship("Test", emptyList<Space>())
        val expectedPlacement = Placement(Space(0, 0), Orientation.Horizontal)
        val placer = RandomFleetPlacer(PredictableRandomSource())

        mockWhen(template.spacesRequired(expectedPlacement)).then { listOf(Space(0, 0)) }
        mockWhen(template.makeShip(expectedPlacement)).then { ship }
        placer.placeShips(listOf(template), GameBoard(1))
        verify(template).makeShip(expectedPlacement)
    }

    @Test
    fun fleetPlacerCanPlaceMultipleShips() {
        val templates = listOf(
                ShipTemplate("A", 3),
                ShipTemplate("B", 4),
                ShipTemplate("C", 5)
        )
        val board = GameBoard(10)
        val placer = RandomFleetPlacer()
        placer.placeShips(templates, board)

        // Correct number of non-empty squares
        val spaces = board.allSpaces().toList()
        Assert.assertEquals(3 + 4 + 5, spaces.filter { board.get(it) != null }.count())

        Assert.assertEquals(3, spaces.filter { board.get(it)?.name == "A" }.count())
        Assert.assertEquals(4, spaces.filter { board.get(it)?.name == "B" }.count())
        Assert.assertEquals(5, spaces.filter { board.get(it)?.name == "C" }.count())
    }
}