package de.jball.aoc2024.day09

import de.jball.AdventOfCodeDay

class Day09(test: Boolean = false): AdventOfCodeDay<Long>(test, 1928, 2858) {
	val fileDb = input[0].windowed(2, 2, partialWindows = true)
		.map { blocks ->
			val fileSize = blocks.first().toString().toInt()
			val gapSize = if (blocks.length > 1) blocks.last().toString().toInt() else 0
			Pair(fileSize, gapSize)
		}

	override fun part1(): Long {
		val defragmented = fileDb.flatMapIndexed { index, (fileSize, gapSize) ->
			val file = List<Long?>(fileSize) { index.toLong() }
			val gap = List<Long?>(gapSize) { null }
			file + gap
		}.toMutableList()

		var currentGap = defragmented.indexOfFirst { it == null }
		var lastValue = defragmented.indexOfLast { it != null }

		while(currentGap < lastValue) {
			defragmented[currentGap] = defragmented[lastValue]
			defragmented[lastValue] = null
			currentGap = defragmented.indexOfFirst { it == null }
			lastValue = defragmented.indexOfLast { it != null }
		}
		return defragmented.filter { it != null }
			.mapIndexed { index, entry ->
				index.toLong() * entry!!
			}.sum()
	}

	override fun part2(): Long {
		val defragmented = FileSystem(fileDb.mapIndexed { index, (fileSize, gapSize) ->
			FileWithGap(index, fileSize, gapSize)
		}.toMutableList())

		for (currentFile in (fileDb.size - 1 downTo 0)) {
			var currentGap = 0
			val currentFilePos = defragmented.files.indexOfFirst { it.id == currentFile }
			val (_, currentFileSize, currentGapSize) = defragmented.files[currentFilePos]
			while (defragmented.files[currentGap].id != currentFile) {
				if (defragmented.files[currentGap].gap >= currentFileSize) {
					val (file, fileSize, gap) = defragmented.files[currentGap]
					defragmented.files[currentGap] = FileWithGap(file, fileSize, 0)
					defragmented.files.add(currentGap + 1, FileWithGap(currentFile, currentFileSize, gap-currentFileSize))
					defragmented.files.removeAt(currentFilePos+1)
					val (previousFile, previousFileSize, previousGap) = defragmented.files[currentFilePos]
					defragmented.files[currentFilePos] = FileWithGap(previousFile, previousFileSize, previousGap+currentFileSize+currentGapSize)
					break
				}
				currentGap++
			}
		}

		val reduced = defragmented.files.fold(Pair(0L,0L)) { (index, acc), nextFile ->
			val value = (index until index + nextFile.size).sumOf { it * nextFile.id }
			Pair(index + nextFile.size + nextFile.gap, value + acc)
		}
		return reduced.second
	}
}

fun main() {
	Day09().run()
}
