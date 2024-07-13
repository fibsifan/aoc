package de.jball.aoc2023.day14

import de.jball.AdventOfCodeDay

class Day14(test: Boolean = false): AdventOfCodeDay<Long>(test, 136L, 64L) {
	private val platform = Platform.read(input)

	override fun part1(): Long {
		return platform.shift(Direction.NORTH).load()
	}

	override fun part2(): Long {
		val cyclePath = mutableListOf(1)
		val seenStates = mutableListOf(platform)

		while (cyclePath.last() == cyclePath.size) {
			val next = seenStates.last().cycle()

			if (seenStates.contains(next.cycle())) {
				val index = seenStates.indexOfFirst { it == next.cycle() }
				cyclePath += index
				seenStates += next
			} else {
				cyclePath += cyclePath.size+1
				seenStates += next
			}
		}

		val cycleSize = cyclePath.size - cyclePath.last()
		val preCycleSize = cyclePath.size - cycleSize
		val postCycleSize = (1_000_000_000 - preCycleSize) % cycleSize

		val endState = preCycleSize + postCycleSize

		return seenStates[endState].load()
	}
}

fun main() {
	Day14(false).run()
}
