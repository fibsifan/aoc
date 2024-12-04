package de.jball.aoc2022.day09

import de.jball.AdventOfCodeDay
import de.jball.aocutils.Direction

class Day09(test: Boolean = false) : AdventOfCodeDay<Int>(test, 88, 36) {
	private val directions = input.map { parseDirectionLine(it) }

	private fun parseDirectionLine(directionLine: String): Pair<Direction, Int> {
		val blah = directionLine.split(" ")
		return Pair(parseDirection(blah[0]), blah[1].toInt())
	}

	private fun parseDirection(directionString: String): Direction {
		return when (directionString) {
			"U" -> Direction.NORTH
			"L" -> Direction.WEST
			"R" -> Direction.EAST
			"D" -> Direction.SOUTH
			else -> {
				error("Should not happen")
			}
		}
	}

	override fun part1(): Int {
		val rope = Rope(2)

		return directions.map { rope.move(it.first, it.second) }
			.flatten().toSet().size
	}

	override fun part2(): Int {
		val rope = Rope(10)
		return directions.map { rope.move(it.first, it.second) }
			.flatten().toSet().size
	}
}

fun main() {
	Day09().run()
}
