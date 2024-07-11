package de.jball.aoc2021.day01

import de.jball.AdventOfCodeDay

class Day01(test: Boolean = false) : AdventOfCodeDay<Long>(test, 7, 5) {
	override fun part1(): Long {
		val intInput = input.map { it.toLong() }
		return intInput.subList(0, intInput.size - 1)
			.zip(intInput.subList(1, intInput.size))
			.sumOf { if (it.first < it.second) 1L else 0L }
	}


	override fun part2(): Long {
		val intInput = input.map { it.toLong() }
		return intInput.windowed(4, 1) {
			if (it.subList(0, 2).sum() < it.subList(1, 3).sum()) 1L else 0L
		}.sum()
	}
}

fun main() {
	Day01(true).run()
}
