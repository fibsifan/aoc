package de.jball.aoc2021.day18

class SailfishInt(internal val delegate: Int) : SailfishNumberComponent() {
    override fun magnitude(): Long {
        return delegate.toLong()
    }

    override fun height(): Int {
        return 0
    }

    override fun needsSplit() = delegate > 9

    override fun split(): SailfishPair {
        val left = delegate / 2
        val right = delegate - left
        return SailfishPair(SailfishInt(left), SailfishInt(right))
    }

    override fun toString(): String {
        return delegate.toString()
    }

    override fun equals(other: Any?): Boolean {
        return other is SailfishInt && delegate == other.delegate
    }

    override fun hashCode(): Int {
        return delegate
    }
}
