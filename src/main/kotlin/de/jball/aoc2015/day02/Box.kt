package de.jball.aoc2015.day02

import java.lang.Integer.max

data class Box(val a: Int, val b: Int, val c: Int) {

    constructor(dimensions: List<Int>) : this(dimensions[0], dimensions[1], dimensions[2])

    fun paperNeeded(): Int {
        val areas = listOf(a*b, b*c, c*a)
        return areas.sum() * 2 + areas.min()
    }

    fun ribbonNeeded(): Int {
        val wrap = ((a+b+c) - max(a,max(b,c)))*2
        val bow = a*b*c
        return wrap + bow
    }
}
