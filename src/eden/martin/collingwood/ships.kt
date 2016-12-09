package eden.martin.collingwood

/**
 * Describes a *kind of* ship. For example a Destroyer, which has a length of 4. It doesn't represent
 * any particular destroyer, which may be fully sunk or in rude health.
 */
interface IShipTemplate {
    val name: String
    val length: Int
    fun makeShip(placement: Placement): IShip
    fun spacesRequired(placement: Placement): Collection<Space>
}

class ShipTemplate(override val name: String, override val length: Int) : IShipTemplate {
    override fun spacesRequired(placement: Placement): Collection<Space> {
        val vector = Vector(placement.orientation)
        val spaces = mutableListOf<Space>()
        var current = placement.origin
        for (i in 1..length) {
            spaces.add(current)
            current += vector
        }
        return spaces
    }

    override fun makeShip(placement: Placement): IShip {
        return Ship(name, spacesRequired(placement))
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
    private val remainingSpaces = spaces.toMutableSet()

    override val sunk: Boolean
        get() = remainingSpaces.isEmpty()

    override fun hit(space: Space): Boolean {
        if (space in remainingSpaces) {
            remainingSpaces.remove(space)
            return true
        }
        return false
    }
}