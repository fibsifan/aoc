package de.jball.aoc2015.day03

import de.jball.AdventOfCodeDay

class Day03(test: Boolean = false): AdventOfCodeDay<Int>(test, 4, 3) {
    private val directions = input[0].chunked(1).map {
        when(it) {
            "^" -> Coordinate(0,1)
            "v" -> Coordinate(0,-1)
            ">" -> Coordinate(1,0)
            "<" -> Coordinate(-1,0)
            else -> throw Error("Unexpected: $it")
        }
    }
    override fun part1(): Int {
        return directions.runningFold(Coordinate(0,0)) {
            position, direction -> position + direction
        }.toSet().size
    }

    override fun part2(): Int {
        return directions.windowed(2, 2).runningFold(listOf(Coordinate(0,0), Coordinate(0,0))) {
            positions, directions ->
                positions.zip(directions) { a, b -> a+b }

        }.flatten().toSet().size
    }

}

fun main() {
    Day03().run()
}
