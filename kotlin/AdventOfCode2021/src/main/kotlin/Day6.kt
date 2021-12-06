import java.io.File

class Day6 {
    fun run() {
//        parseInput("..\\..\\data\\day6_sample.txt")
        parseInput("..\\..\\data\\day6.txt")

        var startMillis = System.currentTimeMillis()

        var ages: MutableList<Int> = ArrayList(initialAges)

        for (day in 1..80) {
            var additionalFish = 0
            for (i in 0 until ages.size) {
                if (ages[i] == 0) {
                    ages[i] = 6
                    additionalFish += 1
                }
                else {
                    ages[i] -= 1
                }
            }

            if (additionalFish > 0) {
                for (i in 1..additionalFish) {
                    ages.add(8)
                }
            }
        }

        val elapsed1 = System.currentTimeMillis() - startMillis
        println("Day 6 Part 1    Number of lantern fish after 80 days:  ${ages.size}")
        println("Day 6 Part 1    Time:  $elapsed1 ms")
        println()

        startMillis = System.currentTimeMillis()

        var ageGroups: MutableMap<Int, Long> = HashMap()
        for (age in initialAges) {
            val count = ageGroups.computeIfAbsent(age) { 0 }
            ageGroups[age] = count + 1
        }

        for (day in 1..256) {
            var newAgeGroups: MutableMap<Int, Long> = HashMap()
            for (entry in ageGroups) {
                if (entry.key == 0) {
                    newAgeGroups[8] = entry.value

                    // may need to merge with existing 6
                    val count = newAgeGroups.computeIfAbsent(6) { 0 }
                    newAgeGroups[6] = count + entry.value

                }
                else if (entry.key == 7) {
                    // going down to 6, so we need to merge with existing 6
                    val count = newAgeGroups.computeIfAbsent(6) { 0 }
                    newAgeGroups[6] = count + entry.value
                }
                else {
                    newAgeGroups[entry.key - 1] = entry.value
                }
            }

            ageGroups = newAgeGroups
        }

        val fishes = ageGroups.map { it.value }.sum()

        val elapsed2 = System.currentTimeMillis() - startMillis
        println("Day 6 Part 2    Number of lantern fish after 256 days:  $fishes")
        println("Day 6 Part 2    Time:  $elapsed2 ms")
        println()
    }

    fun parseInput(filename: String) {
        val lines = File(filename).readLines()
        initialAges = lines[0].split(',').map { it.toInt() }
    }

    private var initialAges: List<Int> = ArrayList()
}