package de.jball.aoc2021

import de.jball.AdventOfCodeDay
import de.jball.aoc2021.day01.Day01
import de.jball.aoc2021.day02.Day02
import de.jball.aoc2021.day03.Day03
import de.jball.aoc2021.day04.Day04
import de.jball.aoc2021.day05.Day05
import de.jball.aoc2021.day06.Day06
import de.jball.aoc2021.day07.Day07
import de.jball.aoc2021.day08.Day08
import de.jball.aoc2021.day09.Day09
import de.jball.aoc2021.day10.Day10
import de.jball.aoc2021.day11.Day11
import de.jball.aoc2021.day12.Day12
import de.jball.aoc2021.day13.Day13
import de.jball.aoc2021.day14.Day14
import de.jball.aoc2021.day15.Day15
import de.jball.aoc2021.day16.Day16
import de.jball.aoc2021.day17.Day17
import de.jball.aoc2021.day18.Day18
import de.jball.aoctestutils.AdventOfCodeDayTest
import org.junit.jupiter.params.ParameterizedClass
import org.junit.jupiter.params.provider.MethodSource

@ParameterizedClass
@MethodSource("getDays")
class Tests2021<T: AdventOfCodeDay<out Any>>(dayConstructor: (Boolean) -> T):
	AdventOfCodeDayTest<T>(dayConstructor) {
	companion object {
		@JvmStatic
		fun getDays() = listOf(::Day01, ::Day02, ::Day03, ::Day04, ::Day05, ::Day06, ::Day07, ::Day08, ::Day09,
			::Day10, ::Day11, ::Day12, ::Day13, ::Day14, ::Day15, ::Day16, ::Day17, ::Day18)
	}
}
