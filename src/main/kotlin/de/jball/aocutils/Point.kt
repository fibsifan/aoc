package de.jball.aocutils

data class Point(val x: Int, val y: Int) {
	operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
	operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)
	operator fun plus(direction: Direction): Point {
		val (dx, dy) = direction
		return Point(x + dx, y + dy)
	}
	operator fun minus(direction: Direction): Point {
		val (dx, dy) = direction
		return Point(x - dx, y - dy)
	}
	operator fun times(other: Point) = Point(x * other.x, y * other.y)
	operator fun times(direction: Direction) = Point(x * direction.component1(), y * direction.component2())
	operator fun times(scale: Int) = Point(x * scale, y * scale)
}
