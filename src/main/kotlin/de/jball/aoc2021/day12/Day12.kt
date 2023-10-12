package de.jball.aoc2021.day12

import de.jball.aoc2021.Day

class Day12(test: Boolean = false) : Day(test, 226, 3509) {
    private val graph = Graph()
    init {
        input.map {
            it.split("-")
        }.forEach {
            graph.addEdge(it[0], it[1])
        }
    }
    override fun part1(): Long {
        return graph.allPaths("start", "end")
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }
}

class Graph(private val adjacencyList: MutableMap<String, MutableSet<String>> = mutableMapOf()) {
    fun addEdge(start: String, end: String) {
        adjacencyList.getOrPut(start) { mutableSetOf() }.add(end)
        adjacencyList.getOrPut(end) { mutableSetOf() }.add(start)
    }

    fun allPaths(start: String = "start",
                 end: String = "end",
                 visitedSmallCaves: Set<String> = setOf("start"),
                 usedEdgesWithDirection: Set<Pair<String, String>> = emptySet()): Long {

        val nextNodes = adjacencyList[start]!!
            .filter { !visitedSmallCaves.contains(it) }
            .filter { !usedEdgesWithDirection.contains(Pair(start, it)) }
        val endReachable = if (end in nextNodes) 1L else 0L

        return endReachable + nextNodes.filter { end != it }
            .sumOf { node ->
                allPaths(node, end,
                    visitedSmallCaves(visitedSmallCaves, node),
                    usedEdgesWithDirection + Pair(start, node)
                )
            }
    }

    private fun visitedSmallCaves(visitedSmallCaves: Set<String>, node: String): Set<String> {
        return if (node.lowercase() == node) {
            visitedSmallCaves+node
        } else visitedSmallCaves
    }
}

fun main() {
    Day12(false).run()
}