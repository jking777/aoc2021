import java.io.File

class Board(boardLines: List<String>) {
    var score: Int = 0
    private var board: MutableList<MutableList<Pair<Int, Boolean>>> = arrayListOf()

    init {
        for (s in boardLines) {
            val splits = s.trim().split("\\s+".toRegex())
            val boardLine = splits.map { Pair(it.toInt(), false) }
            board.add(ArrayList(boardLine))
        }
    }

    fun mark(value: Int): Boolean {
        var isMarked = false
        var markRow = 0
        var markColumn = 0

        for (i in 0 until board.size) {
            for (j in 0 until board[i].size) {
                if (board[i][j].first == value) {
                    board[i][j] = Pair(board[i][j].first, true)
                    isMarked = true
                    markRow = i
                    markColumn = j
                    break
                }
            }
            if (isMarked) {
                break
            }
        }

        if (isMarked) {
            // if either markRow or markColumn is all true then we have a bingo; calculate score and return true
            if (board[markRow].all { it.second } || board.map { it[markColumn] }.all { it.second }) {
                val sum = board.sumOf { it.sumOf { p -> if (p.second) 0 else p.first } }
                score = sum * value
                return true
            }
        }

        return false
    }

    fun resetMarks() {
        for (line in board) {
            for (j in 0 until line.size)
            {
                line[j] = Pair(line[j].first, false)
            }
        }
    }
}

class Day4 {
    fun run() {
//        parseInput("..\\..\\data\\day4_sample.txt")
        parseInput("..\\..\\data\\day4.txt")

        var winningScore = 0

        for (i in ballValues) {
            for (b in boards) {
                if (b.mark(i)) {
                    winningScore = b.score
                    break
                }
            }
            if (winningScore > 0) {
                break
            }
        }

        println("Part 1 winning score:  $winningScore")

        for (b in boards) {
            b.resetMarks()
        }

        var losingScore = 0

        for (i in ballValues) {
            val winners: MutableList<Board> = ArrayList()
            for (b in boards) {
                if (b.mark(i)) {
                    winners.add(b)
                }
            }

            for (w in winners) {
                if (boards.size > 1) {
                    boards.remove(w)
                }
                else {
                    losingScore = boards[0].score
                }
            }

            if (losingScore > 0) {
                break
            }
        }

        println("Part 2 losing score:  $losingScore")
    }

    private fun parseInput(filename: String) {
        val lines = File(filename).useLines { it.toList() }
        ballValues = lines[0].split(',').map { it.toInt() }

        boards = ArrayList()
        for (lineNumber in 2 until lines.size step 6) {
            val board = Board(lines.subList(lineNumber, lineNumber + 5))
            boards.add(board)
        }
    }

    private var boards: MutableList<Board> = ArrayList()
    private var ballValues: List<Int> = ArrayList()
}