package de.jball.aoc2021.day18

import de.jball.AdventOfCodeDay

class Day18(test: Boolean = false): AdventOfCodeDay<Long>(test, expected1 = 4140, expected2 = 0L) {
    private val sailfishTrees = input.map { line -> SailfishTree.fromString(line) }

    override fun part1(): Long {
        return sailfishTrees.reduce { sum, sailfishTree -> sum + sailfishTree }.magnitude()
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }
}


fun main() {
    Day18(true).run()
}
