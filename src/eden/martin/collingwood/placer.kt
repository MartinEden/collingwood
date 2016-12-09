package eden.martin.collingwood

import java.util.*

/**
 * Sets up the board at the beginning of the game from a given stock of ships
 */
interface IFleetPlacer {
    fun placeShips(fleet: Iterable<IShipTemplate>, board: IMutableGameBoard)
}

class RandomFleetPlacer : IFleetPlacer {
    override fun placeShips(fleet: Iterable<IShipTemplate>, board: IMutableGameBoard) {
        for (ship in fleet) {
            placeShip(ship, board)
        }
    }

    private fun placeShip(shipTemplate: IShipTemplate, board: IMutableGameBoard) {
        val ship = shipTemplate.makeShip(getPlacement(shipTemplate, board))
        for (space in ship.spaces) {
            board.put(space, ship)
        }
    }

    private fun getPlacement(shipTemplate: IShipTemplate, board: IGameBoard): Placement {
        val candidates = allPlacements(board).filter { canPlace(shipTemplate, it, board) }
        val index = Random().nextInt(candidates.count())
        return candidates[index]
    }

    private fun canPlace(shipTemplate: IShipTemplate, placement: Placement, board: IGameBoard): Boolean {
        return shipTemplate.spacesRequired(placement).all { board.inBounds(it) && board.get(it) == null }
    }

    private fun allPlacements(board: IGameBoard): Collection<Placement> {
        // every square combined with every orientation
        return board.allSpaces().flatMap  {
            sequenceOf(
                    Placement(it, Orientation.Horizontal),
                    Placement(it, Orientation.Vertical)
            )
        }.toList()
    }
}