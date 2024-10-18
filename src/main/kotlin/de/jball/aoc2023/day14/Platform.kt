package de.jball.aoc2023.day14

class Platform(
	val map: Map<Pair<Int, Int>, Char>,
	private val width: Int,
	private val height: Int
) {
	companion object {
		fun read(input: List<String>): Platform {
			val platformState = input.flatMapIndexed { row: Int, line: String ->
				line.mapIndexed { column: Int, char: Char ->
					Pair(column + 1, row + 1) to char
				}
			}.toMap()
			val height = platformState.keys.maxOf { (row, _) -> row }
			val width = platformState.keys.maxOf { (_, column) -> column }
			val map = platformState.entries.filter { it.value != '.' }.associate { (coordinate, char) -> coordinate to char }
			return Platform(map, height, width)
		}
	}

	fun load() = map.filter { it.value == 'O' }
		.map { height.toLong() - it.key.second.toLong() + 1 }
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
		val (horizontal, vertical) = direction
		val newMap = map.map { (position, char) ->
			if (char != 'O') {
				position to char
			} else {
				val (x, y) = position
				val newX = x + horizontal
				val newY = y + vertical
				val newPosition = newX to newY
				if (!map.containsKey(newPosition) && newX in (1..width) && newY in (1..height)) {
					newPosition to char
				} else {
					position to char
				}
			}
		}.toMap()

		return Platform(newMap, this.width, this.height)
	}

	override fun toString(): String {
		return (1..width).joinToString("\n") { row ->
			(1..height).joinToString("") { column ->
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
