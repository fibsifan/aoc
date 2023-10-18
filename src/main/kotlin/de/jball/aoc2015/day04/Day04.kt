package de.jball.aoc2015.day04

import de.jball.AdventOfCodeDay
import java.math.BigInteger
import java.security.MessageDigest

class Day04(test: Boolean = false): AdventOfCodeDay<Int>(test, 609043, 6742839) {
    private val prefix = input[0]
    override fun part1(): Int {
        return generateSequence(1) { it+1 }
            .find { (prefix + it.toString()).md5().startsWith("00000") }!!
    }

    override fun part2(): Int {
        return generateSequence(1) { it+1 }
            .find { (prefix + it.toString()).md5().startsWith("000000") }!!
    }

    private fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')
}

fun main() {
    Day04().run()
}
