import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    fun isHorizontal(): Boolean {
        return y1 == y2
    }

    fun isVertical(): Boolean {
        return x1 == x2
    }

    fun isDiagonal(): Boolean {
        val slope = (y2 - y1).toDouble() / (x2 - x1).toDouble()
        return abs(slope) == 1.0
    }

    fun maxX(): Int {
        return max(x1, x2)
    }

    fun maxY(): Int {
        return max(y1, y2)
    }
}

class Day5 {
    fun run() {
//        parseInput("..\\..\\data\\day5_sample.txt")
        parseInput("..\\..\\data\\day5.txt")

        // find dimensions
        val maxX = lines.maxOf { it.maxX() }
        val maxY = lines.maxOf { it.maxY() }

        var ventMap: MutableList<MutableList<Int>> = ArrayList()
        for (i in 0..maxY) {
            ventMap.add(MutableList(maxX + 1) { 0 })
        }

        for (l in lines) {
            if (l.isHorizontal()) {
                val minX = min(l.x1, l.x2)
                val maxX = max(l.x1, l.x2)
                for (x in minX..maxX) {
                    ventMap[l.y1][x] += 1
                }
            }
            else if (l.isVertical()) {
                val minY = min(l.y1, l.y2)
                val maxY = max(l.y1, l.y2)
                for (y in minY..maxY) {
                    ventMap[y][l.x1] += 1
                }
            }
        }

        // count line overlaps
        val v = ventMap.sumOf { it.count { v -> v > 1 } }

        println("Day 5 Part 1:  $v overlaps")

        // do diagonals
        for (l in lines) {
            if (l.isDiagonal()) {
                val stepX = if (l.x1 < l.x2) 1 else -1
                val stepY = if (l.y1 < l.y2) 1 else -1
                var x = l.x1
                var y = l.y1
                for (i in 0..abs(l.y2 - l.y1)) {
                    ventMap[y][x] += 1
                    x += stepX
                    y += stepY
                }
            }
        }

        val v2 = ventMap.sumOf { it.count { v -> v > 1 } }

        println("Day 5 Part 2:  $v2 overlaps")
    }

    fun parseInput(filename: String) {
        val l = File(filename).useLines { it.toList() }.map {
            val coordPairs = it.split(" -> ")
            val c1 = coordPairs[0].split(',').map { s -> s.toInt() }
            val c2 = coordPairs[1].split(',').map { s -> s.toInt() }
            return@map Line(c1[0], c1[1], c2[0], c2[1])
        }
        lines = ArrayList(l)
    }

    private var lines: MutableList<Line> = ArrayList()
}