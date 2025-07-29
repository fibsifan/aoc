package de.jball.aoc2023.day14

import de.jball.aocutils.Direction
import de.jball.aocutils.Grid
import de.jball.aocutils.Point

class Platform(
	map: Map<Point, Char>,
	width: Int,
	height: Int
): Grid<Char>(map, width, height, '.') {
	companion object {
		fun parse(input: List<String>): Platform {
			val tmpGrid = parse(input, '.')
			return Platform(tmpGrid.map, tmpGrid.width, tmpGrid.height)
		}
	}

	fun load() = map.filter { it.value == 'O' }
		.map { it.key.component2().toLong() + 1 }
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
			val directionDiff = ((b.key - a.key) * direction)
			if (directionDiff.component1() != 0) directionDiff.component1() else directionDiff.component2()
		}

		val newMap = entriesSortedByDirection.associate { (position, char) ->
			if (char != 'O') {
				position to char
			} else {
				val newPosition = position + direction
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

	override fun equals(other: Any?): Boolean {
		return other is Platform && map == other.map && width == other.width && height == other.height
	}

	override fun hashCode(): Int {
		return map.hashCode() + width + height
	}
}
