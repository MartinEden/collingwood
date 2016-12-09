package eden.martin.collingwood.tests.eden.martin.collingwood.tests.helpers

import eden.martin.collingwood.FleetPlacerBase
import eden.martin.collingwood.IMutableGameBoard
import eden.martin.collingwood.Placement
import eden.martin.collingwood.ShipTemplate

class PredictableFleetPlacer : FleetPlacerBase() {
    fun placeShip(shipTemplate: ShipTemplate, board: IMutableGameBoard, placement: Placement) {
        super.placeShip(shipTemplate, board, placement)
    }
}