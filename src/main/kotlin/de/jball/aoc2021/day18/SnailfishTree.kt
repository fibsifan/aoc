package de.jball.aoc2021.day18

class SnailfishTree(private val root: SnailfishPair) {
	companion object {
		fun fromString(input: String): SnailfishTree {
			return SnailfishTree(SnailfishPair.fromString(input))
		}
	}

	operator fun plus(otherTree: SnailfishTree): SnailfishTree = SnailfishTree((root + otherTree.root).reduced())

	fun magnitude() = root.magnitude()

	override fun equals(other: Any?) = other is SnailfishTree && root == other.root

	override fun hashCode() = root.hashCode()

	override fun toString() = root.toString()
}
