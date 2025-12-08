package de.jball.aoctestutils

import de.jball.AdventOfCodeDay
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class AdventOfCodeDayTest<T: AdventOfCodeDay<out Any>>(constr: (Boolean) -> T) {
	val day = constr(true)

	@Test
	fun testPart1() {
		assertEquals(day.expected1, day.part1())
	}

	@Test
	fun testPart2() {
		assertEquals(day.expected2, day.part2())
	}
}
