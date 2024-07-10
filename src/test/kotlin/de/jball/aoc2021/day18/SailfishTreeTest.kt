package de.jball.aoc2021.day18

import kotlin.test.Test
import kotlin.test.assertEquals


class SailfishTreeTest {
    @Test
    fun testExplode1() {
        val input = SailfishTree.fromString("[[[[[9,8],1],2],3],4]")
        val expected = SailfishTree.fromString("[[[[0,9],2],3],4]")

        input.explode()

        assertEquals(expected, input)
    }

    @Test
    fun testExplode2() {
        val input = SailfishTree.fromString("[7,[6,[5,[4,[3,2]]]]]")
        val expected = SailfishTree.fromString("[7,[6,[5,[7,0]]]]")

        input.explode()

        assertEquals(expected, input)
    }

    @Test
    fun testExplode3() {
        val input = SailfishTree.fromString("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
        val expected = SailfishTree.fromString("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")

        input.explode()

        assertEquals(expected, input)
    }
}
