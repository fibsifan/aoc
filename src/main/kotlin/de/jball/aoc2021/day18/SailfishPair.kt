package de.jball.aoc2021.day18

import java.util.Deque
import java.util.LinkedList
import kotlin.math.max

class SailfishPair(internal var left: SailfishNumberComponent, internal var right: SailfishNumberComponent) : SailfishNumberComponent() {
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

    init {
        left.parent = this
        right.parent = this
    }

    override fun magnitude(): Long {
        return left.magnitude() * 3L + right.magnitude() * 2L
    }

    override fun height(): Int {
        return max(left.height(), right.height()) + 1
    }

    override fun toString(): String {
        return "[$left,$right]"
    }

    override fun needsSplit(): Boolean {
        return left.needsSplit() || right.needsSplit()
    }

    override fun split(): SailfishPair {
        return if (left.needsSplit()) {
            SailfishPair(left.split(), right)
        } else if (right.needsSplit()) {
            SailfishPair(left, right.split())
        } else {
            throw IllegalStateException("Split called on node that doesnt need splitting: $this")
        }
    }

    operator fun component1(): SailfishNumberComponent {
        return left
    }

    operator fun component2(): SailfishNumberComponent {
        return right
    }

    operator fun plus(increment: SailfishPair): SailfishPair {
        return SailfishPair(this, increment)
    }

    override fun equals(other: Any?): Boolean {
        return other is SailfishPair && left == other.left && right == other.right
    }

    override fun hashCode(): Int {
        return left.hashCode() * 31 + right.hashCode()
    }
}
