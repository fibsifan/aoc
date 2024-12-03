package de.jball.aoc2024.day03

import de.jball.AdventOfCodeDay

class Day03(test: Boolean = false): AdventOfCodeDay<Long>(test, 161, 0) {
	private val regex1 = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
	override fun part1(): Long {
		return input.sumOf { line ->
			regex1.findAll(line)
				.map { it.groupValues[1].toLong() * it.groupValues[2].toLong() }
				.sum()
		}
	}

	override fun part2(): Long {
		TODO("Not yet implemented")
	}
}

fun main() {
	Day03().run()
}
