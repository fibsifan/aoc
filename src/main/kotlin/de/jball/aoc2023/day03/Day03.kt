package de.jball.aoc2023.day03

import de.jball.AdventOfCodeDay

class Day03(test: Boolean = false): AdventOfCodeDay<Int>(test, 4361, 467835) {
    private val numberRegex = Regex("\\d+")
    private val symbolRegex = Regex("[^0-9.]")

    private val numbers = input.mapIndexed { lineNo, line ->
        numberRegex.findAll(line).map { foundNumber ->
            Triple(lineNo, foundNumber.range, foundNumber.value.toInt())
        }.toList()
    }.flatten()
    private val symbols = input.mapIndexed { lineNo, line ->
        symbolRegex.findAll(line)
            .map { matchResult -> Triple(lineNo, matchResult.range.first, matchResult.value) }
            .toList()
    }.flatten().toSet()
    override fun part1(): Int {

        return numbers
            .filter { numberPosition ->
                getHalo(numberPosition).intersect(symbols.map { (a, b, _) -> Pair(a, b)}.toSet()).isNotEmpty()
            }.sumOf { (_, _,number) -> number }
    }

    private fun getHalo(numberPosition: Triple<Int, IntRange, Int>): Set<Pair<Int, Int>> {
        val columns = IntRange(numberPosition.second.first-1, numberPosition.second.last+1).toList()
        val bottomRow = List(columns.size) { numberPosition.first+1 } zip columns
        val topRow = List(columns.size) {numberPosition.first-1} zip columns
        val startEnd = listOf(Pair(numberPosition.first, columns.first()), Pair(numberPosition.first, columns.last()))
        return (startEnd + topRow + bottomRow).toSet()
    }

    override fun part2(): Int {
        return symbols.filter {
            (_, _, symbol) -> symbol == "*"
        }.sumOf { (a, b, _) ->
            val gearNumbers = numbers.filter { number ->
                getHalo(number).intersect(setOf(Pair(a, b))).isNotEmpty()
            }
            if (gearNumbers.size == 2)
                gearNumbers[0].third * gearNumbers[1].third
            else
                0
        }
    }
}

fun main() {
    Day03().run()
}
