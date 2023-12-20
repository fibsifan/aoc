package de.jball

import kotlin.math.abs

fun manhattanDistance(a: Pair<Int, Int>, b: Pair<Int, Int>): Int =
    abs(a.first - b.first) + abs(a.second - b.second)
