package de.jball.aoc2024.day01

import de.jball.AdventOfCodeDay
import kotlin.math.abs

class Day01(test: Boolean = false): AdventOfCodeDay<Long>(test, 11, 31) {
	private val regex = Regex("(\\d+)\\s+(\\d+)")
	private val lists = input.map { line ->
		val groups = regex.matchEntire(line)!!.groups
		Pair(groups[1]!!.value.toLong(), groups[2]!!.value.toLong())
	}.unzip()

	override fun part1(): Long {
		return lists.first.sorted().zip(lists.second.sorted()) { a, b -> abs(a-b) }.sum()
	}

	override fun part2(): Long {
		val lookup = lists.second.groupingBy { it }.eachCount()
		return lists.first.sumOf {
			it * (lookup[it] ?: 0)
		}
	}
}

fun main() {
	Day01().run()
}
