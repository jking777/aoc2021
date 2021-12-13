import java.io.File
import java.lang.Integer.min

class Day13 {
    fun run() {
        parseInput(0)

        val maxX = dots.maxOf { it.first }
        val maxY = dots.maxOf { it.second }

        val grid: MutableList<MutableList<Boolean>> = ArrayList(maxY + 1)
        for (i in 0..maxY) {
            val a = MutableList(maxX + 1) { false }
            grid.add(a)
        }

        // populate grid
        for (d in dots) {
            grid[d.second][d.first] = true
        }

        val fold = folds.first()
        var newGrid: List<List<Boolean>> = ArrayList()
        if (fold.first == 'x') {
            newGrid = doXFold(fold.second, grid)
        }
        else if (fold.first == 'y') {
            newGrid = doYFold(fold.second, grid)
        }
        else {
            println("Unknown fold axis:  ${fold.first}")
        }

        val foldedDots = newGrid.map { it.filter { b -> b }.size }.sum()

        println("Day 13 Part 1    Dots after first fold:  $foldedDots")

        for (fold in folds.drop(1)) {
            if (fold.first == 'x') {
                newGrid = doXFold(fold.second, newGrid)
            }
            else if (fold.first == 'y') {
                newGrid = doYFold(fold.second, newGrid)
            }
            else {
                println("Unknown fold axis:  ${fold.first}")
            }
        }

        println()
        println("Day 13 Part 2:")
        println()

        for (line in newGrid) {
            for (b in line) {
                if (b)
                    print('*')
                else
                    print('.')
            }
            println()
        }
    }

    private fun doXFold(xFold: Int, grid: List<List<Boolean>>): List<List<Boolean>> {
        val newGrid: MutableList<MutableList<Boolean>> = ArrayList()

        val newGridWidth = grid[0].size - xFold - 1

        // populate new grid as blank
        for (y in grid.indices) {
            newGrid.add(MutableList(newGridWidth) { false })
        }

        // how blank columns do we need?
        val numRightOfFold = grid[0].size - xFold - 1
        val numBlanks = min(0, numRightOfFold - xFold)

        // copy left-of-fold columns to new grid
        for (y in grid.indices) {
            for (x in 0 until xFold) {
                newGrid[y][x + numBlanks] = grid[y][x]
            }
        }

        // copy right-of-fold columns to new grid
        for (y in grid.indices) {
            for (x in grid[y].size - 1 downTo xFold + 1) {
                val newX = grid[y].size - 1 - x
                newGrid[y][newX] = newGrid[y][newX] || grid[y][x]
            }
        }

        return newGrid
    }

    private fun doYFold(yFold: Int, grid: List<List<Boolean>>): List<List<Boolean>> {
        val newGrid: MutableList<MutableList<Boolean>> = ArrayList()

        // how blank lines do we need?
        val numBelowFold = grid.size - yFold - 1
        val numBlanks = min(0, numBelowFold - yFold)
        for (i in 0 until numBlanks) {
            newGrid.add(MutableList(grid[0].size) { false })
        }

        for (i in 0 until yFold) {
            newGrid.add(grid[i].toMutableList())
        }

        for (y in grid.size - 1 downTo yFold + 1 ) {
            val newY = grid.size - 1 - y
            for (x in 0 until grid[y].size) {
                newGrid[newY][x] = newGrid[newY][x] || grid[y][x]
            }
        }

        return newGrid
    }

    private fun parseInput(sample: Int) {
        val filename = if (sample == 0) "..\\..\\data\\day13.txt" else "..\\..\\data\\day13_sample$sample.txt"

        dots.clear()
        folds.clear()

        var state = 0
        for (line in File(filename).readLines()) {
            if (state == 0) {
                if (line.isEmpty()) {
                    state = 1
                }
                else {
                    val tokens = line.split(',')
                    dots.add(Pair(tokens[0].toInt(), tokens[1].toInt()))
                }
            }
            else if (state == 1) {
                val tokens = line.split(' ')
                // throw away first two tokens ("fold" and "along")
                val tokens2 = tokens[2].split('=')
                folds.add(Pair(tokens2[0].first(), tokens2[1].toInt()))
            }
        }
    }

    private var dots: MutableList<Pair<Int, Int>> = ArrayList()
    private var folds: MutableList<Pair<Char, Int>> = ArrayList()
}