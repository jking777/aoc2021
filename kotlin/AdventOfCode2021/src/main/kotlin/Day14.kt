import java.io.File

class Day14 {
    fun run() {
        parseInput(1)

        val result1 = doIt(10)
        println("Day 14 Part 1    most - least:  $result1")

        val result2 = doIt(20)
        println("Day 14 Part 2    most - least:  $result2")
    }

    private fun doIt(count: Int): Int {
        var result = startingTemplate
        for (i in 1..count) {
            result = applySubs(result)
        }

        val frequencies: MutableMap<Char, Int> = HashMap()
        for (c in result) {
            frequencies.putIfAbsent(c, 0)
            frequencies[c] = frequencies[c]!! + 1
        }

        val mostCommon = frequencies.maxOf { it.value }
        val leastCommon = frequencies.minOf { it.value }

        return mostCommon - leastCommon
    }

    private fun applySubs(template: List<Char>): List<Char> {
        var polymer: MutableList<Char> = ArrayList()
        polymer.add(template.first())
        for (i in 0 until template.size - 1) {
            val pair = Pair(template[i], template[i + 1])
            val sub = substitutions[pair]!!
            polymer.add(sub)
            polymer.add(pair.second)
        }

        return polymer
    }

    private fun parseInput(sample: Int) {
        val filename = if (sample == 0) "..\\..\\data\\day14.txt" else "..\\..\\data\\day14_sample$sample.txt"

        val lines = File(filename).readLines()

        startingTemplate = lines[0].toList()

        substitutions = lines.drop(2).map { it.split(" -> ") }.associate { it[0].let { s -> Pair(s[0], s[1]) } to it[1][0] }
    }

    private var startingTemplate: List<Char> = ArrayList()
    private var substitutions: Map<Pair<Char, Char>, Char> = HashMap()
}