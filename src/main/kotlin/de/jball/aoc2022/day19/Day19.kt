package de.jball.aoc2022.day19

import de.jball.AdventOfCodeDay

class Day19(test: Boolean = false): AdventOfCodeDay<Int>(test, 33, 0) {
    private val parserRegex = "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.".toRegex()
    private val blueprints: Map<Int, RobotBlueprint> = input.map { parserRegex.find(it)!! }
        .associateBy(keySelector = { getGroupValueAsInt(1, it.groups) },
            valueTransform = { RobotBlueprint(it.groups) })
    override fun part1(): Int {
        return blueprints.map { entry ->
            entry.key * determineMaxGeodes(entry.value)
        }.sum()
    }

    private fun determineMaxGeodes(blueprint: RobotBlueprint): Int {
        val startResources = MaterialCounts()
        var currentResources = listOf(startResources)
        val startRobots = RobotCounts()
        var currentRobots = listOf(startRobots)
        for (time in 1..24) {
            val t = currentResources.zip(currentRobots).map {
                calculatePossibleNext(it)
            }.flatten().distinct()
        }
        TODO("Not yet implemented")
    }

    private fun calculatePossibleNext(state: Pair<MaterialCounts, RobotCounts>): List<Pair<MaterialCounts, RobotCounts>> {
        TODO("Not yet implemented")
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }
}

fun getGroupValueAsInt(groupValue: Int, matchGroups: MatchGroupCollection) = matchGroups[groupValue]!!.value.toInt()

data class MaterialCounts(val ore: Int = 0,
                          val clay: Int = 0,
                          val obsidian: Int = 0,
                          val geodes: Int = 0)

data class RobotCounts(val oreRobots: Int = 1,
                       val clayRobots: Int = 0,
                       val obsidianRobots: Int = 0,
                       val geodeRobots: Int = 0)

class RobotBlueprint(private val oreBotRecipe: Int,
                     private val clayBotRecipe: Int,
                     private val obsidianBotRecipe: Pair<Int, Int>,
                     private val geodeBotRecipe: Pair<Int, Int>) {
    constructor(matchGroups: MatchGroupCollection) : this(getGroupValueAsInt(2, matchGroups),
        getGroupValueAsInt(3, matchGroups),
        Pair(getGroupValueAsInt(4, matchGroups), getGroupValueAsInt(5, matchGroups)),
        Pair(getGroupValueAsInt(6, matchGroups), getGroupValueAsInt(7, matchGroups)))

    fun buildOreBot(robots: RobotCounts, counts: MaterialCounts): Pair<RobotCounts, MaterialCounts> {
        val (oreBots, clayBots, obsidianBots, geodeBots) = robots
        val (ore, clay, obsidian, geodes) = counts
        return Pair(RobotCounts(oreBots+1, clayBots, obsidianBots, geodeBots),
            MaterialCounts(ore-oreBotRecipe, clay, obsidian, geodes))
    }

    fun buildClayBot(robots: RobotCounts, counts: MaterialCounts): Pair<RobotCounts, MaterialCounts> {
        val (oreBots, clayBots, obsidianBots, geodeBots) = robots
        val (ore, clay, obsidian, geodes) = counts
        return Pair(RobotCounts(oreBots, clayBots+1, obsidianBots, geodeBots),
            MaterialCounts(ore-clayBotRecipe, clay, obsidian, geodes))
    }

    fun buildObsidianBot(robots: RobotCounts, counts: MaterialCounts): Pair<RobotCounts, MaterialCounts> {
        val (oreBots, clayBots, obsidianBots, geodeBots) = robots
        val (ore, clay, obsidian, geodes) = counts
        return Pair(RobotCounts(oreBots, clayBots, obsidianBots+1, geodeBots),
            MaterialCounts(ore-obsidianBotRecipe.first, clay-obsidianBotRecipe.second, obsidian, geodes))
    }

    fun buildGeodeBot(robots: RobotCounts, counts: MaterialCounts): Pair<RobotCounts, MaterialCounts> {
        val (oreBots, clayBots, obsidianBots, geodeBots) = robots
        val (ore, clay, obsidian, geodes) = counts
        return Pair(RobotCounts(oreBots, clayBots, obsidianBots, geodeBots+1),
            MaterialCounts(ore-geodeBotRecipe.first, clay, obsidian-geodeBotRecipe.second, geodes))
    }
}
