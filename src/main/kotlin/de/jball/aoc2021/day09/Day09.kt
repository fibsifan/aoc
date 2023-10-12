package de.jball.aoc2021.day09

import de.jball.aoc2021.Day

class Day09(test: Boolean = false): Day(test, 15, 1134) {
    private val numbers = input.map {it.chunked(1).map{it.toInt()}}
    override fun part1(): Long {
        val candidates = numbers.map{ line ->
            listOf(line[0]<line[1]) + line.windowed(3, 1, false) { window ->
                window.size>1 && window[window.size-2] == window.minOrNull()!!
            } + listOf(line[line.size-2]>line[line.size-1])
        }

        val minima = numbers.mapIndexed{ lineNo,line ->
            line.filterIndexed { column, _ ->
                candidates[lineNo][column] && isMinInColumn(numbers, lineNo, column)
            }
        }

        println(minima)
        return minima.sumOf { it.sum() }.toLong() + minima.sumOf { it.size }
    }

    private fun isMinInColumn(numbers: List<List<Int>>, lineNo: Int, column: Int): Boolean {
        return when (lineNo) {
            0 -> {
                numbers[lineNo][column] < numbers[1][column]
            }
            numbers.size-1 -> {
                numbers[lineNo][column] < numbers[lineNo-1][column]
            }
            else -> {
                numbers[lineNo][column] < numbers[lineNo-1][column] &&
                        numbers[lineNo][column] < numbers[lineNo+1][column]
            }
        }
    }

    override fun part2(): Long {
        val basinByPoint = mutableMapOf<Pair<Int, Int>, Int>()
        val pointsByBasin = mutableMapOf<Int, MutableSet<Pair<Int, Int>>>()
        numbers.forEachIndexed { lineNo, line ->
            line.forEachIndexed{ column, position ->
                if (position != 9) {
                    val coordinates = Pair(lineNo, column)
                    val neighborsInMap = findNeighborsInMap(coordinates, basinByPoint)
                    if (neighborsInMap.isEmpty()) {
                        val basin = (basinByPoint.values.maxOrNull() ?: 0) + 1
                        pointsByBasin[basin] = mutableSetOf(coordinates)
                        basinByPoint[coordinates] = basin
                    } else if (neighborsInMap.toSet().size == 1) {
                        val basin = basinByPoint[neighborsInMap.first()]!!
                        basinByPoint[coordinates] = basin
                        pointsByBasin[basin]!!.add(coordinates)
                    } else {
                        // join basins
                        val first = neighborsInMap.first()
                        val newBasin = basinByPoint[first]!!
                        pointsByBasin[newBasin]!!.add(coordinates)
                        basinByPoint[coordinates] = newBasin
                        neighborsInMap.filter {it != first}
                            .forEach {representative ->
                                val oldBasin = basinByPoint[representative]!!
                                pointsByBasin[oldBasin]!!.forEach {
                                    pointsByBasin[newBasin]!!.add(it)
                                    basinByPoint[it] = newBasin
                                }
                                pointsByBasin.remove(oldBasin)
                            }
                    }
                }
            }
        }
        return pointsByBasin.map {(_, points) ->
            points.size
        }.sortedDescending().subList(0,3).reduce {a, b-> a*b}.toLong()
    }

    /**
     * Only coordinates with differing basins.
     */
    private fun findNeighborsInMap(pair: Pair<Int, Int>, basinByPoint: Map<Pair<Int, Int>, Int>): Set<Pair<Int, Int>> {
        return listOf(Pair(pair.first+1, pair.second),
            Pair(pair.first-1, pair.second),
            Pair(pair.first, pair.second-1),
            Pair(pair.first, pair.second+1))
            .asSequence()
            .map { Pair(it, basinByPoint[it])}
            .filter { it.second != null }
            .distinctBy { it.second }
            .map {it.first}
            .toSet()
    }
}

fun main() {
    Day09().run()
}