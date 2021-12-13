import java.io.File

class Cave(val name: String) {
    var isSmall: Boolean = false
    val connectedCaves: MutableSet<Cave> = HashSet()

    init {
        if (name.all { it.isLowerCase()} ) {
            isSmall = true
        }
    }

    fun addConnection(other: Cave) {
        connectedCaves.add(other)
    }

    fun clearConnections() {
        connectedCaves.clear()
    }
}

class Day12 {
    fun run() {
        parseInput(0)

        val path: MutableList<Cave> = ArrayList()
        path.add(start)
        val p = makePaths(path, start)

        val numEndingInEnd = p.filter { it.last() == end }.size

        println("Day 12 Part 1    Paths:  $numEndingInEnd")

        val startMillis = System.currentTimeMillis()

        val p2 = makePathsTwoSmall(path, start)

        val endOnly = p2.filter { it.last() == end }
        val num2 = endOnly.size

//        printPaths(endOnly)

        println("Day 12 Part 2    Paths:  $num2")
        println("Day 12 Part 2    Elapsed:  ${System.currentTimeMillis() - startMillis} ms")
    }

    private fun printPaths(paths: List<List<Cave>>) {
        for (p in paths) {
            for (c in p) {
                print("${c.name},")
            }
            println()
        }
        println()
    }

    private fun makePathsTwoSmall(prefix: List<Cave>, fromHere: Cave): List<List<Cave>> {
        if (fromHere == end) {
            return ArrayList()
        }

        val result: MutableList<List<Cave>> = ArrayList()

        for (c in fromHere.connectedCaves) {
            if (c == start) {
                continue
            }

            val hasOnlyOneEachSmall = pathContainsOnlyOneEachSmall(prefix)
            if (c.isSmall && prefix.contains(c) && !hasOnlyOneEachSmall) {
                continue
            }

            val path: MutableList<Cave> = ArrayList()
            path.add(c)
            val p = makePathsTwoSmall(prefix + path, c)
            if (p.isEmpty()) {
                result.add(path)
            }
            else {
                for (subP in p) {
                    result.add(path + subP)
                }
            }
        }

        return result
    }

    private fun pathContainsOnlyOneEachSmall(path: List<Cave>): Boolean {
        val smalls = path.filter { it.isSmall }
        if (smalls.isEmpty()) {
            return true
        }
        return smalls.size == smalls.distinct().size
    }

    private fun makePaths(prefix: List<Cave>, fromHere: Cave): List<List<Cave>> {
        if (fromHere == end) {
            return ArrayList()
        }

        val result: MutableList<List<Cave>> = ArrayList()

        for (c in fromHere.connectedCaves) {
            if (prefix.contains(c) && c.isSmall) {
                continue
            }

            val path: MutableList<Cave> = ArrayList()
            path.add(c)
            val p = makePaths(prefix + path, c)
            if (p.isEmpty()) {
                result.add(path)
            }
            else {
                for (subP in p) {
                    result.add(path + subP)
                }
            }
        }

        return result
    }

    private fun parseInput(sample: Int) {
        val filename =
            if (sample <= 0) "..\\..\\data\\day12.txt" else "..\\..\\data\\day12_sample$sample.txt"

        caves.clear()
        start.clearConnections()
        caves[start.name] = start
        end.clearConnections()
        caves[end.name] = end

        for (line in File(filename).readLines()) {
            val terminii = line.split('-')
            val cave0 = caves.computeIfAbsent(terminii[0]) { s -> Cave(s) }
            val cave1 = caves.computeIfAbsent(terminii[1]) { s -> Cave(s) }
            cave0.addConnection(cave1)
            cave1.addConnection(cave0)
        }
    }

    val caves: MutableMap<String, Cave> = HashMap()
    val start: Cave = Cave("start")
    val end: Cave = Cave("end")
}