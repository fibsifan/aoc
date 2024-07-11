package de.jball.aoc2021.day15

import de.jball.AdventOfCodeDay
import java.util.*

class Day15(test: Boolean = false) : AdventOfCodeDay<Long>(test, 40, 315) {
	override fun part1(): Long {
		val cave = Cave(input, false)
		return cave.getTargetRiskSum().toLong()
	}

	override fun part2(): Long {
		val cave = Cave(input, true)
		return cave.getTargetRiskSum().toLong()
	}
}

class Cave(input: List<String>, full: Boolean) {
	private val initialRisks = input.mapIndexed { line, risks ->
		risks.chunked(1).mapIndexed { column, risk ->
			CavePosition(Pair(line, column), risk.toByte())
		}
	}

	private val risks = if (full) {
		(0 until initialRisks.size * 5).map { line ->
			(0 until initialRisks[0].size * 5).map { column ->
				CavePosition(Pair(line, column), transformedRisk(line, column))
			}
		}
	} else {
		initialRisks
	}.also { it[0][0].riskSum = 0 }

	init {
		// this is key to being fast (see wikipedia on Dijkstra):
		val queue = PriorityQueue(compareBy { risk: CavePosition -> risk.riskSum })
		queue.add(risks[0][0])

		while (queue.isNotEmpty()) {
			val current = queue.poll()!!
			if (!current.sumIsMinimal) {
				getNeighbors(current.position)
					.also { neighbors ->
						current.updateOwnDistance(neighbors)
					}
					.filter { !it.sumIsMinimal }
					.forEach { neighbor ->
						neighbor.updateOwnDistance(listOf(current))
						queue.add(neighbor)
					}
				current.sumIsMinimal = true
			}
		}
	}

	private fun getNeighbors(position: Pair<Int, Int>): List<CavePosition> {
		return position.let { (line, column) ->
			listOf(Pair(line + 1, column), Pair(line - 1, column), Pair(line, column + 1), Pair(line, column - 1))
				.filter { it.first in risks.indices && it.second in risks[0].indices }
				.map { risks[it.first][it.second] }
		}
	}

	fun transformedRisk(line: Int, column: Int): Byte {
		val modifier = line / initialRisks.size + column / initialRisks[0].size
		return ((initialRisks[line % initialRisks.size][column % initialRisks[0].size].risk - 1 + modifier) % 9 + 1).toByte()
	}

	fun getTargetRiskSum(): Int {
		return risks.last().last().riskSum
	}
}

private class CavePosition(
	val position: Pair<Int, Int>,
	val risk: Byte,
	var riskSum: Int = Int.MAX_VALUE - 10,
	var sumIsMinimal: Boolean = false
) {
	fun updateOwnDistance(neighbors: List<CavePosition>) {
		val newMin = neighbors.minOf { it.riskSum } + risk
		if (newMin < riskSum) {
			riskSum = newMin
		}
	}
}


fun main() {
	Day15(false).run()
}
