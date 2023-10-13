package de.jball.aoc2021.day07

import de.jball.AdventOfCodeDay
import kotlin.math.abs
import kotlin.math.roundToLong

class Day07(test: Boolean = false): AdventOfCodeDay<Long>(test, 37, 168) {
    private val positions = input[0].split(",").map(String::toLong).sorted()

    override fun part1(): Long {
        val m = positions[positions.size/2]
        return positions.sumOf { abs(it - m) }
    }

    override fun part2(): Long {
        // d_t = ((x_t - m)^2 + |x_t - m|)/2, minimize d_1 + ... + d_t + ... ´d_n under m.
        // (m-x)^2 = m^2 - 2mx + x^2 + |x - m|, x known

        // minimize (am^2 - 2bm + c + sum(|x_t - m|))/2
        // derived: (2am - 2b) / 2 + d/dm sum(|x_t - m|)/2
        // 0 = am - b + d/dm sum(|x_t - m|)/2
        // a ==  positions.size, b = positions.sum()

        // b/a - d/dm sum(|x_t - m|)/2a = m
        // d/dm sum(|x_t-m|)/2a < 0.5
        val m = (positions.sum().toDouble() / positions.size.toDouble()).roundToLong() // - 1, in case of the <0.5 part changing the rounding result...
        return positions.sumOf { ((it - m) * (it - m) + abs(it - m)) / 2 }
    }
}

fun main() {
    Day07().run()
}
