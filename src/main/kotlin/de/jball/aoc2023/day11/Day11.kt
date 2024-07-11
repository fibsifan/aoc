package de.jball.aoc2023.day11

import de.jball.AdventOfCodeDay
import de.jball.manhattanDistance

class Day11(test: Boolean = false) : AdventOfCodeDay<Long>(test, 374L, 1030L) {
	private val galaxies = input.mapIndexed { lineNo, line ->
		line.mapIndexed { columnNo, sign ->
			if (sign == '#')
				Pair(columnNo, lineNo)
			else
				null
		}.filterNotNull()
	}.flatten()
	private val maxColumnNo = galaxies.maxOf { (a, _) -> a }
	private val maxLineNo = galaxies.maxOf { (_, b) -> b }
	private val emptyColumns = (0..maxColumnNo).toSet().subtract(galaxies.map { (a, _) -> a }.toSet())
	private val emptyLines = (0..maxLineNo).toSet().subtract(galaxies.map { (_, b) -> b }.toSet())
	private val galaxyPairs = galaxies.mapIndexed { galaxyNo, galaxy ->
		galaxies.slice(galaxyNo + 1 until galaxies.size).map { otherGalaxy -> Pair(galaxy, otherGalaxy) }
	}.flatten()

	override fun part1(): Long {
		return galaxyPairs.sumOf { (galaxy, otherGalaxy) ->
			distance(galaxy, otherGalaxy).toLong()
		}
	}

	private fun distance(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
		return manhattanDistance(a, b) + emptyColumnsBetween(a, b) + emptyLinesBetween(a, b)
	}

	private fun emptyColumnsBetween(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
		return if (a.first < b.first) {
			(a.first..b.first).filter { emptyColumns.contains(it) }.size
		} else {
			(b.first..a.first).filter { emptyColumns.contains(it) }.size
		}
	}

	private fun emptyLinesBetween(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
		return if (a.second < b.second) {
			(a.second..b.second).filter { emptyLines.contains(it) }.size
		} else {
			(b.second..a.second).filter { emptyLines.contains(it) }.size
		}
	}

	override fun part2(): Long {
		return galaxyPairs.sumOf { (galaxy, otherGalaxy) ->
			extendedDistance(galaxy, otherGalaxy)
		}
	}

	private fun extendedDistance(galaxy: Pair<Int, Int>, otherGalaxy: Pair<Int, Int>): Long {
		val factor = if (test) 9L else 999_999L
		return manhattanDistance(galaxy, otherGalaxy)
			.toLong() + factor * emptyColumnsBetween(galaxy, otherGalaxy)
			.toLong() + factor * emptyLinesBetween(galaxy, otherGalaxy).toLong()
	}
}

fun main() {
	Day11().run()
}
