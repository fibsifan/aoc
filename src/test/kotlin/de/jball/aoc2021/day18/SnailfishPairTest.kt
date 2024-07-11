package de.jball.aoc2021.day18

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class SnailfishPairTest {
    @Test
    fun plusWithoutReduce() {
        val sn1 = SnailfishPair.fromString("[1,2]")
        val sn2 = SnailfishPair.fromString("[3,4]")

        val result = sn1 + sn2

        assertFalse { result.needsExplode() }
        assertFalse { result.needsSplit() }
        assertEquals("[[1,2],[3,4]]", result.toString())
    }

    @Test
    fun testNeedsExplode1() {
        val snailfishPair = SnailfishPair.fromString("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")

        assertTrue { snailfishPair.needsExplode() }
    }

    @Test
    fun testNeedsExplode2() {
        val snailfishPair = SnailfishPair.fromString("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]")

        assertTrue { snailfishPair.needsExplode() }
    }

    @Test
    fun testNeedsExplode3() {
        val snailfishPair = SnailfishPair.fromString("[[[[0,7],4],[15,[0,13]]],[1,1]]")

        assertFalse { snailfishPair.needsExplode() }
    }

    @Test
    fun testNeedsSplit1() {
        val snailfishPair = SnailfishPair.fromString("[[[[0,7],4],[15,[0,13]]],[1,1]]")

        assertTrue { snailfishPair.needsSplit() }
    }

    @Test
    fun testNeedsSplit2() {
        val snailfishPair = SnailfishPair.fromString("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")

        assertFalse { snailfishPair.needsSplit() }
    }

    @Test
    fun testExplode1() {
        val input = SnailfishPair.fromString("[[[[[9,8],1],2],3],4]")
        val expected = SnailfishPair.fromString("[[[[0,9],2],3],4]")

        assertEquals(expected, input.explode())
    }

    @Test
    fun testExplode2() {
        val input = SnailfishPair.fromString("[7,[6,[5,[4,[3,2]]]]]")
        val expected = SnailfishPair.fromString("[7,[6,[5,[7,0]]]]")

        assertEquals(expected, input.explode())
    }

    @Test
    fun testExplode3() {
        val input = SnailfishPair.fromString("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
        val expected = SnailfishPair.fromString("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")

        assertEquals(expected, input.explode())
    }
}
