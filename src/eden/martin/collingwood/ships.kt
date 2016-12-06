package eden.martin.collingwood

/**
 * Describes a *kind of* ship. For example a Destroyer, which has a length of 4. It doesn't represent
 * any particular destroyer, which may be fully sunk or in rude health.
 */
interface IShipTemplate {
    val name: String
    val length: Int
    fun makeShip() : IShip
}

/**
 * A particular instance of a kind of ship. This ship may be afloat or sunk, and it provides a method
 * for hitting it with a shell.
 */
interface IShip {
    val name: String
    val sunk: Boolean
    fun hit(space: Space)
}