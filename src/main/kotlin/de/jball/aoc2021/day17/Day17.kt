package de.jball.aoc2021.day17

import de.jball.AdventOfCodeDay

class Day17(test: Boolean = false) : AdventOfCodeDay<Int>(test, 45, Int.MIN_VALUE) {
	private val ranges = input[0].substringAfter("target area: ").split(", ")
		.map {
			val pairList = it.substring(2)
				.split("..")
				.map { number -> number.toInt() }
			(pairList[0]..pairList[1])
		}
	private val xdim = ranges[0]
	private val ydim = ranges[1]

	override fun part1(): Int {
		val yspeed = 0 - ydim.first - 1
		return (yspeed * (yspeed + 1)) / 2
	}

	override fun part2(): Int {
		val maxYspeed = 0 - ydim.first - 1
		val minYspeed = 0 - ydim.first
		val result = (minYspeed..maxYspeed).flatMap {
			possibleXspeeds(it)
		}.count()
		return Int.MIN_VALUE
	}

	private fun possibleXspeeds(ySpeed: Int): List<Pair<Int, Int>> {
		// Assumption: target area is not less than half as high as it is below zero.

		// exclude cases, where y is never inside the target range because we're passing through without being caught inside.
		if ((-ydim.first + 1) / 2 < -ydim.last && ySpeed in ((-ydim.first + 1) / 2..<-ydim.last)) {
			return emptyList()
		}
		// TODO
		return emptyList()
	}
}

fun main() {
	Day17().run()
}
