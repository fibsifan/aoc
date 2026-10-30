package de.jball.aoc2015

import de.jball.AdventOfCodeDay
import de.jball.aoc2015.day01.Day01
import de.jball.aoc2015.day02.Day02
import de.jball.aoc2015.day03.Day03
import de.jball.aoc2015.day04.Day04
import de.jball.aoc2015.day05.Day05
import de.jball.aoctestutils.AdventOfCodeDayTest
import org.junit.jupiter.params.ParameterizedClass
import org.junit.jupiter.params.provider.MethodSource

@ParameterizedClass(name = "Day {index}")
@MethodSource("getDays")
class Tests2015<T: AdventOfCodeDay<out Any>>(dayConstructor: (Boolean) -> T):
	AdventOfCodeDayTest<T>(dayConstructor) {
	companion object {
		@JvmStatic
		fun getDays() = listOf(::Day01, ::Day02, ::Day03, ::Day04, ::Day05)
	}
}
