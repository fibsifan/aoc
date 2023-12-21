package de.jball.aoc2023.day12

import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    private val unitUnderTest = Day12(true)

    @Test
    fun testPossibleArrangements() {
        assertEquals(1, unitUnderTest.possibleArrangements("???.###", listOf(1,1,3)))
        assertEquals(4, unitUnderTest.possibleArrangements(".??..??...?##.", listOf(1,1,3)))
        assertEquals(1, unitUnderTest.possibleArrangements("?#?#?#?#?#?#?#?", listOf(1,3,1,6)))
        assertEquals(1, unitUnderTest.possibleArrangements("????.#...#...", listOf(4,1,1)))
        assertEquals(4, unitUnderTest.possibleArrangements("????.######..#####.", listOf(1,6,5)))
        assertEquals(10, unitUnderTest.possibleArrangements("?###????????", listOf(3,2,1)))
    }
}
