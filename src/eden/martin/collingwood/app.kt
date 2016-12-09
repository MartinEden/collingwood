package eden.martin.collingwood

fun main(args : Array<String>) {
    val board = setupBoard()
    val player = ConsoleHumanPlayer()
    while (board.hasShips) {
        val target = player.chooseTarget(board)
        val report = attack(target, board)
        player.inform(report)
    }
    player.inform(VictoryReport())
}

fun attack(target: Space, board: IGameBoard): IReport {
    throw UnsupportedOperationException("Not implemented")
}

fun setupBoard(): IMutableGameBoard {
    val board = GameBoard(10)
    val fleet = listOf(
            ShipTemplate("Battleship", 5),
            ShipTemplate("Destroyer", 4),
            ShipTemplate("Destroyer", 4)
    )
    RandomFleetPlacer().placeShips(fleet, board)
    return board
}

