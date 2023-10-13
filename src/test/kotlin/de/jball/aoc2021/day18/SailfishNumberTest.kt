package de.jball.aoc2021.day18

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Ignore

class SailfishNumberTest {
    @Test
    fun plusWithoutReduce() {
        val sn1 = SailfishNumber.fromString("[1,2]")
        val sn2 = SailfishNumber.fromString("[3,4]")

        val result = sn1 + sn2

        assertEquals("[[1,2],[3,4]]", result.toString())
    }

    @Test
    @Ignore
    fun plusWithReduce() {
        val sn1 = SailfishNumber.fromString("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]")
        val sn2 = SailfishNumber.fromString("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]")

        val result = sn1 + sn2

        assertEquals("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]", result.toString())
    }
}
