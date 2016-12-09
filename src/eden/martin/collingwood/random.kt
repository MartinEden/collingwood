package eden.martin.collingwood

import java.util.*

interface IRandomSource {
    fun <T> chooseFrom(options: List<T>): T
}

class JavaRandomSource : IRandomSource {
    override fun <T> chooseFrom(options: List<T>): T {
        val index = Random().nextInt(options.size)
        return options[index]
    }
}


