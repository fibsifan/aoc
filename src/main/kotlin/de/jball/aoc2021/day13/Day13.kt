package de.jball.aoc2021.day13

import de.jball.AdventOfCodeDay
import kotlin.math.abs

class Day13(test: Boolean = false) : AdventOfCodeDay<Long>(test, expected1 = 17L, expected2 = 16L) {
	private val emptyLine = input.indexOf("")
	private val map = input.subList(0, emptyLine)
		.map {
			val split = it.split(",")
			Pair(split[0].toInt(), split[1].toInt())
		}.toSet()

	private val folds = input.subList(emptyLine, input.size)
		.filter { it.isNotBlank() }
		.map { it.substringAfter("fold along ") }
		.map {
			val split = it.split("=")
			Pair(split[0], split[1].toInt())
		}

	override fun part1(): Long {
		return foldMap(folds.subList(0, 1))
	}

	override fun part2(): Long {
		return foldMap(folds, true)
	}

	private fun foldMap(listOfFolds: List<Pair<String, Int>>, print: Boolean = false): Long {
		var currentMap = map
		for (fold in listOfFolds) {
			currentMap = when (fold.first) {
				"x" -> {
					currentMap.map {
						Pair(foldNumber(it.first, fold.second), it.second)
					}.toSet()
				}

				"y" -> {
					currentMap.map {
						Pair(it.first, foldNumber(it.second, fold.second))
					}.toSet()
				}

				else -> {
					throw IllegalStateException()
				}
			}
		}
		if (print) {
			printMap(currentMap)
		}
		return currentMap.size.toLong()
	}

	private fun printMap(currentMap: Set<Pair<Int, Int>>) {
		val xMax = currentMap.maxOf { it.first }
		val yMax = currentMap.maxOf { it.second }
		for (i in 0..yMax) {
			for (j in 0..xMax) {
				if (currentMap.contains(Pair(j, i))) {
					print('#')
				} else {
					print('.')
				}
			}
			println()
		}
	}

	private fun foldNumber(number: Int, fold: Int): Int {
		return if (number < fold)
			number
		else if (number > fold)
			fold - abs(number - fold)
		else
			throw IllegalStateException()
	}
}

fun main() {
	Day13().run()
}
