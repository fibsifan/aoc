package de.jball.aoc2024.day07

import de.jball.AdventOfCodeDay

class Day07(test: Boolean = false): AdventOfCodeDay<Long>(test,3749L, 11387L) {

	val numbersByResult = input.map { line ->
		val (a, b) =  line.split(": ").map { it.trim() }
		Pair(a.toLong(), b.split(" ").map { it.toLong() })
	}

	override fun part1(): Long {
		return tryCombinations(this::combinePart1)
	}

	override fun part2(): Long {
		return tryCombinations(this::combinePart2)
	}

	private fun tryCombinations(combine: (Long, Long) -> List<Long>): Long = numbersByResult.sumOf { equation ->
		val possibleResults = equation.second.drop(1)
			.fold(listOf(equation.second.first())) { results, number ->
				results.map {
					combine(it, number)
				}.flatten()
			}
		if (possibleResults.contains(equation.first)) equation.first else 0
	}

	private fun combinePart1(result: Long, number: Long): List<Long> {
		return listOf(result + number, result * number)
	}

	private fun combinePart2(result: Long, number: Long): List<Long> {
		return listOf(result + number, result * number, (result.toString() + number.toString()).toLong())
	}
}

fun main() {
	Day07().run()
}


