package de.jball.aoc2021.day18

class SnailfishInt(internal val delegate: Int) : SnailfishNumberComponent() {
    override fun magnitude(): Long {
        return delegate.toLong()
    }

    override fun height(): Int {
        return 0
    }

    override fun needsSplit() = delegate > 9

    override fun split(): SnailfishPair {
        val left = delegate / 2
        val right = delegate - left
        return SnailfishPair(SnailfishInt(left), SnailfishInt(right))
    }

    override fun toString(): String {
        return delegate.toString()
    }

    override fun equals(other: Any?): Boolean {
        return other is SnailfishInt && delegate == other.delegate
    }

    override fun hashCode(): Int {
        return delegate
    }
}
