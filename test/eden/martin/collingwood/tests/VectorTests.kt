package eden.martin.collingwood.tests

import eden.martin.collingwood.Orientation
import eden.martin.collingwood.Space
import eden.martin.collingwood.Vector
import org.testng.Assert
import org.testng.annotations.Test

class VectorTests {
    @Test
    fun horizontalVectorOnlyHasXComponent() {
        val vector = Vector(Orientation.Horizontal)
        Assert.assertEquals(vector, Vector(1, 0))
    }

    @Test
    fun verticalVectorOnlyHasYComponent() {
        val vector = Vector(Orientation.Vertical)
        Assert.assertEquals(vector, Vector(0, 1))
    }

    @Test
    fun canAddVectorToSpace() {
        val vector = Vector(6, 4)
        val space = Space(2, 2)
        Assert.assertEquals(space + vector, Space(8, 6))
    }
}