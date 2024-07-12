package de.jball.aoc2023.day14

enum class Direction(private val horizontal: Int, private val vertical: Int) {
	NORTH(0, -1),
	EAST(1, 0),
	SOUTH(0, 1),
	WEST(-1, 0);

	operator fun component1() = horizontal
	operator fun component2() = vertical
}
