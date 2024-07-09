package de.jball.aoc2021.day18

sealed class SailfishNumberComponent(var parent: SailfishPair? = null) {
    abstract fun magnitude(): Long
    abstract fun height(): Int
    abstract fun needsSplit(): Boolean
    abstract fun split(): SailfishPair
    fun needsExplode() = height() + depth() > 4
    fun depth(): Int {
        val parentCopy = parent
        return if (parentCopy != null) {
            parentCopy.depth() + 1
        }
        else 0
    }
}
