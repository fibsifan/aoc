package de.jball.aoc2024.day05

import de.jball.AdventOfCodeDay

class Day05(test: Boolean = false): AdventOfCodeDay<Int>(test, 143, 123) {
	private val ordering = input.takeWhile { it.isNotBlank() }
		.map { it.split("|").map(String::toInt) }
		.groupBy({ (a, _) -> a }, { (_, b) -> b })
	private val reports = input.dropWhile { it.isNotBlank() }
		.drop(1)
		.map { reportSting -> reportSting.split(",").map { it.toInt() } }

	override fun part1(): Int {
		return reports.filter { it == it.sortedWith(this::compareWithOrdering) }.sumOf { it[it.size / 2] }
	}

	override fun part2(): Int {
		return reports.filter { it != it.sortedWith(this::compareWithOrdering) }
			.map { it.sortedWith(this::compareWithOrdering)}
			.sumOf { it[it.size / 2] }
	}

	fun compareWithOrdering(a: Int, b: Int): Int {
		return if (ordering.containsKey(a) && ordering[a]!!.contains(b)) {
			-1
		} else if (ordering.containsKey(b) && ordering[b]!!.contains(a)) {
			1
		} else {
			0
		}
	}
}

fun main() {
	Day05().run()
}
