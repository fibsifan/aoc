package de.jball.aoc2023.day08

import de.jball.AdventOfCodeDay

class Day08(test: Boolean = false): AdventOfCodeDay<Long>(test, 6, 6) {
    private val path1 = input1[0].chunked(1)
    private val map1 = input1.drop(2).associate { line ->
        val split = line.split(" = ")
        Pair(split[0], toDirections(split[1]))
    }

    private val path2 = input2[0].chunked(1)
    private val map2 = input2.drop(2).associate { line ->
        val split = line.split(" = ")
        Pair(split[0], toDirections(split[1]))
    }

    private fun toDirections(directionsString: String): Directions {
        val matched = Regex("\\((\\w+), (\\w+)\\)").matchEntire(directionsString)!!
        return Directions(matched.groups[1]!!.value, matched.groups[2]!!.value)
    }


    override fun part1(): Long {
        var current = "AAA"
        var iterations = 0
        while (current != "ZZZ") {
            current = applyPath1(current)
            iterations++
        }
        return iterations.toLong() * path1.size.toLong()
    }

    private fun applyPath1(start: String): String {
        return path1.fold(start) { current, step ->
            if (step == "L")
                map1[current]!!.left
            else
                map1[current]!!.right
        }
    }

    private fun applyPath2(start: String): String {
        return path2.fold(start) { current, step ->
            if (step == "L")
                map2[current]!!.left
            else
                map2[current]!!.right
        }
    }

    override fun part2(): Long {
        var positions = map2.keys.filter { it.endsWith("A") }
        var minIterations = List(positions.size) { Long.MAX_VALUE }
        var iteration = 0L

        while (positions.any { !it.endsWith("Z") } && minIterations.any { it == Long.MAX_VALUE}) {
            positions = positions.map { current -> applyPath2(current) }
            iteration++
            minIterations = minIterations.mapIndexed { index, minIteration ->
                if (minIteration < Long.MAX_VALUE) {
                    minIteration
                } else if (positions[index].endsWith("Z")) {
                    iteration
                } else {
                    minIteration
                }
            }
        }

        return minIterations.reduce { a, b -> findLCM(a, b) } * path2.size
    }

    private fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }
}

data class Directions(val left: String, val right: String)

fun main() {
    Day08().run()
}
