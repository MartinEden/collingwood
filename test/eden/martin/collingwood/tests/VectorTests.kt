package eden.martin.collingwood.tests

import eden.martin.collingwood.Orientation
import eden.martin.collingwood.Space
import eden.martin.collingwood.Vector
import org.testng.Assert
import org.testng.annotations.Test

class VectorTests {
    @Test
    fun HorizontalVectorOnlyHasXComponent() {
        val vector = Vector(Orientation.Horizontal)
        Assert.assertEquals(Vector(1, 0), vector)
    }

    @Test
    fun VerticalVectorOnlyHasYComponent() {
        val vector = Vector(Orientation.Vertical)
        Assert.assertEquals(Vector(0, 1), vector)
    }

    @Test
    fun CanAddVectorToSpace() {
        val vector = Vector(6, 4)
        val space = Space(2, 2)
        Assert.assertEquals(Space(8, 6), space.plus(vector))
    }
}