package de.jball.aoc2021.day03

import de.jball.aoc2021.Day

class Day03(test: Boolean = false): Day(test, 198, 230) {
    override fun part1(): Long {
        val total = input.size

        val countOfOnes = input.map {toIntArray(it)}
            .reduce { sumOfOnes, nextOnes -> sumOfOnes.zip(nextOnes) {a, b -> a+b}}

        val gamma = countOfOnes.map { if (it > total/2) 1 else 0 }
        val epsilon = gamma.map{if (it == 1) 0  else 1}

        val gammaInt = toDecimal(gamma)
        val epsilonInt = toDecimal(epsilon)

        return gammaInt * epsilonInt
    }

    override fun part2(): Long {
        var o2Remaining = input
        for (i in input[0].indices) {
            val countOfOnes = o2Remaining.map { it.substring(i,i+1).toInt() }.sum()
            val select = if (countOfOnes * 2 >= o2Remaining.size) "1" else "0"
            o2Remaining = o2Remaining.filter { it.substring(i,i+1).equals(select)}
            if (o2Remaining.size == 1) {
                break
            }
        }

        var co2Remaining = input
        for (i in input[0].indices) {
            val countOfOnes = co2Remaining.map { it.substring(i,i+1).toInt() }.sum()
            val select = if (countOfOnes * 2 < co2Remaining.size) "1" else "0"
            co2Remaining = co2Remaining.filter { it.substring(i,i+1).equals(select)}
            if (co2Remaining.size == 1) {
                break
            }
        }
        val o2Int = o2Remaining[0].toLong(2)
        val co2Int = co2Remaining[0].toLong(2)
        return o2Int * co2Int
    }

    private fun toIntArray(binary: String): List<Long> {
        return binary.chunked(1) .map { it.toLong() }
    }

    private fun toDecimal(binary: List<Int>) = binary.joinToString("") { it.toString() }.toLong(2)
}

fun main() {
    Day03().run()
}
