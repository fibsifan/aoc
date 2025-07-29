package de.jball.aoc2023.day17

import de.jball.aocutils.Direction
import de.jball.aocutils.Point

data class GridPosition(
	val point: Point,
	val arrived: Pair<Direction, Int>,
	val pathCost: Int
)
