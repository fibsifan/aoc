package de.jball.aoc2021.day18

import de.jball.aoc2021.Day

class Day18(test: Boolean = false): Day(test, expected1 = 4140) {
    override fun part1(): Long {
        TODO("Not yet implemented")
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }
}

private data class SnailfishNumber(val first: Any, val second: Any) {
    operator fun plus(increment: SnailfishNumber): SnailfishNumber {
        return SnailfishNumber(this, increment).reduced()
    }

    private fun reduced(): SnailfishNumber {
        TODO("Not yet implemented")
    }
}

fun main() {
    Day18(true).run()
}