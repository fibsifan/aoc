package de.jball.aoc2021.day18

import java.util.*
import kotlin.math.max

class SailfishNumber(
    a: SailfishNumberComponent, b: SailfishNumberComponent
) : SailfishNumberComponent() {
    companion object {
        fun fromString(sailfishNumberString: String): SailfishNumber {
            var workingString = sailfishNumberString
            val stack: Deque<SailfishNumberComponent> = LinkedList()
            while (workingString.isNotEmpty()) {
                if (workingString.startsWith("[")) {
                    // do nothing
                } else if (workingString.startsWith("]")) {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(SailfishNumber(a, b))
                } else if (workingString.startsWith(",")) {
                    // do nothing
                } else {
                    stack.push(SailfishInt(workingString[0].toString().toInt()))
                }
                workingString = workingString.substring(1)
            }

            if (stack.size != 1) {
                TODO()
            }

            return stack.pop() as SailfishNumber
        }
    }

    private val delegate = Pair(a, b)

    init {
        a.parent = this
        b.parent = this
    }

    override fun magnitude(): Int {
        val (left, right) = this
        return left.magnitude() * 3 + right.magnitude() * 2
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

    operator fun component1(): SailfishNumberComponent {
        return delegate.component1()
    }

    operator fun component2(): SailfishNumberComponent {
        return delegate.component2()
    }

    operator fun plus(increment: SailfishNumber): SailfishNumber {
        return SailfishNumber(this, increment).reduced()
    }

    private fun reduced(): SailfishNumber {
        while (needsExplode() || needsSplit()) {
            if (needsExplode()) {
                explode()
                continue
            }
            if (needsSplit()) {
                split()
            }
        }

        return this
    }

    fun explode() {
        val (left, right) = this
        if (left is SailfishInt && right is SailfishInt) {
            TODO()
        } else if (left is SailfishNumber && left.height() + left.depth() > 4) {
            left.explode()
        } else {
            (right as SailfishNumber).explode()
        }
    }

    fun split() {
        TODO()
    }
}