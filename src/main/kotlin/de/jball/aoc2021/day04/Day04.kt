package de.jball.aoc2021.day04

import de.jball.AdventOfCodeDay

class Day04(test: Boolean = false) : AdventOfCodeDay<Long>(test, 4512, 1924) {
	private val randomNumbers = input[0].split(",").map { it.toLong() }
	private val boards = input.subList(1, input.size)
		.chunked(6)
		.map { parseBoard(it) }

	override fun part1(): Long {
		for ((index, randomNumber) in randomNumbers.withIndex()) {
			boards.forEach { it.check(randomNumber) }
			if (index > 5) {
				val winningBoards = boards.filter { it.hasWon() }
				if (winningBoards.isNotEmpty()) {
					return winningBoards[0].sum * randomNumber
				}
			}
		}
		TODO("What if we get here?")
	}

	override fun part2(): Long {
		val boardsInTheGame = boards.toMutableList()
		for ((index, randomNumber) in randomNumbers.withIndex()) {
			boards.forEach { it.check(randomNumber) }
			if (index > 5) {
				if (boardsInTheGame.size > 1) {
					val winningBoards = boards.filter { it.hasWon() }
					boardsInTheGame.removeAll(winningBoards)
				} else if (boardsInTheGame[0].hasWon()) {
					return boardsInTheGame[0].sum * randomNumber
				}
			}
		}
		TODO("What if we get here?")
	}

	private fun parseBoard(lines: List<String>): Board {
		check(lines.size == 6)
		check(lines[0].isBlank())
		return Board(lines.subList(1, lines.size))
	}
}

class Board(text: List<String>) {
	private val rowHitCounts = mutableListOf(0, 0, 0, 0, 0)
	private val colHitCounts = mutableListOf(0, 0, 0, 0, 0)
	private val board = createBoardMap(text)
	var sum = 0L

	private fun createBoardMap(text: List<String>): MutableMap<Long, Pair<Int, Int>> {
		val board = mutableMapOf<Long, Pair<Int, Int>>()
		for ((i, line) in text.withIndex()) {
			val chunkedLine = line.chunked(3) { it.trim().toString() }
			for ((j, number) in chunkedLine.map { it.toLong() }.withIndex()) {
				board[number] = Pair(i, j)
				sum += number
			}
		}
		return board
	}

	fun check(number: Long) {
		if (board.containsKey(number)) {
			rowHitCounts[board[number]!!.first] += 1
			colHitCounts[board[number]!!.second] += 1
			sum -= number
			board.remove(number)
		}
	}

	fun hasWon(): Boolean {
		return rowHitCounts.any { it == 5 } || colHitCounts.any { it == 5 }
	}
}

fun main() {
	Day04().run()
}
