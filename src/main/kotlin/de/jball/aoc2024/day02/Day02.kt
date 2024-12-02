package de.jball.aoc2024.day02

import de.jball.AdventOfCodeDay

class Day02(test: Boolean = false): AdventOfCodeDay<Int>(test, 2, 4) {
	private var levels = input.map { line ->
		line.split(" ").map { it.toInt() }
	}

	override fun part1(): Int {
		return levels.count { level ->
			strictlySafe(level)
		}
	}

	private fun strictlySafe(level: List<Int>): Boolean {
		val diffs = level.windowed(2) { window -> window[0] - window[1] }
		return diffs.all { it in -3..-1 } || diffs.all { it in 1..3 }
	}

	private fun dampedSafe(level: List<Int>): Boolean {
		return List(level.size) { index -> level.without(index) }
			.any { dampedLevel -> strictlySafe(dampedLevel) }
	}

	override fun part2(): Int {
		return levels.count { level ->
			strictlySafe(level) || dampedSafe(level)
		}
	}
}

private fun <E> List<E>.without(index: Int): List<E> {
	return take(index) + drop(index + 1)
}

fun main() {
	Day02().run()
}
