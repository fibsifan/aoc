package de.jball.aoc2023.day07

import de.jball.AdventOfCodeDay

class Day07(test: Boolean = false): AdventOfCodeDay<Int>(test, 6440, 0) {
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
        val gamesInOrderOfHandRank = games.entries.sortedBy { (hand, _) -> hand }
        return gamesInOrderOfHandRank
            .foldIndexed(0) { indexOfHand, sum, betsForHand   ->
                betsForHand.value.sumOf { bet -> (indexOfHand+1) * bet } + sum
            }
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
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

data class Hand(val cards: List<Card>) : Comparable<Hand> {
    private val cardCounts = cards.groupingBy { it }.eachCount().entries
        .sortedWith(compareByDescending<Map.Entry<Card, Int>> { (_, count) -> count }
            .thenByDescending { (card, _) -> card.ordinal })

    override fun compareTo(other: Hand): Int {
        return if (cardCounts[0].value.compareTo(other.cardCounts[0].value) != 0)
            cardCounts[0].value.compareTo(other.cardCounts[0].value)
        else if (cardCounts.size > 1 && cardCounts[1].value.compareTo(other.cardCounts[1].value) != 0) {
            cardCounts[1].value.compareTo(other.cardCounts[1].value)
        } else if (compareCardAt(other, 0) != 0) {
            compareCardAt(other, 0)
        } else if (compareCardAt(other, 1) != 0) {
            compareCardAt(other, 1)
        } else if (compareCardAt(other, 2) != 0) {
            compareCardAt(other, 2)
        } else if (compareCardAt(other, 3) != 0) {
            compareCardAt(other, 3)
        } else if (compareCardAt(other, 4) != 0) {
            compareCardAt(other, 4)
        } else {
            0
        }
    }

    private fun compareCardAt(other: Hand, index: Int) = cards[index].compareTo(other.cards[index])

    override fun equals(other: Any?): Boolean {
        return other is Hand && compareTo(other) == 0
    }

    override fun toString(): String {
        return cards.joinToString("") { card -> card.label}
    }

    override fun hashCode(): Int {
        return cardCounts.hashCode()
    }
}

fun main() {
    Day07().run()
}
