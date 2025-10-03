package de.jball.aoc2024.day08

import de.jball.AdventOfCodeDay
import de.jball.aocutils.Grid
import de.jball.aocutils.Point
import kotlin.math.sin

class Day08(test: Boolean = false): AdventOfCodeDay<Int>(test, 14, 34) {

	val antennas = Grid.parse(input, '.')

	override fun part1(): Int {

		val antennaLocationsByType = antennas.map.entries
			.groupBy({ it.value }, { it.key })

		val candidates = antennaLocationsByType.entries.flatMap { antiNodes(it.value) }
		return candidates.toSet().size
	}

	private fun antiNodes(input: List<Point>, fullLines: Boolean = false): Set<Point> {
		if (input.size < 2) return setOf()

		val candidates = (0 until input.size).flatMap { i ->
			((i + 1) until input.size).flatMap { j ->
				antiNodes(input[i], input[j], fullLines)
			}
		}

		return candidates.toSet()
	}

	private fun antiNodes(a: Point, b: Point, line: Boolean = false): List<Point> {
		val delta = a-b
		val positives = generateSequence(a) { it + delta }
			.let { if (line) it else it.drop(1) }
			.takeWhile { this.antennas.isPointInGrid(it) }
			.let { if (line) it else it.take(1) }
			.toList()
		val negatives = generateSequence(b) { it - delta }
			.let { if (line) it else it.drop(1) }
			.takeWhile { this.antennas.isPointInGrid(it) }
			.let { if (line) it else it.take(1) }
			.toList()

		return positives + negatives
	}

	override fun part2(): Int {
		val antennaLocationsByType = antennas.map.entries
			.groupBy({ it.value }, { it.key })

		val candidates = antennaLocationsByType.entries.flatMap { antiNodes(it.value, fullLines = true) }
		return candidates.toSet().size
	}
}

fun main() {
	Day08().run()
}
