package de.jball.aoc2023.day17

data class GridPosition(
	val point: Pair<Int, Int>,
	val arrived: Pair<Direction, Int>,
	val pathCost: Int
)
