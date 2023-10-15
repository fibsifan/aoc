package de.jball.aoc2015.day03

data class Coordinate(val x: Int, val y: Int) {
    operator fun plus(other: Coordinate): Coordinate  {
        return Coordinate(x+other.x, y+other.y)
    }
}
