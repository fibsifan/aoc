package de.jball.aoc2023.day06

import de.jball.AdventOfCodeDay
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt


/**
 * record < initTime * (duration - initTime)
 * 0 < -initTime^2 + initTime*duration - record
 * 0 > initTime^2 - initTime*duration + record
 * initTime_(1,2) = duration/2 +/- sqrt( duration^2 / 4 - record )
 */

class Day06(test:Boolean = false): AdventOfCodeDay<Long>(test, 288, 71503) {
    private val durations = input[0].split(Regex("\\s+")).drop(1).map { it.toLong() }
    private val records = input[1].split(Regex("\\s+")).drop(1).map { it.toLong() }
    private val races = durations.zip(records) { duration, record -> Race(duration, record)}

    override fun part1(): Long {
        return races.map {
            upperBound(it) - lowerBound(it) + 1
        }.reduce {
            a, b -> a*b
        }
    }

    private fun upperBound(race: Race): Long {
        val duration = race.duration.toDouble()
        val record = race.record.toDouble()
        val candidate = floor(duration / 2.0 + sqrt(duration * duration / 4.0 - record)).toLong()

        return if (-(candidate*candidate) + candidate*race.duration == race.record) {
            candidate - 1
        } else {
            candidate
        }
    }

    private fun lowerBound(race: Race): Long {
        val duration = race.duration.toDouble()
        val record = race.record.toDouble()
        val candidate = ceil(duration / 2.0 - sqrt(duration * duration / 4.0 - record)).toLong()

        return if (-(candidate*candidate) + candidate*race.duration == race.record) {
            candidate + 1
        } else {
            candidate
        }
    }

    override fun part2(): Long {
        val duration = durations.joinToString("") { it.toString() }.toLong()
        val record = records.joinToString("") { it.toString() }.toLong()
        val race = Race(duration, record)

        return upperBound(race) - lowerBound(race) + 1
    }
}

data class Race(val duration: Long, val record: Long)

fun main() {
    Day06().run()
}
