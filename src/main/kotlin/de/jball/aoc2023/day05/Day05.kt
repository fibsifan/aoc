package de.jball.aoc2023.day05

import de.jball.AdventOfCodeDay

class Day05(test: Boolean = false) : AdventOfCodeDay<Long>(test, 35, 46) {
	private val mappingNames = listOf(
		"seed-to-soil map:",
		"soil-to-fertilizer map:",
		"fertilizer-to-water map:",
		"water-to-light map:",
		"light-to-temperature map:",
		"temperature-to-humidity map:",
		"humidity-to-location map:"
	)
	private val mappings = input.subList(2, input.size)
		.joinToString("--")
		.split("----").associate { mappingString ->
			val mappingList = mappingString.split("--")
			val mappingName = mappingList[0]
			val mapping = mappingList.subList(1, mappingList.size)
				.map { mappingEntry ->
					val mappingEntryComponents = mappingEntry.split(" ").map { it.toLong() }
					val destinationRangeStart = mappingEntryComponents[0]
					val sourceRangeStart = mappingEntryComponents[1]
					val rangeSize = mappingEntryComponents[2] - 1 // end is inclusive for int range
					val destinationRangeEnd = destinationRangeStart + rangeSize
					val sourceRangeEnd = sourceRangeStart + rangeSize
					Pair(
						LongRange(sourceRangeStart, sourceRangeEnd),
						LongRange(destinationRangeStart, destinationRangeEnd)
					)
				}
			Pair(mappingName, mapping)
		}

	override fun part1(): Long {
		val seeds = input[0].split(": ")[1]
			.split(" ")
			.map { it.toLong() }
		return seeds.minOf { seed ->
			val mapper = mappingNames.map { mappings[it]!! }
				.map { mapping -> { a: Long -> applyMapping(mapping, a) } }
				.reduce { acc, nextMapper ->
					{ value -> nextMapper.invoke(acc.invoke(value)) }
				}
			mapper.invoke(seed)
		}
	}

	private fun applyMapping(
		mapping: List<Pair<LongRange, LongRange>>,
		input: Long
	): Long {
		val foundMappingEntry = mapping.find { mappingEntry ->
			mappingEntry.first.contains(input)
		}
		return if (foundMappingEntry != null) {
			input - foundMappingEntry.first.first + foundMappingEntry.second.first
		} else {
			input
		}
	}

	override fun part2(): Long {
		val seeds = input[0].split(": ")[1]
			.split(" ")
			.map { it.toLong() }
			.chunked(2)
			.map { LongRange(it[0], it[0] + it[1] - 1).toList() }
			.flatten()
		return seeds.minOf { seed ->
			val mapper = mappingNames.map { mappings[it]!! }
				.map { mapping -> { a: Long -> applyMapping(mapping, a) } }
				.reduce { acc, nextMapper ->
					{ value -> nextMapper.invoke(acc.invoke(value)) }
				}
			mapper.invoke(seed)
		}
	}
}

fun main() {
	Day05().run()
}
