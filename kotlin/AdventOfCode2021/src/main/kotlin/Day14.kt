import java.io.File

class Day14 {
    fun run() {
        parseInput(0)

        val result1 = doIt(10)
        println("Day 14 Part 1    most - least:  $result1")

        // Part 2 is cribbed from looking at the Reddit solutions thread

        var pairs: MutableMap<Pair<Char, Char>, Long> = HashMap()
        for (i in 0 until startingTemplate.size - 1) {
            val key = Pair(startingTemplate[i], startingTemplate[i + 1])
            pairs[key] = pairs.getOrDefault(key, 0) + 1
        }

        for (step in 1..40) {
            val newPairs: MutableMap<Pair<Char, Char>, Long> = HashMap()
            for (p in pairs) {
                val sub = substitutions[p.key]!!
                val p1 = Pair(p.key.first, sub)
                val p2 = Pair(sub, p.key.second)
                newPairs[p1] = newPairs.getOrDefault(p1, 0) + p.value
                newPairs[p2] = newPairs.getOrDefault(p2, 0) + p.value
            }
            pairs = newPairs
        }

        val letterCounts: MutableMap<Char, Long> = HashMap()
        for (p in pairs) {
            letterCounts[p.key.first] = letterCounts.getOrDefault(p.key.first, 0) + p.value
            letterCounts[p.key.second] = letterCounts.getOrDefault(p.key.second, 0) + p.value
        }

        // first and last letters need a bump
        letterCounts[startingTemplate.first()] = letterCounts[startingTemplate.first()]!! + 1
        letterCounts[startingTemplate.last()] = letterCounts[startingTemplate.last()]!! + 1

        val mostCommon = letterCounts.maxOf { it.value }
        val leastCommon = letterCounts.minOf { it.value }

        println("Day 14 Part 2    most - least:  ${mostCommon/2 - leastCommon/2}")
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