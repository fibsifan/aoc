package de.jball.aoc2025.day02

import de.jball.AdventOfCodeDay

class Day02(test: Boolean = false): AdventOfCodeDay<Long>(test, 1227775554L, 0L) {
	val ranges = input[0].split(",").map { rangeString ->
		val rangeNums = rangeString.split("-")
			.map { numberString -> numberString.toLong() }
		rangeNums[0]..rangeNums[1]
	}

	override fun part1(): Long {
		ranges.map { range ->
			range.map { it.toString() }
		}
		TODO("Not yet implemented")
	}

	override fun part2(): Long {
		TODO("Not yet implemented")
	}

}
