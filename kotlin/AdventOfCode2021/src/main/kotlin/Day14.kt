import java.io.File

class Day14 {
    fun run() {
        parseInput(1)

        val result1 = doIt(10)
        println("Day 14 Part 1    most - least:  $result1")

        val result2 = doIt(40)
        println("Day 14 Part 2    most - least:  $result1")
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

    private fun applySubs(template: String): String {
        var polymer: MutableList<Char> = ArrayList()
        polymer.add(template.first())
        for (i in 0 until template.length - 1) {
            val pair = template.substring(i, i + 2)
            val sub = substitutions[pair]!!
            polymer.add(sub.first())
            polymer.add(pair[1])
        }

        return polymer.joinToString("")
    }

    private fun parseInput(sample: Int) {
        val filename = if (sample == 0) "..\\..\\data\\day14.txt" else "..\\..\\data\\day14_sample$sample.txt"

        val lines = File(filename).readLines()

        startingTemplate = lines[0]

        substitutions = lines.drop(2).map { it.split(" -> ") }.associate { it[0] to it[1] }
    }

    private var startingTemplate: String = ""
    private var substitutions: Map<String, String> = HashMap()
}