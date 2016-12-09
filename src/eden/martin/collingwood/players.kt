package eden.martin.collingwood

import java.io.Reader
import java.io.Writer

/**
 * Every turn, chooses a square to target
 */
interface IPlayer {
    fun chooseTarget(board: IGameBoard): Space
    fun inform(report: IAttackReport)
}

class ConsoleHumanPlayer(private val input : Reader, private val output : Writer) : IPlayer {
    override fun inform(report: IAttackReport) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun chooseTarget(board: IGameBoard): Space {
        throw UnsupportedOperationException("not implemented")
    }
}
