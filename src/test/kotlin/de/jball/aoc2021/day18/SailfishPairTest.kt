package de.jball.aoc2021.day18

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class SailfishPairTest {
    @Test
    fun plusWithoutReduce() {
        val sn1 = SailfishPair.fromString("[1,2]")
        val sn2 = SailfishPair.fromString("[3,4]")

        val result = sn1 + sn2

        assertEquals("[[1,2],[3,4]]", result.toString())
    }

    @Test
    @Ignore
    fun plusWithReduce() {
        val sn1 = SailfishPair.fromString("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]")
        val sn2 = SailfishPair.fromString("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]")

        val result = sn1 + sn2

        assertEquals("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]", result.toString())
    }

    @Test
    fun testNeedsExplode1() {
        val sailfishPair = SailfishPair.fromString("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")

        assertTrue { sailfishPair.needsExplode() }
    }

    @Test
    fun testNeedsExplode2() {
        val sailfishPair = SailfishPair.fromString("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]")

        assertTrue { sailfishPair.needsExplode() }
    }

    @Test
    fun testNeedsExplode3() {
        val sailfishPair = SailfishPair.fromString("[[[[0,7],4],[15,[0,13]]],[1,1]]")

        assertFalse { sailfishPair.needsExplode() }
    }

    @Test
    fun testNeedsSplit1() {
        val sailfishPair = SailfishPair.fromString("[[[[0,7],4],[15,[0,13]]],[1,1]]")

        assertTrue { sailfishPair.needsSplit() }
    }

    @Test
    fun testNeedsSplit2() {
        val sailfishPair = SailfishPair.fromString("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")

        assertFalse { sailfishPair.needsSplit() }
    }
}
