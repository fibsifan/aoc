package de.jball.aoc2024.day06

import de.jball.AdventOfCodeDay
import de.jball.aocutils.Direction
import de.jball.aocutils.parseGrid
import de.jball.aocutils.plus

class Day06(test: Boolean = false): AdventOfCodeDay<Int>(test, 41, 6) {
	private val map = parseGrid(input) { it }.filter { it.value != '.' }
	private val maxX = input.first().length-1
	private val maxY = input.size-1

	override fun part1(): Int {
		val visited = mutableSetOf<Pair<Int, Int>>()
		var direction = Direction.NORTH
		var currentPosition = map.filter { it.value == '^' }.entries.first().key

		while (0 <= currentPosition.first && currentPosition.first <= maxX && 0 <= currentPosition.second && currentPosition.second <= maxY) {
			if (map[currentPosition + direction.toPair()] == '#') {
				direction = Direction.entries[(direction.ordinal + 2) % 8]
			}
			currentPosition += direction.toPair()
			visited.add(currentPosition)
		}

		return visited.size-1
	}

	override fun part2(): Int {
		TODO("Not yet implemented")
	}
}

fun main() {
	Day06().run()
}
