import java.io.File

class Day1 {
    fun run() {
        val result = File("..\\..\\data\\day1.txt").readLines().map { it.toInt() }

        var numIncreases = 0
        for (i in 1 until result.size) {
            if (result[i] > result[i-1]) {
                numIncreases += 1
            }
        }

        println("Part 1 number of depth increases: $numIncreases")

        numIncreases = 0
        for (i in 3 until result.size) {
            val prevWindowSum = result[i-1] + result[i-2] + result[i-3]
            val windowSum = result[i] + result[i-1] + result[i-2]
            if (windowSum > prevWindowSum) {
                numIncreases += 1
            }
        }

        println("Part 2 number of depth increases: $numIncreases")
    }
}