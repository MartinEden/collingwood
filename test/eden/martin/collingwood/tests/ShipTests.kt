package eden.martin.collingwood.tests

import eden.martin.collingwood.GameBoard
import eden.martin.collingwood.Ship
import eden.martin.collingwood.Space
import org.testng.Assert
import org.testng.annotations.Test

class ShipTests {
    @Test
    fun canSetShipNameWhenConstructing() {
        val ship = Ship("Collingwood", listOf(Space(0, 0)))
        Assert.assertEquals("Collingwood", ship.name)
    }

    @Test
    fun hitReturnsTrueForIncludedSpacesAndSinksTheShip() {
        val shipSpaces = listOf(Space(0, 0), Space(1, 0), Space(2, 0))
        val ship = Ship("Test", shipSpaces)

        for (space in shipSpaces) {
            Assert.assertFalse(ship.sunk)
            Assert.assertTrue(ship.hit(space))
        }
        Assert.assertTrue(ship.sunk)
    }

    @Test
    fun hitReturnsFalseForNonIncludedSpaces() {
        val shipSpaces = listOf(Space(0, 0), Space(1, 0), Space(2, 0))
        val ship = Ship("Test", shipSpaces)

        for (space in GameBoard(4).allSpaces().filter { it !in shipSpaces }) {
            Assert.assertFalse(ship.hit(space))
        }
    }
}
