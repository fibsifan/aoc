package de.jball.aoc2021.day18

import kotlin.test.Test
import kotlin.test.assertEquals

class SailfishIntTest {
    @Test
    fun testSplitEven() {
        val input = SailfishInt(10)
        val result = input.split()
        assertEquals(SailfishPair(SailfishInt(5), SailfishInt(5)), result)
    }
    @Test
    fun testSplitUneven() {
        val input = SailfishInt(11)
        val result = input.split()
        assertEquals(SailfishPair(SailfishInt(5), SailfishInt(6)), result)
    }
}
