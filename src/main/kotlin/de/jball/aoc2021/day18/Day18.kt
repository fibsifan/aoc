package de.jball.aoc2021.day18

import de.jball.AdventOfCodeDay

class Day18(test: Boolean = false): AdventOfCodeDay<Long>(test, expected1 = 4140L, expected2 = 3993L) {
    private val snailfishTrees = input.map { line -> SnailfishTree.fromString(line) }

    override fun part1(): Long {
        return snailfishTrees.reduce { sum, snailfishTree ->
            sum + snailfishTree
        }.magnitude()
    }

    override fun part2(): Long {
        return snailfishTrees.maxOf { snailfishTree1 ->
            snailfishTrees.map { snailfishTree2 ->
                snailfishTree1 + snailfishTree2
            }.maxOf {
                it.magnitude()
            }
        }
    }
}


fun main() {
    Day18().run()
}
