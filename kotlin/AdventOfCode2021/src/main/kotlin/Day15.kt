import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

class Day15 {
    fun run() {
        parseInput(0)

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
            val minDist = q.minOf { dist[it]!! }
            val u = q.first { dist[it]!! == minDist }

            q.remove(u)

            if (u == target) {
                break
            }

            val neighborKeys = neighborsOf(u)
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

        var totalRisk = 0
        var v = Pair(cavern.size - 1, cavern.last().size - 1)
        val source = Pair(0 , 0)
        while (v != source) {
            val thisRisk = cavern[v.first][v.second]
            totalRisk += thisRisk
            v = prev[v]!!
        }

        println("Day 15 Part 1    Total risk on shortest path:  $totalRisk")
    }

    private fun neighborsOf(v: Pair<Int, Int>): Set<Pair<Int, Int>> {
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

        cavern = File(filename).readLines().map { line -> line.map { c -> c.digitToInt() }.toIntArray() }

        vertexKeys.clear()
        for (row in cavern.indices) {
            for (col in cavern[row].indices) {
                vertexKeys.add(Pair(row, col))
            }
        }
    }

    private var cavern: List<IntArray> = ArrayList()

    private val vertexKeys: MutableSet<Pair<Int, Int>> = HashSet()
}