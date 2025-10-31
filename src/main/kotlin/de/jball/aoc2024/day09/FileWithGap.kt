package de.jball.aoc2024.day09

data class FileWithGap(val id: Int, val size: Int, val gap: Int) {
	override fun toString(): String {
		return List(size) { id.toString() }.joinToString("") + List(gap) { "." }.joinToString("")
	}
}
