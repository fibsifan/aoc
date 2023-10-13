package de.jball.aoc2021.day18

import de.jball.aoc2021.Day
import java.util.*
import kotlin.math.max

class Day18(test: Boolean = false): Day(test, expected1 = 4140) {
    private val sailfishNumbers = input.map { line -> SailfishNumber.fromString(line) }

    override fun part1(): Long {
        println(sailfishNumbers.joinToString("\n"))
        TODO("Not yet implemented")
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }


}

sealed class SailfishNumberComponent {
    abstract fun magnitude(): Int
    abstract fun height(): Int
    abstract fun depth(): Int
}

private class SailfishInt(val delegate: Int): SailfishNumberComponent() {
    override fun magnitude(): Int {
        return delegate
    }

    override fun height(): Int {
        return 0
    }

    override fun depth(): Int {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return delegate.toString()
    }
}

class SailfishNumber(
    a: SailfishNumberComponent,
    b: SailfishNumberComponent
): SailfishNumberComponent() {
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

    private var delegate = Pair(a, b)
    private var parent: SailfishNumber? = null
    init {
        if (delegate.first is SailfishNumber) {
            (delegate.first as SailfishNumber).parent = this
        }
        if (delegate.second is SailfishNumber) {
            (delegate.second as SailfishNumber).parent = this
        }
    }

    override fun magnitude(): Int {
        return delegate.first.magnitude()*3 + delegate.second.magnitude()*2
    }

    override fun height(): Int {
        return max(delegate.first.height(), delegate.second.height()) + 1
    }

    override fun depth(): Int {
        val parentCopy = parent
        return if (parentCopy == null) {
            0
        } else {
            parentCopy.depth() + 1
        }
    }

    override fun toString(): String {
        return "[${delegate.first},${delegate.second}]"
    }

    fun component1(): SailfishNumberComponent {
        return delegate.first
    }
    fun component2(): SailfishNumberComponent {
        return delegate.second
    }

    operator fun plus(increment: SailfishNumber): SailfishNumber {
        return SailfishNumber(this, increment).reduced()
    }

    private fun reduced(): SailfishNumber {
        return this
    }
}

fun main() {
    Day18(true).run()
}