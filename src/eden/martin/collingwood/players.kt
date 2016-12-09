package eden.martin.collingwood

import java.io.BufferedReader
import java.io.Writer

/**
 * Every turn, chooses a square to target
 */
interface IPlayer {
    fun chooseTarget(board: IGameBoard): Space
    fun inform(report: IReport)
}

class ConsoleHumanPlayer(private val input : BufferedReader, private val output : Writer) : IPlayer {
    constructor(): this(System.`in`.bufferedReader(), System.out.bufferedWriter())

    override fun inform(report: IReport) = output.write(representationFor(report) + "\n")

    private fun representationFor(report : IReport) = when (report) {
        is VictoryReport -> "All ships destroyed. Victory!"
        is SunkTargetReport -> "Hit, and sunk a ${report.target.name}!"
        is HitReport -> "Hit"
        else -> "Miss"
    }

    override fun chooseTarget(board: IGameBoard): Space {
        var target : Space? = null
        val maxRange = format(Space(board.size - 1, board.size - 1))

        while (target == null) {
            output.write("Please enter a target: ")
            target = parse(input.readLine())
            if (target == null) {
                output.write("That doesn't look quite right. You need something like A1, or C6, or E2.\n")
            } else {
                if (!board.inBounds(target)) {
                    output.write("That target is outside the board. Please enter something in the range A0 up to $maxRange")
                    target = null
                }
            }
        }
        return target
    }

    private fun format(space: Space): String {
        val column = (space.x + 'A'.toInt()).toChar()
        val row = space.y + 1
        return "$column$row"
    }

    private fun parse(line: String): Space? {
        var column: Int; var row: Int
        if (line.isBlank()) {
            return null
        }

        if (line[0].isLetter()) {
            column = line[0].toUpperCase().toInt() - 'A'.toInt()
        } else {
            return null
        }

        val remainder = line.substring(1)
        row = try {
            remainder.toInt() - 1
        } catch (e : NumberFormatException) {
            return null
        }

        return Space(column, row)
    }
}
