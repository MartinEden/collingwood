package eden.martin.collingwood.tests

import eden.martin.collingwood.*
import eden.martin.collingwood.tests.eden.martin.collingwood.tests.helpers.PredictableFleetPlacer
import org.testng.Assert
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class AttackTests {
    private lateinit var board : IMutableGameBoard

    @BeforeMethod
    fun setupBoard() {
        board = GameBoard(5)
        val placer = PredictableFleetPlacer()
        placer.placeShip(ShipTemplate("Collingwood", 3), board, Placement(Space(0,0), Orientation.Horizontal))
        // So the ship occupies (0,0), (1,0), and (2,0)
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

    @Test
    fun cannotHitShipSegmentTwice() {
        var report : IReport

        attack(Space(0, 0), board)
        report = attack(Space(0, 0), board)
        Assert.assertTrue(report is MissReport)

        // We check again, to make sure the ship isn't sunk by the third hit
        report = attack(Space(0, 0), board)
        Assert.assertTrue(report is MissReport)
    }
}