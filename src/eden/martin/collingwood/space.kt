package eden.martin.collingwood

data class Space(val x: Int, val y: Int) {
    operator fun plus(vector: Vector) = Space(x + vector.x, y + vector.y)
}

enum class Orientation { Vertical, Horizontal }

data class Vector(val x: Int, val y: Int) {
    constructor(o: Orientation): this(
            if (o == Orientation.Horizontal) 1 else 0,
            if (o == Orientation.Vertical) 1 else 0
    )
}

data class Placement(val origin: Space, val orientation: Orientation)