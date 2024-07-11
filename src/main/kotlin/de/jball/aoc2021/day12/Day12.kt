package de.jball.aoc2021.day12

import de.jball.AdventOfCodeDay

class Day12(test: Boolean = false) : AdventOfCodeDay<Long>(test, 226, 3509) {
	private val graph = Graph()

	init {
		input.map { it.split("-") }
			.forEach { graph.addEdge(it[0], it[1]) }
	}

	override fun part1(): Long {
		return graph.allPaths("-")
	}

	override fun part2(): Long {
		return graph.allPaths(null)
	}
}

fun main() {
	Day12(false).run()
}
