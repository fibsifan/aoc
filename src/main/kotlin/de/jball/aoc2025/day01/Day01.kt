package de.jball.aoc2025.day01

import de.jball.AdventOfCodeDay
import kotlin.math.abs
import kotlin.math.floor

class Day01(test: Boolean = false): AdventOfCodeDay<Int>(test, 3, 6) {

	val rotations = input.map { line ->
		val number = line.drop(1).toInt()
		if (line[0] == 'L') {
			-number
		} else if (line[0] == 'R') {
			+number
		} else {
			TODO("Should not happen")
		}
	}

	override fun part1(): Int {
		return rotations.runningFold(50) { prev, rotation ->
			(prev + rotation) % 100
		}.count { it == 0}
	}

	override fun part2(): Int {
		val positions = rotations.runningFold(50 to 0) { (prev, _), rotation ->
			val next = (prev + rotation + 100) % 100
			val zeroPasses = if (rotation > 0) {
				floor((prev + rotation) / 100.0).toInt() - floor(prev / 100.0).toInt()
			} else {
				floor((prev - 1.0) / 100.0).toInt() - floor((prev - 1.0 + rotation) / 100.0).toInt()
			}
			next to zeroPasses
		}
		return positions.sumOf { (_, zeroPasses) ->
			zeroPasses
		}
	}
}

fun main() {
	Day01().run()
}
