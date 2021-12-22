import java.io.File

class NumberPair() {
    fun setDepth(d: Int) {
        depth = d
    }

    fun setLeft(value: Int) {
        leftValue = value
        leftPair = null
    }

    fun setLeft(pair: NumberPair) {
        leftValue = 0
        leftPair = pair
    }

    fun setRight(value: Int) {
        rightValue = value
        rightPair = null
    }

    fun setRight(pair: NumberPair) {
        rightValue = 0
        rightPair = pair
    }

    fun leftIsRegular(): Boolean {
        return leftPair == null
    }

    fun rightIsRegular(): Boolean {
        return rightPair == null
    }

    private var depth: Int = 0
    private var leftValue: Int = 0
    private var rightValue: Int = 0
    private var leftPair: NumberPair? = null
    private var rightPair: NumberPair? = null
}

class Parser(val snailNumber: String) {
    fun parse(): NumberPair {
        val p = parsePair(depth = 0, startIndex = 1)
        return p.first
    }

    private fun parsePair(depth: Int, startIndex: Int): Pair<NumberPair, Int> {
        val np = NumberPair()
        np.setDepth(depth)
        var index = startIndex
        if (snailNumber[index] == '[') {
            val p = parsePair(depth + 1, index + 1)
            np.setLeft(p.first)
            index = p.second
        }
        else {
            val commaIndex = snailNumber.indexOf(',', index)
            np.setLeft(snailNumber.substring(index, commaIndex).toInt())
            index = commaIndex
        }

        // get to comma
        while (snailNumber[index] != ',') {
            index += 1
        }
        // get past comma
        index += 1

        if (snailNumber[index] == '[') {
            val p = parsePair(depth + 1, index + 1)
            np.setRight(p.first)
            index = p.second
        }
        else {
            val bracketIndex = snailNumber.indexOf(']', index)
            np.setRight(snailNumber.substring(index, bracketIndex).toInt())
            index = bracketIndex + 1
        }

        return Pair(np, index)
    }
}

class Day18 {
    fun run() {
        parseInput(1)

        val result1 = numbers.reduce { acc, s -> addAndReduce(acc, s) }
        val magnitude1 = magnitude(result1)

        println("Day 18 Part 1    Magnitude $magnitude1")
    }

    private fun magnitude(number: String): Long {
        return 0
    }

    private fun addAndReduce(a1: String, a2: String): String {
        // "add"
        var snailSum = "[$a1,$a2]"

        // parse
        val parser = Parser(snailSum)
        val rootPair = parser.parse()

        // "reduce"


        return snailSum
    }

    private fun parseInput(sample: Int) {
        when (sample) {
            0 -> {
                val filename = "..\\..\\data\\day18.txt"
                numbers = File(filename).readLines()
            }
            1 -> numbers = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]")
            2 -> numbers = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]")
            3 -> numbers = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]", "[6,6]")
            4 -> numbers = listOf("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]", "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]", "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]", "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]", "[7,[5,[[3,8],[1,4]]]]", "[[2,[2,2]],[8,[8,1]]]", "[2,9]", "[1,[[[9,3],9],[[9,0],[0,7]]]]", "[[[5,[7,4]],7],1]", "[[[[4,2],2],6],[8,7]]")
        }
    }

    private var numbers = listOf<String>()
}