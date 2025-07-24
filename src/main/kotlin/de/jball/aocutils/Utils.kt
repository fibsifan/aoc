package de.jball.aocutils

import kotlin.math.abs

fun manhattanDistance(a: Pair<Int, Int>, b: Pair<Int, Int>): Int =
	abs(a.first - b.first) + abs(a.second - b.second)

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
	return Pair(this.first + other.first, this.second + other.second)
}

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
	return Pair(this.first - other.first, this.second - other.second)
}
