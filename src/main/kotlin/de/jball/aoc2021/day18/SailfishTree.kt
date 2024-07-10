package de.jball.aoc2021.day18

class SailfishTree(private var root: SailfishPair) {
    companion object {
        fun fromString(input: String): SailfishTree {
            return SailfishTree(SailfishPair.fromString(input))
        }
    }

    operator fun plus(otherTree: SailfishTree): SailfishTree {
        val newTree = SailfishTree(root + otherTree.root)
        if (newTree.needsReduce()) {
            newTree.reduce()
        }
        return newTree
    }

    private fun needsReduce(): Boolean = root.needsExplode() || root.needsSplit()

    private fun reduce() {
        while (needsReduce()) {
            if (root.needsExplode()) {
                explode()
                continue
            }
            if (root.needsSplit()) {
                root = root.split()
            }
        }
    }

    fun explode() {
        val (leftNumber, explodingPair, rightNumber) = findExplosionPoints()

        if (leftNumber != null) {
            leftNumber.delegate += (explodingPair.left as SailfishInt).delegate
        }
        if (rightNumber != null) {
            rightNumber.delegate += (explodingPair.right as SailfishInt).delegate
        }

        val parent = explodingPair.parent!!
        if (parent.left === explodingPair) {
            parent.left = SailfishInt(0)
        } else if (parent.right === explodingPair) {
            parent.right = SailfishInt(0)
        } else {
            throw IllegalStateException("Exploding pair is not part of it's own parent.")
        }
    }

    private fun findExplosionPoints(): Triple<SailfishInt?, SailfishPair, SailfishInt?> {
        val (leftNumber, explodingPair, rightNumber) = findExplosionPoints(root,
            Triple(null as SailfishInt?, null as SailfishPair?, null as SailfishInt?))

        return Triple(leftNumber, explodingPair!!, rightNumber)
    }

    private fun findExplosionPoints(node: SailfishPair, state: Triple<SailfishInt?, SailfishPair?, SailfishInt?>): Triple<SailfishInt?, SailfishPair?, SailfishInt?> {
        val (leftNumber, explodingPair, rightNumber) = state
        if (explodingPair != null) {
            // pair already found, and we're "right" of it, so only searching for the rightNumber.
            if (rightNumber != null) {
                return state
            }

            if (node.left is SailfishInt) {
                return Triple(leftNumber, explodingPair, node.left as SailfishInt)
            } else {
                val leftState = findExplosionPoints(node.left as SailfishPair, state)
                if (leftState.third != null) {
                    return leftState
                }
            }

            if (node.right is SailfishInt) {
                return Triple(leftNumber, explodingPair, node.right as SailfishInt)
            } else {
                val rightState =  findExplosionPoints(node.right as SailfishPair, state)
                if (rightState.third != null) {
                    return rightState
                }
            }

            return state
        } else {
            // updating the left number and searching for explosion pair.
            if (node.left is SailfishInt && node.right is SailfishInt && node.needsExplode()) {
                return Triple(leftNumber, node, rightNumber)
            }

            val leftState = if (node.left is SailfishInt) {
                Triple(node.left as SailfishInt, null, null)
            } else {
                findExplosionPoints(node.left as SailfishPair, state)
            }

            return if (leftState.second != null && leftState.third != null) {
                leftState
            } else if (leftState.second != null && node.right is SailfishInt) {
                Triple(leftState.first, leftState.second, node.right as SailfishInt)
            } else if (node.right is SailfishInt) {
                Triple(node.right as SailfishInt, null, null)
            } else {
                findExplosionPoints(node.right as SailfishPair, leftState)
            }
        }
    }

    fun magnitude() = root.magnitude()

    override fun equals(other: Any?) = other is SailfishTree && root == other.root

    override fun hashCode() = root.hashCode()

    override fun toString() = root.toString()
}
