package de.jball.aoc2024.day06

import de.jball.AdventOfCodeDay
import de.jball.aocutils.Direction
import de.jball.aocutils.Grid
import de.jball.aocutils.Point

class Day06(test: Boolean = false): AdventOfCodeDay<Int>(test, 41, 6) {
	private val grid = Grid.parse(input)
	private val maxX = input.first().length-1
	private val maxY = input.size-1
	private val  start = grid.map.entries.first { it.value == '^' }.key
	private val startDirection = Direction.NORTH

	override fun part1(): Int {
		// count the points from walking the grid but without direction
		return walkTheGrid().first.unzip().first.toSet().size
	}

	override fun part2(): Int {
		// only points we visited could potentially create a circle.
		val originalPath = walkTheGrid().first.unzip().first.filter { it != start }.toSet()

		TODO("Not yet implemented")
	}

	fun walkTheGrid(): Pair<Set<Pair<Point, Direction>>, Int> {
		var currentPosition = start
		var currentDirection = startDirection

		val visited = mutableSetOf<Pair<Point, Direction>>()
		while (visited.add(currentPosition to currentDirection)) {
			when (grid.map[currentPosition + currentDirection]) {
				'#' -> currentDirection = currentDirection.turnClockwise()
				'^', '.' -> currentPosition += currentDirection
				// else we are about to walk of the grid and didn't find a circle
				else -> return visited to 0
			}
		}
		// we encountered a circle and landed from the same direction at the same point
		return visited to 1
	}
}

fun main() {
	Day06().run()
}
