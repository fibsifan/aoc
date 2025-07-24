package de.jball.aoc2024.day04

import de.jball.AdventOfCodeDay
import de.jball.aocutils.Direction
import de.jball.aocutils.plus
import de.jball.aocutils.minus
import de.jball.aocutils.parseGrid

class Day04(test: Boolean = false): AdventOfCodeDay<Int>(test, 18, 9) {
	private val grid = parseGrid(input) { it }
	private val maxLines = input.size
	private val maxColumns = input[0].length

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

	private fun isNotBorder(gridPosition: Pair<Int, Int>): Boolean {
		return gridPosition.first > 0 && gridPosition.first < maxLines-1 &&
			gridPosition.second > 0 && gridPosition.second < maxColumns-1
	}

	override fun part2(): Int {
		return grid.entries
			.filter { isNotBorder(it.key) }
			.filter { it.value == 'A' }
			.count { gridPosition ->
				Direction.DIAGONALS
					.filter { direction -> grid[gridPosition.key + direction.toPair()] == 'S' }
					.filter { direction -> grid[gridPosition.key - direction.toPair()] == 'M' }
					.size >= 2
			}
	}
}

fun main() {
	Day04().run()
}
