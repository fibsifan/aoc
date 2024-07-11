package de.jball.aoc2021.day12

class Graph(private val adjacencyList: MutableMap<String, MutableSet<String>> = mutableMapOf()) {
    fun addEdge(start: String, end: String) {
        adjacencyList.getOrPut(start) { mutableSetOf() }.add(end)
        adjacencyList.getOrPut(end) { mutableSetOf() }.add(start)
    }

    fun allPaths(
        smallCaveVisitedTwice: String?,
        start: String = "start",
        visitedSmallCaves: Set<String> = setOf("start"),
    ): Long {
        val nextNodes = adjacencyList[start]!!.filter {
                (smallCaveVisitedTwice == null && it != "start" && it != "end") || !visitedSmallCaves.contains(it)
            }
        val endReachable = if ("end" in nextNodes)
            1L
        else
            0L

        return endReachable + nextNodes.filter { "end" != it }.sumOf { node ->
                allPaths(
                    smallCaveVisitedTwice(smallCaveVisitedTwice, visitedSmallCaves, node),
                    node,
                    visitedSmallCaves(visitedSmallCaves, node)
                )
            }
    }

    private fun visitedSmallCaves(visitedSmallCaves: Set<String>, node: String): Set<String> {
        return if (node.lowercase() == node) {
            visitedSmallCaves + node
        } else visitedSmallCaves
    }

    private fun smallCaveVisitedTwice(
        smallCaveVisitedTwice: String?, visitedSmallCaves: Set<String>, node: String
    ): String? {
        return smallCaveVisitedTwice ?: if (node != "start" && node != "end" && node.lowercase() == node && visitedSmallCaves.contains(node)) {
            node
        } else {
            null
        }
    }
}
