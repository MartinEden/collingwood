package eden.martin.collingwood.tests.eden.martin.collingwood.tests.helpers

import eden.martin.collingwood.IRandomSource

/**
 * This class allows us to test classes that rely on randomness (such as RandomFleetPlacer) with deterministic results
 */
class PredictableRandomSource : IRandomSource {
    override fun <T> chooseFrom(options: List<T>): T {
        return options.first()
    }
}