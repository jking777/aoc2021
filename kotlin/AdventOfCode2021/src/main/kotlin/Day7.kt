import java.io.File

class Day7 {
    fun run() {
//        parseInput("..\\..\\data\\day7_sample.txt")
        parseInput("..\\..\\data\\day7.txt")

        var fuel = Long.MAX_VALUE
        var fuelPos = -1

        val minPos = positions.minOrNull()!!.toInt()
        val maxPos = positions.maxOrNull()!!.toInt()
        for (position in minPos..maxPos) {
            val f = calculatePart1Fuel(position)
            if (f < fuel) {
                fuel = f
                fuelPos = position
            }
        }

        println("Day 7 Part 1  Position $fuelPos  Fuel $fuel")

        fuel = Long.MAX_VALUE
        fuelPos = -1

        for (position in minPos..maxPos) {
            val f = calculatePart2Fuel(position)
            if (f < fuel) {
                fuel = f
                fuelPos = position
            }
        }

        println("Day 7 Part 2  Position $fuelPos  Fuel $fuel")
    }

    private fun calculatePart1Fuel(finalPos: Int): Long {
        return positions.sumOf { kotlin.math.abs(it - finalPos).toLong() }
    }

    private fun calculatePart2Fuel(finalPos: Int): Long {
        return positions.sumOf { (kotlin.math.abs(it - finalPos) * (kotlin.math.abs(it - finalPos) + 1) / 2).toLong() }
    }

    private fun parseInput(filename: String) {
        val lines = File(filename).readLines()
        positions = lines[0].split(',').map { it.toInt() }
    }

    private var positions: List<Int> = ArrayList()
}