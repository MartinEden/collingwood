package eden.martin.collingwood

/**
 * Sets up the board at the beginning of the game from a given stock of ships
 */
interface IFleetPlacer {
    fun placeShips(fleet: Iterable<IShipTemplate>, board: IMutableGameBoard)
}

abstract class FleetPlacerBase {
    protected fun placeShip(shipTemplate: IShipTemplate, board: IMutableGameBoard, placement: Placement) {
        val ship = shipTemplate.makeShip(placement)
        for (space in ship.spaces) {
            board.put(space, ship)
        }
    }

    protected fun canPlace(shipTemplate: IShipTemplate, placement: Placement, board: IGameBoard): Boolean {
        return shipTemplate.spacesRequired(placement).all { board.inBounds(it) && board.get(it) == null }
    }
}

open class RandomFleetPlacer(private val randomSource: IRandomSource) : FleetPlacerBase(), IFleetPlacer {
    constructor(): this(JavaRandomSource())

    override fun placeShips(fleet: Iterable<IShipTemplate>, board: IMutableGameBoard) {
        for (ship in fleet) {
            placeShip(ship, board)
        }
    }

    private fun placeShip(shipTemplate: IShipTemplate, board: IMutableGameBoard) {
        placeShip(shipTemplate, board, getPlacement(shipTemplate, board))
    }

    private fun getPlacement(shipTemplate: IShipTemplate, board: IGameBoard): Placement {
        val candidates = allPlacements(board).filter { canPlace(shipTemplate, it, board) }
        return randomSource.chooseFrom(candidates)
    }

    private fun allPlacements(board: IGameBoard): Collection<Placement> {
        // every square combined with every orientation
        return board.allSpaces().flatMap {
            sequenceOf(
                    Placement(it, Orientation.Horizontal),
                    Placement(it, Orientation.Vertical)
            )
        }.toList()
    }
}