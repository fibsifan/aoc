package de.jball

import java.io.File

abstract class AdventOfCodeDay<T>(
    year: String,
    val test: Boolean = false,
    val expected1: T,
    val expected2: T) {
    val input: List<String> = File("src/${if(test) "test" else "main"}/resources/${year}",
        "${this::class.simpleName}${if (test) "_test" else ""}.txt")
        .readLines()


    abstract fun part1(): T
    abstract fun part2(): T
    fun run() {
        val part1 = part1()
        println(part1)
        if (test) {
            check(expected1 == part1)
        }

        val part2 = part2()
        println(part2)
        if (test) {
            check(expected2 == part2)
        }
    }
}