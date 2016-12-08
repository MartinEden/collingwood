package eden.martin.collingwood

data class Space(val x: Int, val y: Int)

/**
 * An IGameBoard is a square grid of spaces, each of which can contain either a portion of a ship,
 * or nothing (empty sea). It doesn't matter which space of the ship is contains, as all spaces
 * are identical, so we just store a reference to the ship.
 *
 * This interface is for clients that only need readonly access to the gameboard.
 */
interface IGameBoard {
    val size: Int
    fun get(space: Space): IShip?
}

/**
 * This interface extends IReadonlyGameBoard to allow us to add ships to the gameboard. This is
 * used in game setup.
 */
interface IMutableGameBoard : IGameBoard {
    fun put(space: Space, ship: IShip)
}

class GameBoard(override val size: Int) : IMutableGameBoard {
    override fun get(space: Space): IShip? {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun put(space: Space, ship: IShip) {
        throw UnsupportedOperationException("not implemented")
    }
}