package de.jball.aoc2023.day14

import de.jball.AdventOfCodeDay

class Day14(test: Boolean = false): AdventOfCodeDay<Long>(test, 136L, 64L) {
	private val platform = Platform.read(input)

	override fun part1(): Long {
		return platform.shift(Direction.NORTH).load()
	}

	override fun part2(): Long {
		val seenStates = mutableListOf(platform to 1)
		val seenStrings = mutableSetOf(platform.toString())

		while (seenStates.last().second == seenStates.size) {
			val next = seenStates.last().first.cycle()

			seenStates += if (!seenStrings.add(next.cycle().toString())) {
				val index = seenStates.indexOfFirst { seen -> seen.first == next.cycle() }
				(next to index)
			} else {
				(next to seenStates.size+1)
			}
		}

		val cycleSize = seenStates.size - seenStates.last().second
		val preCycleSize = seenStates.size - cycleSize
		val postCycleSize = (1_000_000_000 - preCycleSize) % cycleSize

		val endState = preCycleSize + postCycleSize

		return seenStates[endState].first.load()
	}
}

fun main() {
	Day14().run()
}
