package de.jball.aocutils

open class Grid<T>(val map: Map<Point, T>, val width: Int, val height: Int, private val default: T? = null) {
	companion object {
		fun parse(input: List<String>, default: Char? = null): Grid<Char> {
			return parse(input, default) { it }
		}

		fun <T> parse(input: List<String>, default: Char? = null, transform: (Char) -> T): Grid<T> {
			val map = parseGridMap(input, transform, default)
			return Grid(map, input.first().length, input.size, default?.let { transform(it) })
		}

		private fun <T> parseGridMap(input: List<String>, transform: (input: Char) -> T, skip: Char?): Map<Point, T> {
			val maxLine = input.size - 1
			return input.flatMapIndexed { lineNo, line ->
				line.mapIndexedNotNull { colNo, letter ->
					if (letter != skip) Pair(Point(colNo, maxLine - lineNo), transform(letter)) else null
				}
			}.toMap()
		}
	}

	override fun toString(): String {
		return (0 ..< height).map { row ->
			(0 ..< width).map { column ->
				map[Point(column, row)] ?: default
			}.joinToString("") { it.toString() }
		}.joinToString(System.lineSeparator()) { it }
	}
}
