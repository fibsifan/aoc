package de.jball.aoc2021.day18

sealed class SailfishNumberComponent(var parent: SailfishNumber? = null) {
    abstract fun magnitude(): Int
    abstract fun height(): Int
    fun depth(): Int {
        val parentCopy = parent
        return if (parentCopy != null) {
            parentCopy.depth() + 1
        }
        else 0
    }
}