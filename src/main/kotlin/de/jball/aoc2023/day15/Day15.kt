package de.jball.aoc2023.day15

import de.jball.AdventOfCodeDay

class Day15(test: Boolean = false) : AdventOfCodeDay<Int>(test, 1320, 145) {
	private val steps = input[0].split(",")
	override fun part1(): Int {
		return steps.sumOf { hash(it) }
	}

	fun hash(step: String): Int {
		return step.toCharArray()
			.map { char ->
				char.code
			}
			.fold(0) { previous, next ->
				((previous + next) * 17) % 256
			}
	}

	override fun part2(): Int {
		val boxes = mutableMapOf<Int, Box>()
		steps.map { step ->
			val label = step.substringBefore('=').substringBefore('-')
			Pair(label, getOperation(step.substringAfter(label)))
		}.forEach { step ->
			val box = boxes.computeIfAbsent(hash(step.first)) { _ -> Box() }
			box.perform(step)
		}

		return boxes.entries.sumOf { (boxNumber, box) ->
			box.lenses.mapIndexed { lensNumber, lens ->
				(boxNumber + 1) * (lensNumber + 1) * lens.focalLength
			}.sum()
		}
	}

	private fun getOperation(operator: String): Operation {
		return if (operator.startsWith("-")) {
			Remove
		} else {
			SetLens(operator.drop(1).toInt())
		}
	}
}

sealed class Operation
data object Remove : Operation()
data class SetLens(val focalLength: Int) : Operation()
data class Lens(val label: String, val focalLength: Int)
data class Box(val lenses: MutableList<Lens> = mutableListOf()) {
	fun perform(step: Pair<String, Operation>) {
		if (step.second is Remove) {
			lenses.removeIf { lens -> lens.label == step.first }
		} else if (lenses.any { lens -> lens.label == step.first }) {
			val operation: SetLens = step.second as SetLens
			lenses.replaceAll { lens ->
				if (lens.label != step.first) lens else Lens(
					step.first,
					operation.focalLength
				)
			}
		} else {
			val operation: SetLens = step.second as SetLens
			lenses.add(Lens(step.first, operation.focalLength))
		}
	}
}

fun main() {
	Day15().run()
}
