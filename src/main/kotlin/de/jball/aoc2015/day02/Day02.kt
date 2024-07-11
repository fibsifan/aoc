package de.jball.aoc2015.day02

import de.jball.AdventOfCodeDay

class Day02(test: Boolean = false) : AdventOfCodeDay<Int>(test, 101, 48) {
	val boxes = input.map { line -> line.split("x").map { it.toInt() } }
		.map { dimensions -> Box(dimensions) }

	override fun part1(): Int {
		return boxes.sumOf { it.paperNeeded() }
	}

	override fun part2(): Int {
		return boxes.sumOf { it.ribbonNeeded() }
	}
}

fun main() {
	Day02().run()
}
