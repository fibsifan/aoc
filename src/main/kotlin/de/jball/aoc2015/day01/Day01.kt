package de.jball.aoc2015.day01

import de.jball.AdventOfCodeDay

class Day01(test: Boolean = false) : AdventOfCodeDay<Int>(test, -1, 5) {
	override fun part1(): Int {
		return input[0].chunked(1).sumOf {
			toDirection(it)
		}
	}

	override fun part2(): Int {
		return input[0].chunked(1).map {
			toDirection(it)
		}.runningFold(0) { total,
						   direction ->
			total + direction
		}.mapIndexed { index, floor -> Pair(index, floor) }
			.first { (_, floor) -> floor < 0 }.first
	}

	private fun toDirection(parenthesis: String) = when (parenthesis) {
		"(" -> 1
		")" -> -1
		else -> throw Error("Unexpected: $parenthesis")
	}
}

fun main() {
	Day01().run()
}
