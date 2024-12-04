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

	operator fun component1() = horizontal
	operator fun component2() = vertical
	fun toPair(): Pair<Int, Int> {
		return Pair(horizontal, vertical)
	}
}
