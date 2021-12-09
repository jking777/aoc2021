import java.io.File

class Day9 {
    fun run() {
        parseInput(0)

        val lowPointLevels: MutableList<Int> = ArrayList()
        val lowPointCoords: MutableList<Pair<Int, Int>> = ArrayList()

        for (row in 0 until heightMap.size) {
            for (col in 0 until heightMap[row].size) {
                val adjacents = getAdjacents(row, col)
                val isLow = adjacents.all { it > heightMap[row][col] }
                if (isLow) {
                    lowPointLevels.add(heightMap[row][col])
                    lowPointCoords.add(Pair(row, col))
                }
            }
        }

        val riskTotal = lowPointLevels.sumOf { it + 1 }

        println("Day 9 Part 1    Risk level sum:  $riskTotal")

        val basinSizes = lowPointCoords.map { calcBasinSize(it) }

        val basinSizeProduct = basinSizes.sortedDescending().subList(0, 3).reduce { acc, i -> acc * i }

        println("Day 9 Part 2    Basin size product: $basinSizeProduct")
    }

    private fun calcBasinSize(coord: Pair<Int, Int>): Int {
        val visited: MutableSet<Pair<Int, Int>> = HashSet()
        visited.add(coord)
        getUnvisitedAdjacentsNotNine(visited, coord)
        return visited.size
    }

    private fun getUnvisitedAdjacentsNotNine(visited: MutableSet<Pair<Int, Int>>, start: Pair<Int, Int>) {
        val adjacentCoords: MutableList<Pair<Int, Int>> = ArrayList()
        val row = start.first
        val col = start.second

        if (row > 0) {
            adjacentCoords.add(Pair(row - 1, col))
        }
        if (row < heightMap.size - 1) {
            adjacentCoords.add(Pair(row + 1, col))
        }
        if (col > 0) {
            adjacentCoords.add(Pair(row, col - 1))
        }
        if (col < heightMap[row].size - 1) {
            adjacentCoords.add(Pair(row, col + 1))
        }

        for (ac in adjacentCoords) {
            if (heightMap[ac.first][ac.second] != 9 && !visited.contains(ac)) {
                visited.add(ac)
                getUnvisitedAdjacentsNotNine(visited, ac)
            }
        }
    }

    private fun getAdjacents(row: Int, col: Int): List<Int> {
        val adjacents: MutableList<Int> = ArrayList()

        if (row > 0) {
            adjacents.add(heightMap[row - 1][col])
        }
        if (row < heightMap.size - 1) {
            adjacents.add(heightMap[row + 1][col])
        }
        if (col > 0) {
            adjacents.add(heightMap[row][col - 1])
        }
        if (col < heightMap[row].size - 1) {
            adjacents.add(heightMap[row][col + 1])
        }

        return adjacents
    }

    private fun parseInput(sample: Int) {
        val filename =
            if (sample <= 0) "..\\..\\data\\day9.txt" else "..\\..\\data\\day9_sample.txt"

        heightMap = File(filename).readLines().map { it.map { c -> c.toChar().digitToInt() }.toIntArray() }
    }

    private var heightMap: List<IntArray> = ArrayList()
}