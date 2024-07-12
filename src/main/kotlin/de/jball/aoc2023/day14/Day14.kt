package de.jball.aoc2023.day14

import de.jball.AdventOfCodeDay

class Day14(test: Boolean = false): AdventOfCodeDay<Long>(test, 136L, 64L) {
	private val platform = Platform.read(input)
	private val lookup = mutableMapOf<Platform, Platform>()

	override fun part1(): Long {
		return platform.shift(Direction.NORTH).load()
	}

	override fun part2(): Long {
		var tmp = platform

		while (!lookup.containsKey(tmp)) {
			val newTmp = tmp.cycle()
			lookup[tmp] = newTmp
			tmp = newTmp
		}

		var cycleSize = 0
		val seen = mutableSetOf<Platform>()

		while (!seen.contains(tmp)) {
			seen += tmp
			cycleSize++
			tmp = lookup[tmp]!!
		}

		var head = 0
		tmp = platform

		while (!seen.contains(tmp)) {
			head++
			tmp = lookup[tmp]!!
		}

		val tail = (1_000_000_000 - head) % cycleSize

		for (i in 0..tail) {
			tmp = lookup[tmp]!!
		}

		return tmp.load()
	}

	private fun cachedCycle(platform: Platform): Platform = lookup.computeIfAbsent(platform) { it.cycle() }
}

fun main() {
	Day14(true).run()
}
