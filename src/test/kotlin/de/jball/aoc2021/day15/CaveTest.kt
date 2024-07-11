package de.jball.aoc2021.day15

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

internal class CaveTest {
	private val testee = Cave(listOf("11", "11"), false)

	@Test
	fun transformedRisk() {
		assertAll(
			{ assertEquals(1, testee.transformedRisk(0, 0)) },
			{ assertEquals(1, testee.transformedRisk(0, 1)) },
			{ assertEquals(1, testee.transformedRisk(1, 1)) },
			{ assertEquals(1, testee.transformedRisk(1, 0)) },
			{ assertEquals(2, testee.transformedRisk(0, 2)) },
			{ assertEquals(3, testee.transformedRisk(2, 2)) },
			{ assertEquals(2, testee.transformedRisk(2, 0)) }
		)
	}
}
