package de.jball.aoc2021.day14

import de.jball.AdventOfCodeDay

class Day14(test: Boolean = false) : AdventOfCodeDay<Long>(test, 1588, 2188189693529L) {
	private val polymerString = input[0]
	private val startPolymer = polymerString.windowed(2).groupingBy { it }.eachCount()
		.map { (k, v) -> Pair(k, v.toLong()) }
		.toMap()
	private val start = polymerString[0]
	private val end = polymerString[polymerString.length - 1]
	private val insertionMap = input.subList(1, input.size)
		.filter { it.isNotBlank() }
		.map { it.split(" -> ") }
		.associate { Pair(it[0], Pair(it[0][0] + it[1], it[1] + it[0][1])) }

	override fun part1(): Long {
		return iterate(10)
	}

	override fun part2(): Long {
		return iterate(40)
	}

	private fun iterate(steps: Int): Long {
		var polymer = startPolymer
		for (i in 1..steps) {
			polymer = polymer.flatMap { (k, v) -> getReplacementCount(k, v) }
				.groupingBy { it.first }
				.aggregate { _, newCount: Long?, (_, count), first -> if (first) count else newCount!! + count }
		}

		val counts =
			polymer.flatMap { (substring, count) -> listOf(Pair(substring[0], count), Pair(substring[1], count)) }
				.groupingBy { it.first }
				.aggregate { _, newCount: Long?, (_, count), first -> if (first) count else newCount!! + count }

		val min = counts.minOf {
			if (it.key == start && it.key == end)
				it.value + 2
			else if (it.key == start)
				it.value + 1
			else if (it.key == end)
				it.value + 1
			else
				it.value
		}

		val max = counts.maxOf {
			if (it.key == start && it.key == end)
				it.value + 2
			else if (it.key == start)
				it.value + 1
			else if (it.key == end)
				it.value + 1
			else
				it.value
		}

		return max / 2 - min / 2
	}

	private fun getReplacementCount(substring: String, count: Long): List<Pair<String, Long>> {
		return insertionMap[substring]!!.let { (first, second) -> listOf(Pair(first, count), Pair(second, count)) }
	}
}

fun main() {
	Day14(false).run()
}
