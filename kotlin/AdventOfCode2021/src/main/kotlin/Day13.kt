import java.io.File

class Day13 {
    fun run() {
        parseInput(1)
    }

    private fun parseInput(sample: Int) {
        val filename = if (sample == 0) "..\\..\\data\\day13.txt" else "..\\..\\data\\day13_sample$sample.txt"

        File(filename).readLines()
    }
}