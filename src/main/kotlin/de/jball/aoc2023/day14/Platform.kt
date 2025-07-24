package de.jball.aoc2023.day14

import de.jball.aocutils.Direction
import de.jball.aocutils.plus
import de.jball.aocutils.minus
import de.jball.aocutils.parseGrid
import de.jball.aocutils.times

class Platform(
	val map: Map<Pair<Int, Int>, Char>,
	private val width: Int,
	private val height: Int
) {
	companion object {
		fun read(input: List<String>): Platform {
			val platformState = parseGrid(input) { it }
			val height = platformState.keys.maxOf { (row, _) -> row } + 1
			val width = platformState.keys.maxOf { (_, column) -> column } + 1
			val map = platformState.entries.filter { it.value != '.' }.associate { (coordinate, char) -> coordinate to char }
			return Platform(map, height, width)
		}
	}

	fun load() = map.filter { it.value == 'O' }
		.map { height.toLong() - it.key.second.toLong() }
		.sum()

	fun cycle() = shift(Direction.NORTH)
		.shift(Direction.WEST)
		.shift(Direction.SOUTH)
		.shift(Direction.EAST)

	fun shift(direction: Direction): Platform {
		var newMap = this
		var newMapShifted = newMap.shiftOne(direction)
		while (newMapShifted != newMap) {
			newMap = newMapShifted
			newMapShifted = newMapShifted.shiftOne(direction)
		}
		return newMap
	}

	private fun shiftOne(direction: Direction): Platform {
		val entriesSortedByDirection = map.entries.sortedWith { a, b ->
			val directionDiff = ((b.key - a.key) * direction.toPair())
			if (directionDiff.first != 0) directionDiff.first else directionDiff.second
		}

		val newMap = entriesSortedByDirection.associate { (position, char) ->
			if (char != 'O') {
				position to char
			} else {
				val newPosition = position + (direction.toPair() * Pair(1,-1))
				val (newX, newY) = newPosition

				if (!map.containsKey(newPosition) && newX in (0..< width) && newY in (0..< height)) {
					newPosition to char
				} else {
					position to char
				}
			}
		}

		return Platform(newMap, this.width, this.height)
	}

	override fun toString(): String {
		return (0..< width).joinToString("\n") { row ->
			(0..< height).joinToString("") { column ->
				map[column to row]?.toString() ?: "."
			}
		}
	}

	override fun equals(other: Any?): Boolean {
		return other is Platform && map == other.map && width == other.width && height == other.height
	}

	override fun hashCode(): Int {
		return map.hashCode() + width + height
	}
}
