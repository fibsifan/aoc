package de.jball.aoc2021.day11

import de.jball.AdventOfCodeDay
import de.jball.aocutils.Grid
import de.jball.aocutils.Point

class Day11(test: Boolean = false) : AdventOfCodeDay<Long>(test, 1656, 195) {
	private val valuesByPosition = Grid.parse(input) { it.digitToInt() }.map.toMutableMap()
	private val valuesByPosition2 = Grid.parse(input) { it.digitToInt() }.map.toMutableMap()

	override fun part1(): Long {
		var flashes = 0L

		for (i in 1..100) {
			flashes = performIteration(flashes, valuesByPosition)
		}
		return flashes
	}

	private fun performIteration(flashes: Long, positionMap: MutableMap<Point, Int>): Long {
		var flashes1 = flashes
		positionMap.keys
			.forEach { positionMap[it] = positionMap[it]!! + 1 }

		var toFlash = positionMap.filter { it.value > 9 }.map { it.key }.toSet()
		val flashed = mutableSetOf<Point>()
		while (toFlash.isNotEmpty()) {
			toFlash.forEach { positionMap[it] = 0 }
			flashed.addAll(toFlash)
			val toIncrease = toFlash.map { findNeighbors(it, flashed, positionMap) }
			toIncrease
				.forEach { sublist ->
					sublist
						.forEach { positionMap[it] = positionMap[it]!! + 1 }
				}
			toFlash = positionMap.filter { it.value > 9 }.map { it.key }.toSet()
		}
		flashes1 += flashed.size
		return flashes1
	}

	private fun findNeighbors(
		position: Point,
		flashed: Set<Point>,
		positionMap: Map<Point, Int>
	): List<Point> {
		val (first, second) = position
		return setOf(
			Point(first - 1, second), Point(first + 1, second),
			Point(first - 1, second - 1), Point(first + 1, second - 1),
			Point(first - 1, second + 1), Point(first + 1, second + 1),
			Point(first, second - 1), Point(first, second + 1)
		)
			.subtract(flashed)
			.filter { positionMap.containsKey(it) }
	}

	override fun part2(): Long {
		for (i in 1..1000) {
			if (performIteration(0, valuesByPosition2) == 100L) {
				return i.toLong()
			}
		}
		TODO("1000 is not enough")
	}
}

fun main() {
	Day11().run()
}
