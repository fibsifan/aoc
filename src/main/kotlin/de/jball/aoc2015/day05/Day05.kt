package de.jball.aoc2015.day05

import de.jball.AdventOfCodeDay

class Day05(test: Boolean = false) : AdventOfCodeDay<Int>(test, 2, 0) {
	override fun part1(): Int {
		return input.count { isN1ce(it) }
	}

	private fun isN1ce(line: String): Boolean {
		return line.count { "aeiou".contains(it) } >= 3 &&
			!line.contains(Regex("ab|cd|pq|xy")) &&
			line.contains(Regex("(.)\\1"))
	}

	override fun part2(): Int {
		return input.count { isN2ce(it) }
	}

	private fun isN2ce(line: String): Boolean {
		return line.contains(Regex("(.).\\1"))
			&& line.contains(Regex("(..).*\\1"))
	}

}

fun main() {
	Day05().run()
}
