package de.jball.aoc2021.day18

import de.jball.aoc2021.Day

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


fun main() {
    Day18(true).run()
}