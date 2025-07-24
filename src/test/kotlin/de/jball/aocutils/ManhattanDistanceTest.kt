package de.jball.aocutils

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.converter.TypedArgumentConverter
import org.junit.jupiter.params.provider.CsvFileSource
import kotlin.test.assertEquals

class ManhattanDistanceTest {
	@ParameterizedTest(name = "Test distance between {0} and {1} to be equal to {2}")
	@CsvFileSource(resources = ["/manhattan_distance.csv"])

	fun manhattanDistance(@ConvertWith(PairParser::class) a: Pair<Int, Int>, @ConvertWith(PairParser::class) b: Pair<Int, Int>, expected: Int) {
		val distance = manhattanDistance(a, b)
		assertEquals(expected, distance, "Distance between $a and $b to be equal to $expected.")
	}

}

class PairParser() : TypedArgumentConverter<String, Pair<Int, Int>>(String::class.java,
	Pair::class.java as Class<Pair<Int, Int>>
) {

	override fun convert(source: String): Pair<Int, Int> {
		return Regex("\\(([^,]+),([^)]+)\\)").matchEntire(source)!!.let {
			Pair(it.groupValues[1].toInt(), it.groupValues[2].toInt())
		}
	}
}
