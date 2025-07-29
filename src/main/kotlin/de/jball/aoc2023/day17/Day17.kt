package de.jball.aoc2023.day17

import de.jball.AdventOfCodeDay
import de.jball.aocutils.Direction
import de.jball.aocutils.Grid
import de.jball.aocutils.Point
import java.util.PriorityQueue
import kotlin.math.abs

class Day17(test: Boolean = false): AdventOfCodeDay<Int>(test, 102, 94) {
	private val grid = Grid.parse(input) { it.digitToInt() }
	private val queue = PriorityQueue(compareBy { pos: GridPosition -> pos.pathCost })
	private val ultraQueue = PriorityQueue(compareBy { pos: GridPosition -> pos.pathCost })
	private val endPos: Pair<Int, Int>
	private val calculated = mutableSetOf<Triple<Point, Direction, Int>>()

	init {
		val start1 = GridPosition(Point(0, grid.width), Pair(Direction.EAST, 0), 0)
		val start2 = GridPosition(Point(0, grid.height), Pair(Direction.SOUTH, 0), 0)
		queue.add(start1)
		queue.add(start2)
		ultraQueue.add(start1)
		ultraQueue.add(start2)

		endPos = Pair(grid.width, 0)
	}

	operator fun GridPosition.plus(other: Triple<Point, Direction, Int>): GridPosition {
		return GridPosition(other.first, Pair(other.second, other.third), this.pathCost + grid.map[other.first]!!)
	}

	operator fun Pair<Int, Int>.times(other: Int): Pair<Int, Int> = Pair(this.first * other, this.second * other)

	private fun key(gridPosition: GridPosition) = Triple(gridPosition.point, gridPosition.arrived.first, gridPosition.arrived.second)

	override fun part1(): Int {

		while (queue.isNotEmpty()) {
			val current = queue.poll()!!
			if (current.point == endPos) {
				return current.pathCost
			}
			if (key(current) !in calculated) {
				queue.addAll(turnLeftAndRight(current))
				calculated.add(key(current))
			}
		}
		TODO("Should not happen")
	}

	private fun turnLeftAndRight(gridPosition: GridPosition): List<GridPosition> {
		val direction = gridPosition.arrived.first
		val newDirections = listOf(direction.turnCounterClockwise(), direction.turnClockwise())
		return newDirections.flatMap { newDirection ->
			(1..3).map { distance ->
				val vector = (Point(0,0) + newDirection) * distance
				Triple(gridPosition.point + vector, newDirection, distance)
			}.filter { grid.map.containsKey(it.first) }.runningFold(gridPosition) { prev, triple ->
				prev + triple
			}
		}
	}

	override fun part2(): Int {
		calculated.clear()
		while (ultraQueue.isNotEmpty()) {
			val current = ultraQueue.poll()!!
			if (current.point == endPos) {
				return current.pathCost
			}
			if (key(current) !in calculated) {
				ultraQueue.addAll(turnUltraLeftAndRight(current))
				calculated.add(key(current))
			}
		}
		TODO("Should not happen")
	}

	private fun turnUltraLeftAndRight(gridPosition: GridPosition): List<GridPosition> {
		val direction = gridPosition.arrived.first
		val directionIndex = Direction.entries.indexOf(direction)
		val newDirections = listOf(Direction.entries[(directionIndex+6) % 8], Direction.entries[(directionIndex+2) % 8])
		val vectors = newDirections.flatMap { newDirection ->
			(1..10).map { distance ->
				val vector = (Point(0,0) + newDirection) * distance
				Triple(gridPosition.point + vector, newDirection, distance)
			}.filter { grid.map.containsKey(it.first) }.runningFold(gridPosition) { prev, triple ->
				prev + triple
			}
		}
		return vectors.filter {
			val diff = it.point - gridPosition.point
			if (diff.x != 0) abs(diff.x) >= 4 else abs(diff.y) >= 4
		}
	}
}

fun main() {
	Day17().run()
}
