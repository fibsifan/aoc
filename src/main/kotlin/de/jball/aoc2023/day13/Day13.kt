package de.jball.aoc2023.day13

import de.jball.AdventOfCodeDay
import kotlin.math.max
import kotlin.math.min

class Day13(test: Boolean = false): AdventOfCodeDay<Int>(test, 405, 400) {
    private val patterns = input.joinToString("J")
        .split("JJ")
        .map { pattern -> pattern.split("J") }
        .map { parsePattern(it) }

    fun parsePattern(patternStrings: List<String>): Pattern {
        val patternMap = patternStrings.mapIndexed { lineNumber, line ->
            line.mapIndexed { columnNumber, character ->
                Pair(Pair(lineNumber+1, columnNumber+1), character == '#')
            }
        }.flatten().toMap()
        return Pattern(patternMap)
    }

    override fun part1(): Int {
        val horizontalSum = patterns.mapNotNull { it.findHorizontalMirror() }.sumOf { it * 100 }
        val verticalSum = patterns.mapNotNull { it.findVerticalMirror() }.sumOf { it }
        return horizontalSum + verticalSum
    }

    override fun part2(): Int {
        val horizontalSum = patterns.mapNotNull {
            it.findSmudgedHorizontalMirror()
        }.sumOf { it * 100 }
        val verticalSum = patterns.mapNotNull {
            it.findSmudgedVerticalMirror()
        }.sumOf { it }
        return horizontalSum + verticalSum
    }
}

data class Pattern(val pattern: Map<Pair<Int, Int>, Boolean>) {
    private val height: Int = pattern.entries.maxOf { it.key.first }
    private val width: Int = pattern.entries.maxOf { it.key.second }

    private fun mirrorHorizontally(rowsAbove: Int): Boolean {
        return (1..width).none { column ->
            (1..min(rowsAbove, height-rowsAbove)).any { line ->
                pattern[Pair(rowsAbove+1-line, column)] != pattern[Pair(rowsAbove+line, column)]
            }
        }
    }

    private fun mirrorSmudgedHorizontally(rowsAbove: Int): Boolean {
        return (1..width).sumOf { column ->
            (1..min(rowsAbove, height - rowsAbove)).count { line ->
                pattern[Pair(rowsAbove+1-line, column)] != pattern[Pair(rowsAbove+line, column)]
            }
        } == 1
    }

    private fun mirrorVertically(rowsLeft: Int): Boolean {
        return (1..min(rowsLeft, width-rowsLeft)).none { column ->
            (1..height).any { line ->
                pattern[Pair(line, rowsLeft+1-column)] != pattern[Pair(line, rowsLeft+column)]
            }
        }
    }

    private fun mirrorSmudgedVertically(rowsLeft: Int): Boolean {
        return (1..min(rowsLeft, width-rowsLeft)).sumOf { column ->
            (1..height).count { line ->
                pattern[Pair(line, rowsLeft+1-column)] != pattern[Pair(line, rowsLeft+column)]
            }
        } == 1
    }

    fun findHorizontalMirror(): Int? {
        return (1 ..< height).firstOrNull { mirrorHorizontally(it) }
    }

    fun findSmudgedHorizontalMirror(): Int? {
        return (1 ..< height).firstOrNull { mirrorSmudgedHorizontally(it) }
    }

    fun findVerticalMirror(): Int? {
        return (1 ..< width).firstOrNull { mirrorVertically(it) }
    }

    fun findSmudgedVerticalMirror(): Int? {
        return (1 ..< width).firstOrNull { mirrorSmudgedVertically(it) }
    }

    override fun toString(): String {
        return (1..height).map { line ->
            (1..width).map { column ->
                if (pattern[Pair(line, column)]!!) {
                    '#'
                } else {
                    '.'
                }
            }.joinToString("")
        }.joinToString("\n")
    }
}

fun main() {
    Day13().run()
}
