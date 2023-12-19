package de.jball.aoc2023.day09

import de.jball.AdventOfCodeDay

class Day09(test: Boolean = false): AdventOfCodeDay<Int>(test, 114, 2) {
    private val sequences = input.map { sequenceString ->
        sequenceString.split(" ").map { it.toInt() }
    }

    override fun part1(): Int {
        return sequences.sumOf { sequence ->
            extrapolateNext(sequence)
        }
    }

    override fun part2(): Int {
        return sequences.sumOf { sequence ->
            extrapolatePrevious(sequence)
        }
    }

    private fun extrapolateNext(sequence: List<Int>): Int {
        val deltaSequences = buildDeltaSequences(sequence)

        for (i in deltaSequences.size-1 downTo 1) {
            deltaSequences[i-1].add(deltaSequences[i-1].last() + deltaSequences[i].last())
        }
        return deltaSequences[0].last()
    }

    private fun extrapolatePrevious(sequence: List<Int>): Int {
        val deltaSequences = buildDeltaSequences(sequence)

        for (i in deltaSequences.size-1 downTo 1) {
            deltaSequences[i-1].add(0, deltaSequences[i-1].first() - deltaSequences[i].first())
        }
        return deltaSequences[0].first()
    }

    private fun buildDeltaSequences(sequence: List<Int>): MutableList<MutableList<Int>> {
        val deltaSequences = mutableListOf(sequence.toMutableList())
        while (deltaSequences.last().zipWithNext { a, b -> a == b }.any { !it }) {
            deltaSequences.add(deltaSequences.last().zipWithNext { a, b -> b - a }.toMutableList())
        }
        return deltaSequences
    }
}

fun main() {
    Day09().run()
}
