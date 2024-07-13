package de.jball.aoc2023.day17

import de.jball.AdventOfCodeDay

class Day17(test: Boolean = false): AdventOfCodeDay<Int>(test, 102, 0) {
	val grid = input.flatMapIndexed { lineNo, line ->
		line.mapIndexed { columnNo, char ->
			Pair(columnNo, lineNo) to char.digitToInt()
		}
	}.toMap()

	override fun part1(): Int {
		TODO("Not yet implemented")
	}

	override fun part2(): Int {
		TODO("Not yet implemented")
	}
}
