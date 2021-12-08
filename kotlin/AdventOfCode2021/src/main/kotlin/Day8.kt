import java.io.File

class Display(val inputs: List<Set<Char>>, val outputs: List<Set<Char>>) {

}

class Day8 {
    fun run() {
        parseInput(0)
        initPlainDigits()

        val filterSizes = setOf(2, 3, 4, 7)
        var total = 0
        for (d in displays) {
            val result = d.outputs.filter { it.size in filterSizes }.size
            total += result
        }

        println("Day 8 Part 1    Easy count:  $total")

        var valueSum = 0

        for (d in displays) {
            val decoder = decodeInputScrambles(d.inputs)

            var value = 0
            for (o in d.outputs) {
                val digit = decodeDigit(decoder, o)
                value = value * 10 + digit
            }

            valueSum += value
        }

        println("Day 8 Part 2    Output sum:  $valueSum")
    }

    private fun decodeDigit(decoder: Map<Char, Char>, encoded: Set<Char>): Int {
        val decoded = encoded.map { decoder[it]!! }.toSet()
        return plainDigits[decoded]!!
    }

    private fun decodeInputScrambles(displayInputs: List<Set<Char>>): Map<Char, Char> {
        var digits: MutableMap<Int, Set<Char>> = HashMap()
        var subMap: MutableMap<Char, Char> = HashMap()

        // get the easy digits
        for (i in displayInputs) {
            if (i.size == 2) {
                digits[1] = i
            }
            else if (i.size == 3) {
                digits[7] = i
            }
            else if (i.size == 4) {
                digits[4] = i
            }
            else if (i.size == 7) {
                digits[8] = i
            }
        }

        // (7) - (1) => one segment, which is the sub for segment A
        subMap['a'] = digits[7]!!.subtract(digits[1]!!).first()

        // there are 3 inputs with length 5; one of those minus (1) will have length 3, and that will be digit 3
        digits[3] = displayInputs.filter { it.size == 5 }.first { it.subtract(digits[1]!!).size == 3 }

        subMap['e'] = digits[8]!!.subtract(digits[3]!!).subtract(digits[4]!!).first()

        // there are 3 inputs with length 6; one those minus (7) will have length 4, and that will be digit 6
        digits[6] = displayInputs.filter { it.size == 6 }.first { it.subtract(digits[7]!!).size == 4 }

        subMap['c'] = digits[7]!!.subtract(digits[6]!!).first()
        subMap['f'] = digits[1]!!.subtract(setOf(subMap['c']!!)).first()

        // length 5s minus (6); one will have length 0, which will be digit 5
        digits[5] = displayInputs.filter { it.size == 5 }.first { it.subtract(digits[6]!!).isEmpty() }

        subMap['b'] = digits[5]!!.subtract(digits[3]!!).first()

        // digits 0, 3, 9 remaining
        val digits039 = displayInputs.filter { !digits.containsValue(it) }

        // (5) minus digits039; one will have length 0, which will be digit 9
        digits[9] = digits039.first { digits[5]!!.subtract(it).isEmpty() }

        val digits03 = digits039.filter { it != digits[9] }

        digits[0] = digits03.first { digits[8]!!.subtract(it).size == 1 }
        digits[3] = digits03.first { it != digits[0]!! }

        subMap['d'] = digits[5]!!.subtract(digits[0]!!).first()
        subMap['g'] = digits[5]!!.subtract(digits[7]!!).subtract(digits[4]!!).first()

        val reverseMap: MutableMap<Char, Char> = HashMap()
        for (e in subMap) {
            reverseMap[e.value] = e.key
        }
        return reverseMap
    }

    private fun initPlainDigits() {
        plainDigits.clear()
        plainDigits["abcefg".toCharArray().toSet()] = 0
        plainDigits["cf".toCharArray().toSet()] = 1
        plainDigits["acdeg".toCharArray().toSet()] = 2
        plainDigits["acdfg".toCharArray().toSet()] = 3
        plainDigits["bcdf".toCharArray().toSet()] = 4
        plainDigits["abdfg".toCharArray().toSet()] = 5
        plainDigits["abdefg".toCharArray().toSet()] = 6
        plainDigits["acf".toCharArray().toSet()] = 7
        plainDigits["abcdefg".toCharArray().toSet()] = 8
        plainDigits["abcdfg".toCharArray().toSet()] = 9
    }

    private fun parseInput(sample: Int) {
        val filename =
            if (sample <= 0) "..\\..\\data\\day8.txt" else if (sample == 1) "..\\..\\data\\day8_sample.txt" else "..\\..\\data\\day8_sample2.txt"

        val lines = File(filename).readLines()
        displays.clear()
        for (line in lines) {
            val inOuts = line.split(" | ").map { it.split(' ').map { s -> s.toCharArray().toSet() }}
            displays.add(Display(inOuts[0], inOuts[1]))
        }
    }

    private var displays: MutableList<Display> = ArrayList()
    private var plainDigits: MutableMap<Set<Char>, Int> = HashMap()
}