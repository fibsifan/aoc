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
			sumOfInvalidIds(range) { id ->
				val idString = id.toString()
				if (idString.length % 2 != 0)
					false
				else
					idString.take(idString.length / 2) == idString.substring(idString.length / 2)
			}
		}
	}

	private fun sumOfInvalidIds(range: LongRange, idTest: (Long) -> Boolean): Long = range
		.filter { id ->
			idTest(id)
		}.sumOf { numberString ->
			numberString
		}

	override fun part2(): Long {
		return ranges.sumOf { range ->
			sumOfInvalidIds(range) { id ->
				val idString = id.toString()
				(1..idString.length/2).any { windowSize ->
					idString.windowed(windowSize, windowSize, true).distinct().size == 1
				}
			}
		}
	}

}

fun main() {
	Day02().run()
}
