package de.jball.aoc2023.day15

import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Test {
    private val unitUnderTest = Day15(true)
    @Test
    fun rn1() {
        assertEquals(30, unitUnderTest.hash("rn=1"))
    }

    @Test
    fun cm() {
        assertEquals(253, unitUnderTest.hash("cm-"))
    }
}
