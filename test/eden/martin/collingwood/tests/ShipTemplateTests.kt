package eden.martin.collingwood.tests

import eden.martin.collingwood.Orientation
import eden.martin.collingwood.Placement
import eden.martin.collingwood.ShipTemplate
import eden.martin.collingwood.Space
import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import org.testng.internal.junit.ArrayAsserts

class ShipTemplateTests {
    @Test(dataProvider = "validArrangements")
    fun correctSpacesAreRequired(origin: Space, length: Int, orientation: Orientation, expectedSpaces: Array<Space>) {
        val template = ShipTemplate("Name", length)
        val spaces = template.spacesRequired(Placement(origin, orientation))
        ArrayAsserts.assertArrayEquals(expectedSpaces, spaces.toTypedArray())
    }

    @DataProvider(name = "validArrangements")
    fun validArrangements(): Array<Array<Any>> = arrayOf(
            // Each of these provides in order: An origin, a length, an orientation.
            // Finally, we describe the expected result of the test
            arrayOf(Space(0, 0), 3, Orientation.Horizontal, arrayOf(Space(0, 0), Space(1, 0), Space(2, 0))),
            arrayOf(Space(1, 0), 3, Orientation.Vertical, arrayOf(Space(1, 0), Space(1, 1), Space(1, 2))),
            arrayOf(Space(1, 1), 2, Orientation.Horizontal, arrayOf(Space(1, 1), Space(2, 1))),
            arrayOf(Space(1, 2), 1, Orientation.Horizontal, arrayOf(Space(1, 2))),
            arrayOf(Space(0, 0), 4, Orientation.Vertical, arrayOf(Space(0, 0), Space(0, 1), Space(0, 2), Space(0, 3)))
    )

    @Test
    fun makeShipCreatesShipWithMatchingName() {
        val template = ShipTemplate("Collingwood", 3)
        val ship = template.makeShip(Placement(Space(0, 0), Orientation.Horizontal))
        Assert.assertEquals(ship.name, template.name)
    }

    @Test
    fun makeShipCreatesShipWithMatchingSpaces() {
        val template = ShipTemplate("Collingwood", 3)
        val expectedSpaces = template.spacesRequired(Placement(Space(1, 2), Orientation.Vertical))
        val ship = template.makeShip(Placement(Space(1, 2), Orientation.Vertical))
        ArrayAsserts.assertArrayEquals(expectedSpaces.toTypedArray(), ship.spaces.toList().toTypedArray())
    }
}