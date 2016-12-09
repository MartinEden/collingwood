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
        val input = BufferedReader(StringReader(inputText))
        output = StringWriter()
        return ConsoleHumanPlayer(input, output)
    }

    @Test
    fun reportsMiss() {
        val player = createPlayer()
        player.inform(MissReport())
        Assert.assertEquals(output.toString().trim(), "Miss")
    }

    @Test
    fun reportsHit() {
        val player = createPlayer()
        player.inform(HitReport())
        Assert.assertEquals(output.toString().trim(), "Hit")
    }

    @Test
    fun reportsSinking() {
        val player = createPlayer()
        val ship = mock(IShip::class.java)
        mockWhen(ship.name).then { "Test" }
        player.inform(SunkTargetReport(ship))
        Assert.assertEquals(output.toString().trim(), "Hit, and sunk a Test!")
    }

    @Test
    fun reportsVictory() {
        val player = createPlayer()
        player.inform(VictoryReport())
        Assert.assertEquals(output.toString().trim(), "All ships destroyed. Victory!")
    }

    @Test
    fun chooseTargetIsReadFromInput() {
        val player = createPlayer("B3")
        val target = player.chooseTarget(GameBoard(5))
        Assert.assertEquals(target, Space(1, 2))
    }
    @Test
    fun chooseTargetCanReadDoubleDigits() {
        val player = createPlayer("A10")
        val target = player.chooseTarget(GameBoard(10))
        Assert.assertEquals(target, Space(0, 9))
    }

    @Test
    fun chooseTargetAsksForAnotherTargetIsThePlayerEntersOneOutOfBounds() {
        val player = createPlayer("Z9\nA1")
        val target = player.chooseTarget(GameBoard(5))
        // The first, invalid target has been ignored, and the second input - A1 - has been used
        Assert.assertEquals(target, Space(0, 0))
    }

    @Test
    fun chooseTargetAsksForAnotherTargetIsThePlayerEntersAnEmptyLine() {
        val player = createPlayer("\nA1")
        val target = player.chooseTarget(GameBoard(5))
        // The first, invalid target has been ignored, and the second input - A1 - has been used
        Assert.assertEquals(target, Space(0, 0))
    }

    @Test
    fun chooseTargetAsksForAnotherTargetIsThePlayerEntersSomethingInTheWrongFormat() {
        val player = createPlayer("Spinach\nB2")
        val target = player.chooseTarget(GameBoard(5))
        // The first, invalid target has been ignored, and the second input - B2 - has been used
        Assert.assertEquals(target, Space(1, 1))
    }
}