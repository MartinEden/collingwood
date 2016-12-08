package eden.martin.collingwood

/**
 * Sets up the board at the beginning of the game from a given stock of ships
 */
interface IFleetPlacer {
    fun placeShips(fleet: Iterable<IShipTemplate>, board: IMutableGameBoard)
}

class RandomFleetPlacer : IFleetPlacer {
    override fun placeShips(fleet: Iterable<IShipTemplate>, board: IMutableGameBoard) {
        throw UnsupportedOperationException("not implemented")
    }
}