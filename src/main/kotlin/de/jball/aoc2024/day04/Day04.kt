package de.jball.aoc2024.day04

import de.jball.AdventOfCodeDay
import de.jball.aocutils.Direction
import de.jball.aocutils.Grid
import de.jball.aocutils.Point
import de.jball.aocutils.plus
import de.jball.aocutils.minus

class Day04(test: Boolean = false): AdventOfCodeDay<Int>(test, 18, 9) {
	private val grid = Grid.parse(input)
	private val maxLines = input.size
	private val maxColumns = input[0].length

	override fun part1(): Int {
		return grid.map.entries
			.filter { it.value == 'X' }
			.map { gridPosition ->
				Direction.entries
					.filter { direction -> grid.map[gridPosition.key + direction] == 'M' }
					.filter { direction -> grid.map[gridPosition.key + direction + direction] == 'A' }
					.filter { direction -> grid.map[gridPosition.key + direction + direction + direction] == 'S' }
			}.flatten().count()
	}

	private fun isNotBorder(gridPosition: Point): Boolean {
		return gridPosition.x > 0 && gridPosition.x < maxLines-1 &&
			gridPosition.y > 0 && gridPosition.y < maxColumns-1
	}

	override fun part2(): Int {
		return grid.map.entries
			.filter { isNotBorder(it.key) }
			.filter { it.value == 'A' }
			.count { gridPosition ->
				Direction.DIAGONALS
					.filter { direction -> grid.map[gridPosition.key + direction] == 'S' }
					.filter { direction -> grid.map[gridPosition.key - direction] == 'M' }
					.size >= 2
			}
	}
}

fun main() {
	Day04().run()
}
