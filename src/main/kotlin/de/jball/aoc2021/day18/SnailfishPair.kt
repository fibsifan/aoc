package de.jball.aoc2021.day18

import java.util.*
import kotlin.math.max

class SnailfishPair(
	internal val left: SnailfishNumberComponent,
	internal val right: SnailfishNumberComponent
) : SnailfishNumberComponent() {
	companion object {
		fun fromString(snailfishNumberString: String): SnailfishPair {
			var workingString = snailfishNumberString
			val stack: Deque<SnailfishNumberComponent> = LinkedList()
			while (workingString.isNotEmpty()) {
				if (workingString.startsWith("[")) {
					// do nothing
				} else if (workingString.startsWith("]")) {
					val b = stack.pop()
					val a = stack.pop()
					stack.push(SnailfishPair(a, b))
				} else if (workingString.startsWith(",")) {
					// do nothing
				} else {
					stack.push(SnailfishInt(Regex("^\\d+").find(workingString)!!.value.toInt()))
				}
				workingString = workingString.replace(Regex("^\\d+|^."), "")
			}

			if (stack.size != 1) {
				throw IllegalStateException("Couldn't parse snailfish number: $snailfishNumberString")
			}

			return stack.pop() as SnailfishPair
		}
	}

	init {
		left.parent = this
		right.parent = this
	}

	override fun magnitude(): Long {
		return left.magnitude() * 3L + right.magnitude() * 2L
	}

	override fun height(): Int {
		return max(left.height(), right.height()) + 1
	}

	override fun toString(): String {
		return "[$left,$right]"
	}

	override fun needsSplit(): Boolean {
		return left.needsSplit() || right.needsSplit()
	}

	override fun split(): SnailfishPair {
		return if (left.needsSplit()) {
			SnailfishPair(left.split(), right)
		} else if (right.needsSplit()) {
			SnailfishPair(left, right.split())
		} else {
			throw IllegalStateException("Split called on node that doesnt need splitting: $this")
		}
	}

	private fun needsReduce(): Boolean = needsExplode() || needsSplit()

	internal fun reduced(): SnailfishPair {
		var newPair: SnailfishPair = this
		while (newPair.needsReduce()) {
			if (newPair.needsExplode()) {
				newPair = newPair.explode()
				continue
			}
			if (newPair.needsSplit()) {
				newPair = newPair.split()
			}
		}
		return newPair
	}

	internal fun explode(): SnailfishPair {
		val explosionPoints = findExplosionPoints()

		return rebuildTree(this, explosionPoints)
	}

	private fun rebuildTree(
		node: SnailfishPair,
		explosionPoints: Triple<SnailfishInt?, SnailfishPair, SnailfishInt?>
	): SnailfishPair {
		val newLeftNumber = copyChild(node.left, explosionPoints)
		val newRightNumber = copyChild(node.right, explosionPoints)

		return SnailfishPair(newLeftNumber, newRightNumber)
	}

	private fun copyChild(
		child: SnailfishNumberComponent,
		explosionPoints: Triple<SnailfishInt?, SnailfishPair, SnailfishInt?>
	): SnailfishNumberComponent {
		val (leftNumber, explosionPair, rightNumber) = explosionPoints

		return if (child === leftNumber) {
			SnailfishInt(leftNumber.delegate + (explosionPair.left as SnailfishInt).delegate)
		} else if (child === rightNumber) {
			SnailfishInt(rightNumber.delegate + (explosionPair.right as SnailfishInt).delegate)
		} else if (child === explosionPair) {
			SnailfishInt(0)
		} else if (child is SnailfishInt) {
			SnailfishInt(child.delegate)
		} else {
			rebuildTree(child as SnailfishPair, explosionPoints)
		}
	}

	private fun findExplosionPoints(): Triple<SnailfishInt?, SnailfishPair, SnailfishInt?> {
		val (leftNumber, explodingPair, rightNumber) = findExplosionPoints(
			this,
			Triple(null as SnailfishInt?, null as SnailfishPair?, null as SnailfishInt?)
		)

		return Triple(leftNumber, explodingPair!!, rightNumber)
	}

	private fun findExplosionPoints(
		node: SnailfishPair,
		state: Triple<SnailfishInt?, SnailfishPair?, SnailfishInt?>
	): Triple<SnailfishInt?, SnailfishPair?, SnailfishInt?> {
		val (leftNumber, explodingPair, rightNumber) = state
		if (explodingPair != null) {
			// pair already found, and we're "right" of it, so only searching for the rightNumber.
			if (rightNumber != null) {
				return state
			}

			if (node.left is SnailfishInt) {
				return Triple(leftNumber, explodingPair, node.left)
			} else {
				val leftState = findExplosionPoints(node.left as SnailfishPair, state)
				if (leftState.third != null) {
					return leftState
				}
			}

			if (node.right is SnailfishInt) {
				return Triple(leftNumber, explodingPair, node.right)
			} else {
				val rightState = findExplosionPoints(node.right as SnailfishPair, state)
				if (rightState.third != null) {
					return rightState
				}
			}

			return state
		} else {
			// updating the left number and searching for explosion pair.
			if (node.left is SnailfishInt && node.right is SnailfishInt && node.needsExplode()) {
				return Triple(leftNumber, node, rightNumber)
			}

			val leftState = if (node.left is SnailfishInt) {
				Triple(node.left, null, null)
			} else {
				findExplosionPoints(node.left as SnailfishPair, state)
			}

			return if (leftState.second != null && leftState.third != null) {
				leftState
			} else if (leftState.second != null && node.right is SnailfishInt) {
				Triple(leftState.first, leftState.second, node.right)
			} else if (node.right is SnailfishInt) {
				Triple(node.right, null, null)
			} else {
				findExplosionPoints(node.right as SnailfishPair, leftState)
			}
		}
	}

	operator fun component1(): SnailfishNumberComponent {
		return left
	}

	operator fun component2(): SnailfishNumberComponent {
		return right
	}

	operator fun plus(increment: SnailfishPair): SnailfishPair {
		return SnailfishPair(this, increment)
	}

	override fun equals(other: Any?): Boolean {
		return other is SnailfishPair && left == other.left && right == other.right
	}

	override fun hashCode(): Int {
		return left.hashCode() * 31 + right.hashCode()
	}
}
