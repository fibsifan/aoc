package de.jball.aocutils

import org.junit.jupiter.params.ParameterizedClass
import org.junit.jupiter.params.provider.CsvFileSource
import kotlin.test.ExperimentalKotlinTestApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotlinTestApi::class)
@ParameterizedClass(name = "Starting {0}")
@CsvFileSource(useHeadersInDisplayName = true, resources = ["/aocutils/directions.csv"])
class DirectionTest(private val input: Direction, private val clockwise: Direction, private val counterClockwise: Direction) {
	@Test
	fun `Turn clockwise`() {
		assertEquals(clockwise, input.turnClockwise()) { "Expected direction $clockwise after turning right from $input" }
	}

	@Test
	fun `Turn counterclockwise`() {
		assertEquals(counterClockwise, input.turnCounterClockwise()) { "Expected direction $counterClockwise after turning left from $input" }
	}
}
