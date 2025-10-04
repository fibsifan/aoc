package de.jball.aoc2024.day09

import de.jball.AdventOfCodeDay

class Day09(test: Boolean = false): AdventOfCodeDay<Long>(test, 1928, 0) {
	val fileDb = input[0].windowed(2, 2, partialWindows = true).flatMapIndexed { index, blocks ->
		val fileSize = blocks.first().toString().toInt()
		val gapSize = if (blocks.length > 1) blocks.last().toString().toInt() else 0
		val file = List<Long?>(fileSize) { index.toLong() }
		val gap = List<Long?>(gapSize) { null }
		file + gap
	}.toMutableList()

	override fun part1(): Long {
		var currentGap = fileDb.indexOfFirst { it == null }
		var lastValue = fileDb.indexOfLast { it != null }

		while(currentGap < lastValue) {
			fileDb[currentGap] = fileDb[lastValue]
			fileDb[lastValue] = null
			currentGap = fileDb.indexOfFirst { it == null }
			lastValue = fileDb.indexOfLast { it != null }
		}
		return fileDb.filter { it != null }
			.mapIndexed { index, entry ->
				index.toLong() * entry!!
			}.sum()
	}

	override fun part2(): Long {
		TODO("Not yet implemented")
	}
}

fun main() {
	Day09().run()
}
