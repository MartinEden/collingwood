package eden.martin.collingwood.tests

import eden.martin.collingwood.*
import org.mockito.Mockito.`when` as mockWhen
import org.mockito.Mockito.mock
import org.testng.Assert
import org.testng.annotations.Test
import java.io.*

class ConsoleHumanPlayerTests {
    private lateinit var output : Writer

    private fun createPlayer(inputText : String = ""): IPlayer {
        val input = StringReader(inputText)
        output = StringWriter()
        return ConsoleHumanPlayer(input, output)
    }

    @Test
    fun reportsMiss() {
        val player = createPlayer()
        player.inform(MissReport())
        Assert.assertEquals("Miss", output.toString())
    }

    @Test
    fun reportsHit() {
        val player = createPlayer()
        player.inform(HitReport())
        Assert.assertEquals("Hit", output.toString())
    }

    @Test
    fun reportsSinking() {
        val player = createPlayer()
        val ship = mock(Ship::class.java)
        mockWhen(ship.name).then { "Test" }
        player.inform(SunkTargetReport(ship))
        Assert.assertEquals("Hit, and sunk a Test!", output.toString())
    }

    @Test
    fun chooseTargetIsReadFromInput() {
        val player = createPlayer("B3")
        val target = player.chooseTarget(GameBoard(5))
        Assert.assertEquals(Space(1, 2), target)
    }
    @Test
    fun chooseTargetCanReadDoubleDigits() {
        val player = createPlayer("A10")
        val target = player.chooseTarget(GameBoard(10))
        Assert.assertEquals(Space(1, 9), target)
    }

    @Test
    fun chooseTargetAsksForAnotherTargetIsThePlayerEntersOneOutOfBounds() {
        val player = createPlayer("Z9\nA1")
        val target = player.chooseTarget(GameBoard(5))
        // The first, invalid target has been ignored, and the second input - A1 - has been used
        Assert.assertEquals(Space(0, 0), target)
    }

    @Test
    fun chooseTargetAsksForAnotherTargetIsThePlayerEntersAnEmptyLine() {
        val player = createPlayer("\nA1")
        val target = player.chooseTarget(GameBoard(5))
        // The first, invalid target has been ignored, and the second input - A1 - has been used
        Assert.assertEquals(Space(0, 0), target)
    }

    @Test
    fun chooseTargetAsksForAnotherTargetIsThePlayerEntersSomethingInTheWrongFormat() {
        val player = createPlayer("Spinach\nB2")
        val target = player.chooseTarget(GameBoard(5))
        // The first, invalid target has been ignored, and the second input - A1 - has been used
        Assert.assertEquals(Space(1, 1), target)
    }
}