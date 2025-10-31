package de.jball.aoc2024.day09

data class FileSystem(val files: MutableList<FileWithGap>) {
	override fun toString(): String {
		return files.joinToString("")
	}
}
