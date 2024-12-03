package de.jball.aoc2024.day03

import de.jball.AdventOfCodeDay

class Day03(test: Boolean = false): AdventOfCodeDay<Long>(test, 161, 48) {
	private val multiplicationRegex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
	private val deactivationRegex = Regex("don't\\(\\).+?do\\(\\)")

	override fun part1(): Long {
		return input.sumOf { line ->
			multiplicationRegex.findAll(line)
				.map { it.groupValues[1].toLong() * it.groupValues[2].toLong() }
				.sum()
		}
	}

	override fun part2(): Long {
		val cleanedInput = input.reduce { a, b -> a + b }
			.replace(deactivationRegex, "")
		return multiplicationRegex.findAll(cleanedInput)
			.map { it.groupValues[1].toLong() * it.groupValues[2].toLong() }
			.sum()
	}
}

fun main() {
	Day03().run()
}
