package de.jball.aoc2021.day08

import de.jball.AdventOfCodeDay
import java.util.*

class Day08(test: Boolean = false) : AdventOfCodeDay<Long>(test, 26, 61229) {
	private val pairedInput = input
		.map { line ->
			line.split(" | ").map { digits ->
				digits.split(" ").map { digit ->
					digit.chunked(1).toSortedSet()
				}
			}
		}
		.map {
			Pair(it[0], it[1])
		}

	override fun part1(): Long {
		return pairedInput
			.map { it.second }
			.flatten()
			.filter { it.size in listOf(2, 3, 4, 7) }
			.size.toLong()
	}

	override fun part2(): Long {
		return pairedInput.sumOf { determineOutput(it) }
	}

	private fun determineOutput(line: Pair<List<SortedSet<String>>, List<SortedSet<String>>>): Long {
		val wholeLine = line.first + line.second
		val one = wholeLine.first { it.size == 2 }.toSortedSet()
		val seven = wholeLine.first { it.size == 3 }.toSortedSet()
		val eight = wholeLine.first { it.size == 7 }.toSortedSet()
		val four = wholeLine.first { it.size == 4 }.toSortedSet()
		val six = wholeLine.filter { it.size == 6 }.first { (it intersect one).size == 1 }.toSortedSet()
		val zero = wholeLine.filter { it.size == 6 }.first { ((four subtract it) subtract one).size == 1 }.toSortedSet()
		val three = wholeLine.filter { it.size == 5 }.first { (it subtract one).size == 3 }.toSortedSet()
		val five = wholeLine.filter { it.size == 5 }.first { (it union six).size == 6 }.toSortedSet()
		val two = wholeLine.filter { it.size == 5 }.first { (it union six) == eight && it != three }.toSortedSet()
		val nine = wholeLine.first { it !in setOf(zero, one, two, three, four, five, six, seven, eight) }.toSortedSet()

		val map = mapOf(
			Pair(zero, 0L), Pair(one, 1L), Pair(two, 2L), Pair(three, 3L), Pair(four, 4L),
			Pair(five, 5L), Pair(six, 6L), Pair(seven, 7L), Pair(eight, 8L), Pair(nine, 9L)
		)

		val l: Long
		val m: Long
		val n: Long
		val o: Long
		try {
			l = map[line.second[0]]!! * 1000L
			m = map[line.second[1]]!! * 100L
			n = map[line.second[2]]!! * 10L
			o = map[line.second[3]]!!
		} catch (e: RuntimeException) {
			println(map)
			println(line.second)
			throw e
		}
		return l + m + n + o
	}
}

fun main() {
	Day08().run()
}
