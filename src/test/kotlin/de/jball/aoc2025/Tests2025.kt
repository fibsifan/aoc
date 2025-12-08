package de.jball.aoc2025

import de.jball.AdventOfCodeDay
import de.jball.aoc2025.day01.Day01
import de.jball.aoc2025.day02.Day02
import de.jball.aoctestutils.AdventOfCodeDayTest
import org.junit.jupiter.params.ParameterizedClass
import org.junit.jupiter.params.provider.MethodSource

@ParameterizedClass(name = "Day {index}")
@MethodSource("getDays")
class Tests2025<T: AdventOfCodeDay<out Any>>(dayConstructor: (Boolean) -> T):
	AdventOfCodeDayTest<T>(dayConstructor) {

	companion object {
		@JvmStatic
		fun getDays() = listOf(::Day01, ::Day02)
	}
}
