package de.jball.aoc2021.day18

sealed class SailfishNumberComponent(var parent: SailfishNumber? = null) {
    abstract fun magnitude(): Int
    abstract fun height(): Int
    abstract fun needsSplit(): Boolean
    fun needsExplode() = height() + depth() > 4
    fun depth(): Int {
        val parentCopy = parent
        return if (parentCopy != null) {
            parentCopy.depth() + 1
        }
        else 0
    }
}