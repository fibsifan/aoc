package de.jball.aoc2024.day04

import de.jball.AdventOfCodeDay
import de.jball.aocutils.Direction
import de.jball.aocutils.plus

class Day04(test: Boolean = false): AdventOfCodeDay<Int>(test, 18, 9) {
	private val grid = input.flatMapIndexed { lineNo, line ->
		line.mapIndexed { colNo, letter -> Pair(Pair(lineNo, colNo), letter) }
	}.toMap()

	override fun part1(): Int {
		return grid.entries
			.filter { it.value == 'X' }
			.map { gridPosition ->
				Direction.entries
					.filter { direction -> grid[gridPosition.key + direction.toPair()] == 'M' }
					.filter { direction -> grid[gridPosition.key + direction.toPair() + direction.toPair()] == 'A' }
					.filter { direction -> grid[gridPosition.key + direction.toPair() + direction.toPair() + direction.toPair()] == 'S' }
			}.flatten().count()
	}

	override fun part2(): Int {
		TODO("Not yet implemented")
	}
}

fun main() {
	Day04().run()
}
