package de.jball.aoc2025.day02

import de.jball.AdventOfCodeDay

class Day02(test: Boolean = false): AdventOfCodeDay<Long>(test, 1227775554L, 4174379265L) {
	val ranges = input[0]
		.split(",")
		.map { rangeString ->
			val rangeNums = rangeString.split("-")
				.map { numberString -> numberString.toLong() }
			rangeNums[0]..rangeNums[1]
	}

	override fun part1(): Long {
		return ranges.sumOf { range ->
			sumOfInvalidIds(range, { idString ->
				val windowSize = idString.length / 2 + idString.length % 2
				windowSize..windowSize
			})
		}
	}

	private fun sumOfInvalidIds(range: LongRange, windowFunction: (id: String) -> IntRange): Long = range.map { number ->
		number.toString()
	}.filter { idString ->
		windowFunction(idString)
			.any { windowSize ->
				// if the different parts of the id string are all equal, then the distinct call would result in a list of size 1.
				idString.windowed(windowSize, windowSize, true).distinct().size == 1
			}
	}.sumOf { numberString ->
		numberString.toLong()
	}

	override fun part2(): Long {
		return ranges.sumOf { range ->
			sumOfInvalidIds(range, { idString ->
				1..(idString.length / 2 + idString.length % 2)
			})
		}
	}

}

fun main() {
	Day02().run()
}
