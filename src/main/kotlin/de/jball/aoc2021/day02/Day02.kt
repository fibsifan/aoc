package de.jball.aoc2021.day02

import de.jball.AdventOfCodeDay

class Day02(test: Boolean = false): AdventOfCodeDay<Long>(test, 150, 900) {
    private val preparedInput = input.map {
        val split = it.split(" ")
        Pair(split[0], split[1].toLong())
    }

    override fun part1(): Long {
        val hor = preparedInput.filter { "forward" == it.first }.sumOf { it.second }
        val depth = preparedInput.filter { "down" == it.first }.sumOf { it.second } -
                preparedInput.filter { "up" == it.first }.sumOf { it.second }
        return hor * depth
    }

    override fun part2(): Long {
        val aims = preparedInput.runningFold(0L) {
                aim,
                pair ->
            when (pair.first) {
                "down" -> aim + pair.second
                "up" -> aim - pair.second
                else -> aim
            }
        }
        val depthHor = preparedInput.foldIndexed(Pair(0L,0L)) {
                index, depthHor, currentInput ->
            if("forward" == currentInput.first)
                Pair(depthHor.first + aims[index]*currentInput.second,
                    depthHor.second+currentInput.second)
            else
                depthHor
        }
        return depthHor.first*depthHor.second
    }
}

fun main() {
    Day02().run()
}
