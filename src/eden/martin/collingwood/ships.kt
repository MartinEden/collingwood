package eden.martin.collingwood

/**
 * Describes a *kind of* ship. For example a Destroyer, which has a length of 4. It doesn't represent
 * any particular destroyer, which may be fully sunk or in rude health.
 */
interface IShipTemplate {
    val name: String
    val length: Int
    fun makeShip(orientation: Orientation, origin: Space): IShip
    fun spacesRequired(orientation: Orientation, origin: Space): Collection<Space>
}

class ShipTemplate(override val name: String, override val length: Int) : IShipTemplate {
    override fun spacesRequired(orientation: Orientation, origin: Space): Collection<Space> {
        throw UnsupportedOperationException("not implemented")
    }

    override fun makeShip(orientation: Orientation, origin: Space): IShip {
        throw UnsupportedOperationException("not implemented")
    }
}

/**
 * A particular instance of a kind of ship. This ship may be afloat or sunk, and it provides a method
 * for hitting it with a shell.
 */
interface IShip {
    val name: String
    val sunk: Boolean
    val spaces: Iterable<Space>
    fun hit(space: Space): Boolean
}

class Ship(override val name : String, override val spaces: Iterable<Space>) : IShip {
    override val sunk: Boolean
        get() = throw UnsupportedOperationException("not implemented")

    override fun hit(space: Space): Boolean {
        throw UnsupportedOperationException("not implemented")
    }

}