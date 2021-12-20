import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

class Day15 {
    fun run() {
        parseInput(0)

        val part1Risk = riskOfShortestPath(originalCavern)

        println("Day 15 Part 1    Total risk on shortest path:  $part1Risk")

        val bigCavern = make5XCavern()

        val part2Risk = riskOfShortestPath(bigCavern)

        println("Day 15 Part 2    Total risk on shortest path:  $part2Risk")
    }

    private fun make5XCavern(): List<IntArray> {
        val bigCavern: MutableList<IntArray> = ArrayList()

        // do the first N rows (N is size of original list)
        for (r in originalCavern.indices) {
            val rowSize = originalCavern[r].size
            val a: IntArray = IntArray(rowSize * 5)
            for (c in originalCavern[r].indices) {
                a[c] = originalCavern[r][c]
            }
            for (base in 1..4) {
                val startIndex = base * rowSize
                for (c in startIndex until startIndex + rowSize) {
                    a[c] = max(1, (a[c - rowSize] + 1) % 10)
                }
            }
            bigCavern.add(a)
        }

        // do remaining rows
        for (r in originalCavern.size until originalCavern.size * 5) {
            val sourceRow = r - originalCavern.size
            val a: IntArray = IntArray(bigCavern[sourceRow].size)
            for (c in a.indices) {
                a[c] = max(1, (bigCavern[sourceRow][c] + 1) % 10)
            }
            bigCavern.add(a)
        }

        return bigCavern
    }

    private fun riskOfShortestPath(cavern: List<IntArray>): Long {
        val vertexKeys: MutableSet<Pair<Int, Int>> = HashSet()
        for (row in cavern.indices) {
            for (col in cavern[row].indices) {
                vertexKeys.add(Pair(row, col))
            }
        }

        val q: MutableSet<Pair<Int, Int>> = HashSet()
        val dist: MutableMap<Pair<Int, Int>, Long> = HashMap()
        val prev: MutableMap<Pair<Int, Int>, Pair<Int, Int>?> = HashMap()

        for (key in vertexKeys) {
            dist[key] = Long.MAX_VALUE
            prev[key] = null
            q.add(key)
        }
        dist[Pair(0, 0)] = 0

        val target = Pair(cavern.size - 1, cavern.last().size - 1)

        while (q.isNotEmpty()) {
            if (q.size % 100 == 0) {
                println("q size:  ${q.size}")
            }

//            val minDist = q.minOf { dist[it]!! }
//            val u = q.first { dist[it]!! == minDist }
            val u = q.minByOrNull { dist[it]!! }!!

            q.remove(u)

            if (u == target) {
                break
            }

            val neighborKeys = neighborsOf(cavern, u)
            for (v in neighborKeys) {
                if (q.contains(v)) {
                    val alt = dist[u]!! + cavern[v.first][v.second]
                    if (alt < dist[v]!!) {
                        dist[v] = alt
                        prev[v] = u
                    }
                }
            }
        }

        var totalRisk = 0L
        var v = Pair(cavern.size - 1, cavern.last().size - 1)
        val source = Pair(0 , 0)
        while (v != source) {
            val thisRisk = cavern[v.first][v.second]
            totalRisk += thisRisk
            v = prev[v]!!
        }

        return totalRisk
    }

    private fun neighborsOf(cavern: List<IntArray>, v: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val neighbors: MutableSet<Pair<Int, Int>> = HashSet()
        if (v.first > 0) {
            neighbors.add(Pair(v.first - 1, v.second))
        }
        if (v.second > 0) {
            neighbors.add(Pair(v.first, v.second - 1))
        }
        if (v.first < cavern.size - 1) {
            neighbors.add(Pair(v.first + 1, v.second))
        }
        if (v.second < cavern[v.first].size - 1) {
            neighbors.add(Pair(v.first, v.second + 1))
        }

        return neighbors
    }

//    private fun neighborsOf(v: Pair<Int, Int>): Set<Pair<Int, Int>> {
//        val neighbors: MutableSet<Pair<Int, Int>> = HashSet()
//        for (r in max(0, v.first - 1)..min(cavern.size - 1, v.first + 1)) {
//            for (c in max(0, v.second -1)..min(cavern[r].size - 1, v.second + 1)) {
//                val p = Pair(r, c)
//                if (v != p) {
//                    neighbors.add(p)
//                }
//            }
//        }
//
//        return neighbors
//    }

    private fun parseInput(sample: Int) {
        val filename = if (sample == 0) "..\\..\\data\\day15.txt" else "..\\..\\data\\day15_sample$sample.txt"

        originalCavern = File(filename).readLines().map { line -> line.map { c -> c.digitToInt() }.toIntArray() }
    }

    private var originalCavern: List<IntArray> = ArrayList()
}