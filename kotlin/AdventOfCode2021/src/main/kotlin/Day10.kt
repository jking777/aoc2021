import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class Day10 {
    fun run() {
        parseInput(0)

        val openChars = setOf('(', '[', '{', '<')
        val expectedPops = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
        val illegalScores = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

        val illegalChars: MutableList<Char> = ArrayList()

        val incompleteLines: MutableList<String> = ArrayList()

        for (line in navLines) {
            val stack: Deque<Char> = ArrayDeque()
            var isCorrupt = false
            for (c in line) {
                if (c in openChars) {
                    stack.addLast(c)
                }
                else {
                    val pop = stack.removeLast()!!
                    if (pop != expectedPops[c]) {
                        illegalChars.add(c)
                        isCorrupt = true
                        break
                    }
                }
            }

            if (!isCorrupt) {
                incompleteLines.add(line)
            }
        }

        val scoreTotal = illegalChars.sumOf { illegalScores[it]!! }

        println("Day 10 Part 1    Illegal characters points:  $scoreTotal")

        // make reverse map to do completions
        val missingCompletions = expectedPops.entries.associateBy({ it.value }) { it.key }

        val completionLines: MutableList<List<Char>> = ArrayList()

        for (line in incompleteLines) {
            val stack: Deque<Char> = ArrayDeque()
            var isCorrupt = false
            for (c in line) {
                if (c in openChars) {
                    stack.addLast(c)
                }
                else {
                    val pop = stack.removeLast()!!
                    if (pop != expectedPops[c]) {
                        throw IllegalArgumentException("Got an unexpected pop in incomplete line: $line")
                    }
                }
            }

            val completionChars: MutableList<Char> = ArrayList()
            while (stack.size > 0) {
                val pop = stack.removeLast()
                completionChars.add(missingCompletions[pop]!!)
            }

            completionLines.add(completionChars)
        }

        val completionCharScores = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

        val completionLineScores: MutableList<Long> = ArrayList()

        for (line in completionLines) {
            var score: Long = 0
            for (c in line) {
                score = score * 5 + completionCharScores[c]!!
            }
            completionLineScores.add(score)
        }

        val middleScore = completionLineScores.sorted()[completionLineScores.size / 2]

        println("Day 10 Part 2    Middle completion score:  $middleScore")
    }

    private fun parseInput(sample: Int) {
        val filename =
            if (sample <= 0) "..\\..\\data\\day10.txt" else "..\\..\\data\\day10_sample.txt"

        navLines = File(filename).readLines()
    }

    private var navLines: List<String> = ArrayList()
}