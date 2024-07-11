package de.jball.aoc2023.day01

import de.jball.AdventOfCodeDay

class Day01(test: Boolean = false) : AdventOfCodeDay<Int>(test, 142, 281 + 21) {
	private val digits = mapOf(
		"1" to 1,
		"2" to 2,
		"3" to 3,
		"4" to 4,
		"5" to 5,
		"6" to 6,
		"7" to 7,
		"8" to 8,
		"9" to 9
	)
	private val digitStrings = mapOf(
		"one" to 1,
		"two" to 2,
		"three" to 3,
		"four" to 4,
		"five" to 5,
		"six" to 6,
		"seven" to 7,
		"eight" to 8,
		"nine" to 9
	)
	private val digitRegexString = digits.keys.joinToString("|") + "|" + digitStrings.keys.joinToString("|")
	override fun part1(): Int {
		return input1.sumOf { line ->
			val letters = line.chunked(1)
			val first = letters.firstNotNullOf { it.toIntOrNull() }
			val last = letters.last { it.toIntOrNull() != null }.toInt()
			first * 10 + last
		}
	}

	override fun part2(): Int {
		return input2.sumOf {
			val digitsMatches = Regex("(?=($digitRegexString))").findAll(it)
				.map { matchResult -> matchResult.groups[1]!! }
			val first = digitsMatches.first().value
			val last = digitsMatches.last().value
			val firstDigit = digits[first] ?: digitStrings[first]!!
			val lastDigit = digits[last] ?: digitStrings[last]!!
			firstDigit * 10 + lastDigit
		}
	}
}

fun main() {
	Day01().run()
}
