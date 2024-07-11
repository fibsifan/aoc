package de.jball.aoc2021.day18

import kotlin.test.Test
import kotlin.test.assertEquals

class SnailfishIntTest {
	@Test
	fun testSplitEven() {
		val input = SnailfishInt(10)
		val result = input.split()
		assertEquals(SnailfishPair(SnailfishInt(5), SnailfishInt(5)), result)
	}

	@Test
	fun testSplitUneven() {
		val input = SnailfishInt(11)
		val result = input.split()
		assertEquals(SnailfishPair(SnailfishInt(5), SnailfishInt(6)), result)
	}
}
