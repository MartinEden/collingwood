package eden.martin.collingwood

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
    // This is just an empty 2D array - kotlin doesn't have elegant support for this currently
    private val grid: Array<Array<IShip?>> = Array(size, { arrayOfNulls<IShip?>(size) })

    override fun get(space: Space): IShip? = grid[space.x][space.y]
    override fun put(space: Space, ship: IShip) {
        grid[space.x][space.y] = ship
    }
}