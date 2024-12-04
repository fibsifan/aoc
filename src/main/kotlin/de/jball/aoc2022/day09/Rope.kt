package de.jball.aoc2022.day09

import kotlin.math.abs
import kotlin.math.sign
import de.jball.aocutils.Direction

class Rope(length: Int) {
	private val knots = MutableList(length) { Pair(0, 0) }

	/**
	 * @return tail-trail of that movement
	 */
	fun move(direction: Direction, steps: Int): List<Pair<Int, Int>> {
		val tailTrail = mutableListOf<Pair<Int, Int>>()
		repeat(steps) {
			val head = knots[0]
			val (x, y) = direction
			knots[0] = Pair(head.first + x, head.second + y)
			followHead()
			tailTrail.add(knots.last())
		}
		return tailTrail
	}

	private fun followHead() {
		for (i in 1 until knots.size) {
			val leader = knots[i - 1]
			val knot = knots[i]
			knots[i] = if (abs(leader.first - knot.first) > 1 || abs(leader.second - knot.second) > 1) {
				Pair(
					reduceDistanceIfAboveLength(leader.first, knot.first),
					reduceDistanceIfAboveLength(leader.second, knot.second)
				)
			} else {
				knot
			}
		}
	}

	private fun reduceDistanceIfAboveLength(a: Int, b: Int) = b + (a - b).sign
}
