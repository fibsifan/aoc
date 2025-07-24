package de.jball.aoc2023.day14

import kotlin.test.Test
import kotlin.test.assertEquals
import de.jball.aocutils.Direction
import kotlin.test.Ignore

class PlatformTest {
	private val testStart = Platform.read("""
			O....#....
			O.OO#....#
			.....##...
			OO.#O....O
			.O.....O#.
			O.#..O.#.#
			..O..#O..O
			.......O..
			#....###..
			#OO..#....
		""".trimIndent().split("\n"))

	@Test
	@Ignore
	fun testShiftNorth() {
		val expected = """
			OOOO.#.O..
			OO..#....#
			OO..O##..O
			O..#.OO...
			........#.
			..#....#.#
			..O..#.O.O
			..O.......
			#....###..
			#....#....
		""".trimIndent()

		assertEquals(expected, testStart.shift(Direction.NORTH).toString())
	}

	@Test
	fun testLoad() {
		val input = Platform.read("""
			OOOO.#.O..
			OO..#....#
			OO..O##..O
			O..#.OO...
			........#.
			..#....#.#
			..O..#.O.O
			..O.......
			#....###..
			#....#....
		""".trimIndent().split("\n"))

		assertEquals(136L, input.load())
	}

	@Test
	@Ignore
	fun test1Cycle() {
		val expected = Platform.read("""
			.....#....
			....#...O#
			...OO##...
			.OO#......
			.....OOO#.
			.O#...O#.#
			....O#....
			......OOOO
			#...O###..
			#..OO#....
		""".trimIndent().split("\n"))

		assertEquals(expected, testStart.cycle())
	}
}
