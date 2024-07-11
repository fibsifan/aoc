package de.jball.aoc2021.day06

import de.jball.AdventOfCodeDay

class Day06(test: Boolean = false) : AdventOfCodeDay<Long>(test, 5934L, 26984457539L) {
	private val fishByAge = input[0].split(",")
		.groupingBy { it }
		.aggregate { _, accumulator: Long?, _, first -> if (first) 1L else accumulator!! + 1L }
	private val mapping = mapOf(
		Pair("0", listOf("6", "8")),
		Pair("1", listOf("0")),
		Pair("2", listOf("1")),
		Pair("3", listOf("2")),
		Pair("4", listOf("3")),
		Pair("5", listOf("4")),
		Pair("6", listOf("5")),
		Pair("7", listOf("6")),
		Pair("8", listOf("7"))
	)

	override fun part1(): Long {
		var nextDay = fishByAge
		for (i in 1..80) {
			nextDay = mapAndRecount(nextDay)
		}
		return nextDay.values.sum()
	}

	private fun mapAndRecount(today: Map<String, Long>): Map<String, Long> {
		return today.flatMap { (age, count) -> mapping[age]!!.map { newAge -> Pair(newAge, count) } }
			.groupingBy { (newAge, _) -> newAge }
			.aggregate { _, newCount: Long?, (_, count), first -> if (first) count else newCount!! + count }
	}

	override fun part2(): Long {
		var nextDay = fishByAge
		for (i in 1..256) {
			nextDay = mapAndRecount(nextDay)
		}
		return nextDay.values.sum()
	}
}

fun main() {
	Day06().run()
}
