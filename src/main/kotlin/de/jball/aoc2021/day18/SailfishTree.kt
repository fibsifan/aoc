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
        TODO("explode not implemented yet")
    }

    fun magnitude() = root.magnitude()
}
