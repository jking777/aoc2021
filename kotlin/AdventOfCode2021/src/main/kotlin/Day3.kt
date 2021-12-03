import java.io.File

class Day3 {
    fun run() {
        val sampleInput = File("..\\..\\data\\day3_sample.txt").readLines().map {
            s -> s.map { ch -> ch.digitToInt() }
        }
        val fullInput = File("..\\..\\data\\day3.txt").readLines().map {
                s -> s.map { ch -> ch.digitToInt() }
        }

        val input = fullInput

        val wordLength = input[0].size
        val halfLength = (input.size + 1) / 2

        var oneCounts = IntArray(wordLength)

        for (i in 0 until wordLength) {
            oneCounts[i] = input.sumOf { it[i] }
        }

        var mostCommon = IntArray(wordLength)
        var leastCommon = IntArray(wordLength)
        for (i in 0 until wordLength) {
            mostCommon[i] = if (oneCounts[i] >= halfLength) 1 else 0
            leastCommon[i] = if (oneCounts[i] >= halfLength) 0 else 1
        }

        val mostCommonValue = arrayToValue(mostCommon)
        val leastCommonValue = arrayToValue(leastCommon)

        println("Part 1    Most common:  $mostCommonValue    Least common:  $leastCommonValue    Power:  ${mostCommonValue * leastCommonValue}")

        part2(input)
    }

    private fun part2(input: List<List<Int>>) {
        val wordLength = input[0].size
        var oxygen = ArrayList<List<Int>>(input)
        var co2 = ArrayList<List<Int>>(input)

        for (position in 0 until wordLength) {
            if (oxygen.size > 1) {
                val oneCount = oxygen.sumOf { it[position] }
                val zeroCount = oxygen.size - oneCount
                val mostCommon = if (oneCount >= zeroCount) 1 else 0
                oxygen.removeIf { it[position] != mostCommon }
            }

            if (co2.size > 1) {
                val oneCount = co2.sumOf { it[position] }
                val zeroCount = co2.size - oneCount
                val leastCommon = if (zeroCount <= oneCount) 0 else 1
                co2.removeIf { it[position] != leastCommon }
            }
        }

        val oxygenRating = arrayToValue(oxygen[0].toIntArray())
        val co2Rating = arrayToValue(co2[0].toIntArray())

        println("Part 2    Oxygen rating:  $oxygenRating    CO2 rating:  $co2Rating    Life support:  ${oxygenRating * co2Rating}")
    }

    private fun arrayToValue(a: IntArray): Int {
        var value: Int = 0
        for (i in a) {
            value = (value shl 1) + i
        }
        return value
    }
}