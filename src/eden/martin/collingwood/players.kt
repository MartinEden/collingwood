package eden.martin.collingwood

/**
 * Every turn, chooses a square to target
 */
interface IPlayer {
    fun chooseTarget(board: IGameBoard): Space
}
