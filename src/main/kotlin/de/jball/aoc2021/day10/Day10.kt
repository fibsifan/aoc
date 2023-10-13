package de.jball.aoc2021.day10

import de.jball.AdventOfCodeDay
import java.util.ArrayDeque
import kotlin.IllegalArgumentException

class Day10(test: Boolean = false): AdventOfCodeDay<Long>(test, 26397, 288957) {
    private val chunks = input.map { line-> line.chunked(1) }
    enum class Chunk(val char: String, val corruptionScore: Long = 0L, val completionScore: Long = 0L) {
        OPENING_PARENTHESIS("("),
        OPENING_BRACKET("["),
        OPENING_BRACE("{"),
        OPENING_HAT("<"),
        CLOSING_PARENTHESIS(")", 3L, 1),
        CLOSING_BRACKET("]", 57L, 2),
        CLOSING_BRACE("}",1197L, 3),
        CLOSING_HAT(">", 25137L, 4);

        fun isOpening(): Boolean {
            return this.name.startsWith("OPENING")
        }

        fun findClosingPartner(): Chunk {
            return values().filter { it.name.startsWith("CLOSING") }
                .first { it.name.endsWith(this.name.removePrefix("OPENING_")) }
        }

        companion object {
            fun findByName(chunkChar: String): Chunk {
                val result = values().firstOrNull { it.char == chunkChar }
                if (result == null) {
                    throw IllegalArgumentException("$chunkChar is not a valid char.")
                } else return result
            }
        }
    }

    override fun part1(): Long {
        return chunks.mapNotNull { corruptionScore(it) }.sum()
    }

    private fun corruptionScore(line: List<String>): Long? {
        val stack = ArrayDeque<Chunk>()
        for (chunkChar in line) {
            val chunk = Chunk.findByName(chunkChar)
            if (chunk.isOpening()) {
                stack.push(chunk.findClosingPartner())
            } else if (chunk != stack.pop()) {
                return chunk.corruptionScore
            }
        }
        return null
    }

    override fun part2(): Long {
        val completionScores = chunks.mapNotNull { completionScore(it) }.sorted()
        return completionScores[completionScores.size/2]
    }

    private fun completionScore(line: List<String>): Long? {
        val stack = ArrayDeque<Chunk>()
        for (chunkChar in line) {
            val chunk = Chunk.findByName(chunkChar)
            if (chunk.isOpening()) {
                stack.push(chunk.findClosingPartner())
            } else if (chunk != stack.pop()) {
                return null
            }
        }
        return calculateCompletionScore(stack)
    }

    private fun calculateCompletionScore(stack: ArrayDeque<Chunk>): Long {
        var score = 0L
        while (stack.isNotEmpty()) {
            score = score*5 + stack.pop().completionScore
        }
        return score
    }
}

fun main() {
    Day10().run()
}
