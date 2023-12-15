package de.jball.aoc2023.day07

import de.jball.AdventOfCodeDay

val partTwoCardOrder = "J23456789TQKA"

class Day07(test: Boolean = false) : AdventOfCodeDay<Int>(test, 6440, 5905) {
    private val games = input
        .map { line ->
            val split = line.split(" ")
            Pair(parseHand(split[0]), split[1].toInt())
        }
        .groupingBy { (hand, _) -> hand }
        .fold(listOf<Int>()) { betsForHand, (_, nextBet) ->
            betsForHand + listOf(nextBet)
        }

    private fun parseHand(handString: String): Hand {
        val cards = handString.chunked(1)
            .map { label -> Card.entries.find { card: Card -> card.label == label }!! }
        return Hand(cards)
    }

    override fun part1(): Int {
        val gamesInOrderOfHandRank = games.entries.sortedWith(
            compareBy({ oneHand, otherHand -> partOneCompareHands(oneHand, otherHand) }) { (hand, _) -> hand })
        return gamesInOrderOfHandRank
            .foldIndexed(0) { indexOfHand, sum, betsForHand ->
                betsForHand.value.sumOf { bet -> (indexOfHand + 1) * bet } + sum
            }
    }


    private fun partOneCompareHands(one: Hand, other: Hand): Int {
        return if (one.cardCountsPartOne[0].value.compareTo(other.cardCountsPartOne[0].value) != 0)
            one.cardCountsPartOne[0].value.compareTo(other.cardCountsPartOne[0].value)
        else if (one.cardCountsPartOne.size > 1 && one.cardCountsPartOne[1].value.compareTo(other.cardCountsPartOne[1].value) != 0) {
            one.cardCountsPartOne[1].value.compareTo(other.cardCountsPartOne[1].value)
        } else if (partOneCompareCardAt(one, other, 0) != 0) {
            partOneCompareCardAt(one, other, 0)
        } else if (partOneCompareCardAt(one, other, 1) != 0) {
            partOneCompareCardAt(one, other, 1)
        } else if (partOneCompareCardAt(one, other, 2) != 0) {
            partOneCompareCardAt(one, other, 2)
        } else if (partOneCompareCardAt(one, other, 3) != 0) {
            partOneCompareCardAt(one, other, 3)
        } else if (partOneCompareCardAt(one, other, 4) != 0) {
            partOneCompareCardAt(one, other, 4)
        } else {
            0
        }
    }

    private fun partTwoCompareHands(one: Hand, other: Hand): Int {
        return if (one.cardCountsPartTwo[0].value.compareTo(other.cardCountsPartTwo[0].value) != 0)
            one.cardCountsPartTwo[0].value.compareTo(other.cardCountsPartTwo[0].value)
        else if (one.cardCountsPartTwo.size > 1 && one.cardCountsPartTwo[1].value.compareTo(other.cardCountsPartTwo[1].value) != 0) {
            one.cardCountsPartTwo[1].value.compareTo(other.cardCountsPartTwo[1].value)
        } else if (partTwoCompareCardAt(one, other, 0) != 0) {
            partTwoCompareCardAt(one, other, 0)
        } else if (partTwoCompareCardAt(one, other, 1) != 0) {
            partTwoCompareCardAt(one, other, 1)
        } else if (partTwoCompareCardAt(one, other, 2) != 0) {
            partTwoCompareCardAt(one, other, 2)
        } else if (partTwoCompareCardAt(one, other, 3) != 0) {
            partTwoCompareCardAt(one, other, 3)
        } else if (partTwoCompareCardAt(one, other, 4) != 0) {
            partTwoCompareCardAt(one, other, 4)
        } else {
            0
        }
    }


    private fun partOneCompareCardAt(one: Hand, other: Hand, index: Int) = one.cards[index].compareTo(other.cards[index])
    private fun partTwoCompareCardAt(one: Hand, other: Hand, index: Int) = partTwoCardOrder.indexOf(one.cards[index].label)
        .compareTo(partTwoCardOrder.indexOf(other.cards[index].label))

    override fun part2(): Int {
        val gamesInOrderOfHandRank = games.entries.sortedWith(
            compareBy({ oneHand, otherHand -> partTwoCompareHands(oneHand, otherHand) }) { (hand, _) -> hand })
        return gamesInOrderOfHandRank
            .foldIndexed(0) { indexOfHand, sum, betsForHand ->
                betsForHand.value.sumOf { bet -> (indexOfHand + 1) * bet } + sum
            }
    }
}

enum class Card(val label: String) {
    Two("2"),
    Three("3"),
    Four("4"),
    Five("5"),
    Six("6"),
    Seven("7"),
    Eight("8"),
    Nine("9"),
    Ten("T"),
    Jack("J"),
    Queen("Q"),
    King("K"),
    Ace("A")
}

class Hand(val cards: List<Card>) {
    val cardCountsPartOne = cards.groupingBy { it }.eachCount().entries
        .sortedWith(compareByDescending<Map.Entry<Card, Int>> { (_, count) -> count }
            .thenByDescending { (card, _) -> card.ordinal })

    val cardCountsPartTwo = groupCardsForPartTwo()

    private fun groupCardsForPartTwo(): List<Map.Entry<Card, Int>> {
        val groupedCards = cards.groupingBy { it }.eachCount().toMutableMap()
        val sortedGroupedCards = groupedCards.entries
            .sortedWith(compareByDescending<Map.Entry<Card, Int>> { (_, count) -> count }
                .thenByDescending { (card, _) -> partTwoCardOrder.indexOf(card.label) })

        val jackCount = sortedGroupedCards.find { (card, _) -> card == Card.Jack }
        if (jackCount != null && sortedGroupedCards.size > 1) {
            val bestCardNotJack = sortedGroupedCards.first { (card, _) -> card != Card.Jack }.key
            groupedCards[bestCardNotJack] = jackCount.value + groupedCards[bestCardNotJack]!!
            groupedCards.remove(jackCount.key)
        }

        return groupedCards.entries
            .sortedWith(compareByDescending<Map.Entry<Card, Int>> { (_, count) -> count }
                .thenByDescending { (card, _) -> partTwoCardOrder.indexOf(card.label) })
    }

    override fun toString(): String {
        return cards.joinToString("") { card -> card.label }
    }
}

fun main() {
    Day07().run()
}
