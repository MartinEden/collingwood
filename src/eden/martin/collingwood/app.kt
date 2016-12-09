package eden.martin.collingwood

fun main(args : Array<String>) {
    val board = setupBoard()
    val player = ConsoleHumanPlayer()
    playGame(board, player)
}

fun playGame(board : IGameBoard, player : IPlayer) {
    while (board.hasShips) {
        val target = player.chooseTarget(board)
        val report = attack(target, board)
        player.inform(report)
    }
    player.inform(VictoryReport())
}

fun attack(target: Space, board: IGameBoard): IReport {
    val ship = board.get(target)
    if (ship != null && ship.hit(target)) {
        if (ship.sunk) {
            return SunkTargetReport(ship)
        } else {
            return HitReport()
        }
    } else {
        return MissReport()
    }
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

