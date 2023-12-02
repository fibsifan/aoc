package de.jball.aoc2023.day02

import de.jball.AdventOfCodeDay

class Day02(test: Boolean = false) : AdventOfCodeDay<Int>(test, 8, 2286) {

    private val part1Maximums = mapOf(
        Color.red to 12,
        Color.green to 13,
        Color.blue to 14
    )
    override fun part1(): Int {
        return parseGameList(input1)
            .filter {(_, game) -> !game.any { maximumTopped(it) } }
            .sumOf { (gameId, _) -> gameId }
    }

    private fun parseGameList(lines: List<String>) = lines.asSequence()
        .map { line -> line.split(": ") }
        .map { line -> Pair(parseGameId(line[0]), line[1]) }
        .map { (gameId, gameString) ->
            val game = parseGame(gameString)
            Pair(gameId, game)
        }

    private fun maximumTopped(round: Map<Color, Int>): Boolean {
        return round.entries.any { part1Maximums[it.key]!! < it.value }
    }

    private fun parseGameId(gameIdString: String) : Int {
        return Regex("Game (\\d+)").matchEntire(gameIdString)!!.groups[1]!!.value.toInt()
    }

    private fun parseGame(gameString: String) : List<Map<Color, Int>> {
        val roundStrings = gameString.split("; ")
        return roundStrings.map { parseRound(it) }
    }

    private fun parseRound(roundString: String): Map<Color, Int> {
        return roundString.split(", ").map {
            val result = it.split(" ")
            val color = Color.valueOf(result[1])
            val count = result[0].toInt()
            Pair(color, count)
        }.toMap()
    }

    override fun part2(): Int {
        return parseGameList(input2)
            .sumOf {
                getGamePower(it.second)
            }
    }

    private fun getGamePower(game: List<Map<Color, Int>>) : Int {
        val bluePower = maxByColor(game, Color.blue)
        val greenPower = maxByColor(game, Color.green)
        val redPower = maxByColor(game, Color.red)
        return bluePower * greenPower * redPower
    }

    private fun maxByColor(game: List<Map<Color, Int>>, color: Color) : Int {
        return game.maxOf { it[color] ?: Int.MIN_VALUE }
    }
}

enum class Color {
    red, blue, green
}

fun main() {
    Day02().run()
}
