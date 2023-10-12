package de.jball.aoc2021

import de.jball.AdventOfCodeDay

abstract class Day(
    test: Boolean = false,
    expected1: Long = 0,
    expected2: Long = 0):
    AdventOfCodeDay<Long>("2021", test, expected1, expected2)
