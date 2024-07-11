package de.jball.aoc2021.day11

import de.jball.AdventOfCodeDay

class Day11(test: Boolean = false) : AdventOfCodeDay<Long>(test, 1656, 195) {
	private val valuesByPosition = input.flatMapIndexed { lineNo, line ->
		line
			.chunked(1)
			.mapIndexed { columnNo, chunk -> Triple(lineNo, columnNo, chunk.toInt()) }
	}
		.associateBy({ Pair(it.first, it.second) }, { it.third }).toMutableMap()
	private val valuesByPosition2 = input.flatMapIndexed { lineNo, line ->
		line
			.chunked(1)
			.mapIndexed { columnNo, chunk -> Triple(lineNo, columnNo, chunk.toInt()) }
	}
		.associateBy({ Pair(it.first, it.second) }, { it.third }).toMutableMap()

	override fun part1(): Long {
		var flashes = 0L

		for (i in 1..100) {
			flashes = performIteration(flashes, valuesByPosition)
		}
		return flashes
	}

	private fun performIteration(flashes: Long, positionMap: MutableMap<Pair<Int, Int>, Int>): Long {
		var flashes1 = flashes
		positionMap.keys
			.forEach { positionMap[it] = positionMap[it]!! + 1 }

		var toFlash = positionMap.filter { it.value > 9 }.map { it.key }.toSet()
		val flashed = mutableSetOf<Pair<Int, Int>>()
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
		position: Pair<Int, Int>,
		flashed: Set<Pair<Int, Int>>,
		positionMap: MutableMap<Pair<Int, Int>, Int>
	): List<Pair<Int, Int>> {
		val (first, second) = position
		return setOf(
			Pair(first - 1, second), Pair(first + 1, second),
			Pair(first - 1, second - 1), Pair(first + 1, second - 1),
			Pair(first - 1, second + 1), Pair(first + 1, second + 1),
			Pair(first, second - 1), Pair(first, second + 1)
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
