package de.jball.aoc2021.day18;

class SailfishInt(private val delegate: Int) : SailfishNumberComponent() {
    override fun magnitude(): Int {
        return delegate
    }

    override fun height(): Int {
        return 0
    }

    override fun needsSplit() = delegate > 9

    override fun toString(): String {
        return delegate.toString()
    }
}