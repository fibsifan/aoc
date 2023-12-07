package de.jball.aoc2023.day04

import de.jball.AdventOfCodeDay

class Day04(test: Boolean = false): AdventOfCodeDay<Int>(test, 13, 30) {
    override fun part1(): Int {
        return input.map {
            parseGame(it)
        }.sumOf { game ->
            val matches = game.first.intersect(game.second)
            if (matches.isEmpty()) {
                0
            } else {
                (1 shl (matches.size-1))
            }
        }
    }

    private fun parseGame(line: String): Pair<Set<Int>, Set<Int>> {
        val numbersStrings = line.split(": ")[1].split(" | ")
        val winningNumbers = numbersStrings[0].chunked(3).map { it.trim().toInt() }.toSet()
        val drawnNumbers = numbersStrings[1].chunked(3).map { it.trim().toInt() }.toSet()
        return Pair(winningNumbers, drawnNumbers)
    }

    override fun part2(): Int {
        return input.foldIndexed(MutableList(input.size) { 1 }) {
                index: Int, gameCopies: MutableList<Int>, line: String ->
            val game = parseGame(line)
            val wins = game.first.intersect(game.second).size
            if (wins > 0) {
                val wonCopies = IntRange(index+1, index+wins)
                wonCopies.forEach { wonCopy ->
                    gameCopies[wonCopy] += gameCopies[index]
                }
            }
            gameCopies
        }.sum()
    }
}

fun main() {
    Day04().run()
}
