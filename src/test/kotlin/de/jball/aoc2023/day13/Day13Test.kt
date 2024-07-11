package de.jball.aoc2023.day13

import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {
	private val unitUnderTest = Day13(true)

	private val verticalTestData = """
        #.##..##.
        ..#.##.#.
        ##......#
        ##......#
        ..#.##.#.
        ..##..##.
        #.#.##.#.
    """.trimIndent().split("\n")
	private val horizontalTestData = """
        #...##..#
        #....#..#
        ..##..###
        #####.##.
        #####.##.
        ..##..###
        #....#..#
    """.trimIndent().split("\n")

	@Test
	fun testVerticalMirror() {
		val pattern = unitUnderTest.parsePattern(verticalTestData)
		assertEquals(5, pattern.findVerticalMirror())
	}

	@Test
	fun testHorizontalMirror() {
		val pattern = unitUnderTest.parsePattern(horizontalTestData)
		assertEquals(4, pattern.findHorizontalMirror())
	}

	@Test
	fun testSmudgedHorizontalMirror1() {
		val pattern = unitUnderTest.parsePattern(verticalTestData)
		assertEquals(3, pattern.findSmudgedHorizontalMirror())
	}

	@Test
	fun testSmudgedHorizontalMirror2() {
		val pattern = unitUnderTest.parsePattern(horizontalTestData)
		assertEquals(1, pattern.findSmudgedHorizontalMirror())
	}
}
