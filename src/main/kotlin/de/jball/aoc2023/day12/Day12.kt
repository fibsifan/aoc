package de.jball.aoc2023.day12

import de.jball.AdventOfCodeDay

class Day12(test: Boolean = false) : AdventOfCodeDay<Long>(test, 21, 525152) {
    private val cache: MutableMap<Pair<String, List<Int>>, Long> = mutableMapOf()

    private val springReport = input.map { line ->
        val splitLine = line.split(" ")
        val groupSizes = splitLine[1].split(",").map { groupSizeString -> groupSizeString.toInt() }
        Pair(splitLine[0], groupSizes)
    }

    fun possibleArrangements(damageReport: String, groupSizes: List<Int>): Long {

        // in combination with normalizing this prevents a lot of recursive calls.
        if (cache.containsKey(Pair(damageReport, groupSizes))) {
            return cache[Pair(damageReport, groupSizes)]!!
        }

        if (damageReport.isEmpty()) {
            cache[Pair(damageReport, groupSizes)] = 0
            return 0
        }

        if (groupSizes.isEmpty()) {
            return if (damageReport.contains('#')) {
                // at least one group should have remained. No valid combination can be constructed with the remaining string.
                cache[Pair(damageReport, groupSizes)] = 0
                0
            } else {
                // there is exactly one way to not have any remaining broken spring, regardless of the number of ?'s.
                if (!cache.containsKey(Pair(damageReport, groupSizes))) {
                    cache[Pair(damageReport, groupSizes)] = 1
                }
                1
            }
        }

        if (damageReport.startsWith(".")) {
            val shortened = possibleArrangements(damageReport.substring(1), groupSizes)
            cache[Pair(damageReport.substring(1), groupSizes)] = shortened
            return shortened
        }

        if (groupSizes.size == 1 && Regex("^[#?]{${groupSizes.first()}}$").matches(damageReport)) {
            cache[Pair(damageReport, groupSizes)] = 1
            return 1
        }

        if (damageReport.startsWith("#")) {
            return if (Regex("^[#?]{${groupSizes.first()}}[^#]").containsMatchIn(damageReport)) {
                if (damageReport.length < groupSizes.first()+1) {
                    cache[Pair(damageReport, groupSizes)] = 0
                    0
                } else if (damageReport.length == groupSizes.first()+1 && groupSizes.size==1) {
                    // not sure why I need this case but works like this.
                    cache[Pair(damageReport, groupSizes)] = 1
                    1
                } else {
                    val remainingCombinationsAfterFirstGroupPlaced =
                        possibleArrangements(damageReport.substring(groupSizes.first() + 1), groupSizes.drop(1))
                    cache[Pair(damageReport.substring(groupSizes.first() + 1), groupSizes.drop(1))] = remainingCombinationsAfterFirstGroupPlaced
                    remainingCombinationsAfterFirstGroupPlaced
                }
            } else {
                cache[Pair(damageReport, groupSizes)] = 0
                0
            }
        }

        if (damageReport.startsWith("?")) {
            val combinationsIfFirstSpringWorking = possibleArrangements(damageReport.substring(1), groupSizes)
            cache[Pair(damageReport.substring(1), groupSizes)] = combinationsIfFirstSpringWorking

            val combinationsIfFirstSpringBroken = possibleArrangements(damageReport.replaceFirstChar { '#' }, groupSizes)
            cache[Pair(damageReport.replaceFirstChar { '#' }, groupSizes)] = combinationsIfFirstSpringBroken

            return combinationsIfFirstSpringWorking + combinationsIfFirstSpringBroken
        }

        throw Error("should not be reached.")
    }

    override fun part1(): Long {
        return springReport.sumOf { possibleArrangements(normalizeReport(it.first), it.second) }
    }

    override fun part2(): Long {
        return springReport.map { unfold(it) }.sumOf { possibleArrangements(normalizeReport(it.first), it.second) }
    }

    /**
     * remove dots at start and end and reduce multiple dots to single.
     */
    private fun normalizeReport(damageReport: String) = damageReport.replace(Regex("\\.+"), ".")
        .replace(Regex("^\\."), "")
        .replace(Regex("\\.$"), "")

    private fun unfold(damageReport: String) = List(5) { damageReport }.joinToString("?")
    private fun unfold(groupSizes: List<Int>) = List(5) { groupSizes }.flatten()
    private fun unfold(annotatedReport: Pair<String, List<Int>>) = Pair(unfold(annotatedReport.first), unfold(annotatedReport.second))
}

fun main() {
    Day12().run()
}
