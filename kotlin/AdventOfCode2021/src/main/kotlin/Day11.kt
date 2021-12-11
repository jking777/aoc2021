import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

class Day11 {
    fun run() {
        parseInput(0)

        val numItems = energy.sumOf { it.size }

        var allFlash: Int? = null
        var totalFlashes = 0
        val part1StepMax = 100

        for (step in 1..part1StepMax) {
            val numFlashes = doOneStep()

            totalFlashes += numFlashes

            if (numFlashes == numItems && allFlash == null) {
                allFlash = step
            }
        }

        println("Day 11 Part 1    Total flashes:  $totalFlashes")

        var step = part1StepMax + 1
        while (allFlash == null) {
            val numFlashes = doOneStep()
            if (numFlashes == numItems) {
                allFlash = step
            }
            step += 1
        }

        println("Day 11 Part 2    All flashed at step $allFlash")
    }

    private fun doOneStep(): Int {
        // increase everything by 1
        for (row in energy.indices) {
            for (col in 0 until energy[row].size) {
                energy[row][col] += 1
            }
        }

        var hasFlashed = energy.map { BooleanArray(it.size) }

        // handle flashes
        for (row in energy.indices) {
            for (col in 0 until energy[row].size) {
                if (energy[row][col] > 9 && !hasFlashed[row][col]) {
                    hasFlashed[row][col] = true
                    doNeighborFlash(hasFlashed, row, col)
                }
            }
        }

        // count flashes and reset items that flashed
        var numFlashes = 0
        for (row in energy.indices) {
            for (col in 0 until energy[row].size) {
                if (hasFlashed[row][col]) {
                    energy[row][col] = 0
                    numFlashes += 1
                }
            }
        }

        return numFlashes
    }

    private fun printEnergy() {
        for (row in energy) {
            for (value in row) {
                print("$value  ")
            }
            println()
        }
        println()
    }

    private fun doNeighborFlash(hasFlashed: List<BooleanArray>, row: Int, col: Int) {
        val neighbors = getNeighborCoords(row, col)
        for (coord in neighbors) {
            val r = coord.first
            val c = coord.second
            energy[r][c] += 1
            if (energy[r][c] > 9 && !hasFlashed[r][c]) {
                hasFlashed[r][c] = true
                doNeighborFlash(hasFlashed, r, c)
            }
        }
    }

    private fun getNeighborCoords(row: Int, col: Int): List<Pair<Int, Int>> {
        var coords: MutableList<Pair<Int, Int>> = ArrayList()
        for (r in max(0, row - 1) until min(energy.size, row + 2)) {
            for (c in max(0, col - 1) until min(energy[row].size, col + 2)) {
                if (r != row || c != col) {
                    coords.add(Pair(r, c))
                }
            }
        }
        return coords
    }

    private fun parseInput(sample: Int) {
        val filename =
            if (sample <= 0) "..\\..\\data\\day11.txt" else "..\\..\\data\\day11_sample.txt"

        energy = File(filename).readLines().map { it.map { ch -> ch.digitToInt() }.toMutableList() }
    }

    private var energy: List<MutableList<Int>> = ArrayList()

}