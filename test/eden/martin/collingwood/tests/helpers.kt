package eden.martin.collingwood.tests

import eden.martin.collingwood.Space

fun allSpaces(size: Int): Sequence<Space> {
    var x = -1
    var y = 0
    return generateSequence {
        x++
        if (x >= size) {
            x = 0
            y++
        }
        if (y >= size) {
            null
        } else {
            Space(x, y)
        }
    }
}