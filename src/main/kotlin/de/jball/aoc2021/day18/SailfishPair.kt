package de.jball.aoc2021.day18

import java.util.*
import kotlin.math.max

class SailfishPair(a: SailfishNumberComponent, b: SailfishNumberComponent) : SailfishNumberComponent() {
    companion object {
        fun fromString(sailfishNumberString: String): SailfishPair {
            var workingString = sailfishNumberString
            val stack: Deque<SailfishNumberComponent> = LinkedList()
            while (workingString.isNotEmpty()) {
                if (workingString.startsWith("[")) {
                    // do nothing
                } else if (workingString.startsWith("]")) {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(SailfishPair(a, b))
                } else if (workingString.startsWith(",")) {
                    // do nothing
                } else {
                    stack.push(SailfishInt(Regex("^\\d+").find(workingString)!!.value.toInt()))
                }
                workingString = workingString.replace(Regex("^\\d+|^."), "")
            }

            if (stack.size != 1) {
                throw IllegalStateException("Couldn't parse sailfish number: $sailfishNumberString")
            }

            return stack.pop() as SailfishPair
        }
    }

    private val delegate = Pair(a, b)

    init {
        a.parent = this
        b.parent = this
    }

    override fun magnitude(): Long {
        val (left, right) = this
        return left.magnitude() * 3L + right.magnitude() * 2L
    }

    override fun height(): Int {
        val (left, right) = this
        return max(left.height(), right.height()) + 1
    }

    override fun toString(): String {
        val (left, right) = this
        return "[$left,$right]"
    }

    override fun needsSplit(): Boolean {
        val (left, right) = this
        return left.needsSplit() || right.needsSplit()
    }

    override fun split(): SailfishPair {
        val (left, right) = this
        return if (left.needsSplit()) {
            SailfishPair(left.split(), right)
        } else if (right.needsSplit()) {
            SailfishPair(left, right.split())
        } else {
            throw IllegalStateException("Split called on node that doesnt need splitting: $this")
        }
    }

    operator fun component1(): SailfishNumberComponent {
        return delegate.component1()
    }

    operator fun component2(): SailfishNumberComponent {
        return delegate.component2()
    }

    operator fun plus(increment: SailfishPair): SailfishPair {
        return SailfishPair(this, increment)
    }

    override fun equals(other: Any?): Boolean {
        return other is SailfishPair && delegate == other.delegate
    }

    override fun hashCode(): Int {
        return delegate.hashCode()
    }
}
