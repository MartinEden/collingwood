package eden.martin.collingwood.tests

import eden.martin.collingwood.*
import org.testng.Assert
import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class AttackTests {
    private lateinit var board : IMutableGameBoard

    @BeforeMethod
    fun setupBoard() {
        // We know that the placer will put the ship at (0,0) with a horizontal orientation,
        // so it will occupy (0,0), (1,0), and (2,0)
        board = GameBoard(5)
        val placer = RandomFleetPlacer(PredictableRandomSource())
        placer.placeShips(listOf(ShipTemplate("Collingwood", 3)), board)
    }

    @Test
    fun attackReturnsMissOnEmptySpace() {
        val report = attack(Space(4, 4), board)
        Assert.assertTrue(report is MissReport)
    }

    @Test
    fun sinkingShipReturnsCorrectReports() {
        val r1 = attack(Space(0, 0), board)
        Assert.assertTrue(r1 is HitReport)
        val r2 = attack(Space(1, 0), board)
        Assert.assertTrue(r2 is HitReport)
        val r3 = attack(Space(2, 0), board)
        if (r3 is SunkTargetReport) {
            Assert.assertEquals(r3.target.name, "Collingwood")
        } else {
            Assert.fail("Expected r3 to be a SunkTargetReport")
        }
    }
}