package de.jball.aocutils

enum class Direction(private val horizontal: Int, private val vertical: Int) {
	NORTH(0, 1),
	NORTHEAST(1, 1),
	EAST(1, 0),
	SOUTHEAST(1, -1),
	SOUTH(0, -1),
	SOUTHWEST(-1, -1),
	WEST(-1, 0),
	NORTHWEST(-1, 1);

	companion object {
		val DIAGONALS = entries.filter { (horizontal, vertical) -> horizontal != 0 && vertical != 0 }
	}

	operator fun component1() = horizontal
	operator fun component2() = vertical
	fun toPair(): Pair<Int, Int> {
		return Pair(horizontal, vertical)
	}

	fun turnClockwise() = Direction.entries[(this.ordinal + 2) %8]
	fun turnCounterClockwise() = Direction.entries[(this.ordinal + 6) %8]
}
