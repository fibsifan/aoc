package de.jball.aoc2022
import de.jball.AdventOfCodeDay

abstract class Day<T>(
    test: Boolean = false,
    expected1: T,
    expected2: T): AdventOfCodeDay<T>("2022", test, expected1, expected2) {
}
