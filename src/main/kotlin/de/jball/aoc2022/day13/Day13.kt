package de.jball.aoc2022.day13

import de.jball.AdventOfCodeDay

class Day13(test: Boolean = false) : AdventOfCodeDay<Int>(test, 13, 140) {
	private val pairs = input.chunked(3).map { Pair(parsePacket(it[0]), parsePacket(it[1])) }

	private fun parsePacket(packetString: String): Packet {
		var workingString = packetString
		val context = mutableListOf<MutableList<Packet>>()
		while (workingString.isNotEmpty()) {
			if (workingString.startsWith("[")) {
				val startedList = mutableListOf<Packet>()
				context.add(startedList)
				workingString = workingString.substring(1)
			} else if (workingString.startsWith("]")) {
				val finishedList = ListPacket(context.removeLast())
				val parent = context.lastOrNull()
				if (parent != null) {
					parent.add(finishedList)
					workingString = workingString.substring(1)
				} else {
					return finishedList
				}
			} else if (workingString.startsWith(",")) {
				workingString = workingString.substring(1)
			} else {
				val numberRegex = "^(\\d+).*".toRegex()
				if (workingString matches numberRegex) {
					val numberString = numberRegex.find(workingString)!!.groups[1]!!.value
					context.last().add(NumberPacket(numberString.toInt()))
					workingString = workingString.substring(numberString.length)
				} else {
					error("Unexpected input $workingString")
				}
			}
		}
		error("Should have returned in while loop")
	}

	override fun part1(): Int {
		return pairs.mapIndexed { index, pair -> Pair(index, pair) }
			.filter { (_, pair) ->
				pair.first <= pair.second
			}
			.sumOf { (index, _) -> index + 1 }
	}

	override fun part2(): Int {
		val divider1 = ListPacket(listOf(ListPacket(listOf(NumberPacket(2)))))
		val divider2 = ListPacket(listOf(ListPacket(listOf(NumberPacket(6)))))
		val sortedArray = pairs.flatMap { it.toList() }.sorted().toTypedArray()

		val indexDivider1 = -sortedArray.binarySearch(divider1)
		val indexDivider2 = -sortedArray.binarySearch(divider2) + 1
		return indexDivider2 * indexDivider1
	}
}

sealed class Packet : Comparable<Packet>

class ListPacket(val packets: List<Packet>) : Packet() {
	override fun compareTo(other: Packet): Int {
		return if (other is ListPacket) {
			val foundPair = packets.zip(other.packets)
				.firstOrNull { (a, b) -> a.compareTo(b) != 0 }
			foundPair?.first?.compareTo(foundPair.second) ?: packets.size.compareTo(other.packets.size)
		} else {
			compareTo(ListPacket(listOf(other)))
		}
	}

	override fun toString(): String {
		return packets.joinToString(",", "[", "]")
	}
}

class NumberPacket(val value: Int) : Packet() {
	override fun compareTo(other: Packet): Int {
		return if (other is NumberPacket) {
			value.compareTo(other.value)
		} else {
			ListPacket(listOf(this)).compareTo(other)
		}
	}

	override fun toString(): String {
		return value.toString()
	}
}

fun main() {
	Day13().run()
}
