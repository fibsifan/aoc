package de.jball.aoc2024

import de.jball.AdventOfCodeDay
import de.jball.aoc2024.day01.Day01
import de.jball.aoc2024.day02.Day02
import de.jball.aoc2024.day03.Day03
import de.jball.aoc2024.day04.Day04
import de.jball.aoc2024.day05.Day05
import de.jball.aoc2024.day06.Day06
import de.jball.aoc2024.day07.Day07
import de.jball.aoc2024.day08.Day08
import de.jball.aoc2024.day09.Day09
import de.jball.aoctestutils.AdventOfCodeDayTest
import org.junit.jupiter.params.ParameterizedClass
import org.junit.jupiter.params.provider.MethodSource

@ParameterizedClass(name = "Day {index}")
@MethodSource("getDays")
class Tests2024<T: AdventOfCodeDay<out Any>>(dayConstructor: (Boolean) -> T):
	AdventOfCodeDayTest<T>(dayConstructor) {
	companion object {
		@JvmStatic
		fun getDays() = listOf(::Day01, ::Day02, ::Day03, ::Day04, ::Day05, ::Day06, ::Day07, ::Day08, ::Day09)
	}
}
