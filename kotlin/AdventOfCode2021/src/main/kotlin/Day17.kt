import java.lang.Integer.max

class Day17 {
    fun run() {
        parseInput(0)

        var yMax = -1
        var initialAtYMax = Pair(0, 0)

        val allHitAreas = mutableListOf<Pair<Int, Int>>()

        for (xInitial in 1..xRange.second) {
            var xOvershoot = false
            var yInitial = yRange.first
            var thisYMax = -1

            while (!xOvershoot && yInitial < 300) {
                var xVel = xInitial
                var yVel = yInitial
                var x = 0
                var y = 0

                var hitArea = false

                while (!hitArea && x <= xRange.second && y >= yRange.first) {
                    x += xVel
                    y += yVel
                    thisYMax = max(y, thisYMax)

                    xVel -= if (xVel == 0) 0 else if (xVel < 0) -1 else 1
                    yVel -= 1

                    if (x in xRange.first..xRange.second && y in yRange.first..yRange.second) {
                        hitArea = true
                        allHitAreas.add(Pair(xInitial, yInitial))
                    }
                }

                if (hitArea && thisYMax > yMax) {
                    yMax = thisYMax
                    initialAtYMax = Pair(xInitial, yInitial)
                }

                xOvershoot = x > xRange.second
                yInitial += 1
            }
        }

        println("Day 17 Part 1    Highest y: $yMax    Initial velocity: X=${initialAtYMax.first}, Y=${initialAtYMax.second}")

        println("Day 17 Part 2    Initial vels that hit area:  ${allHitAreas.size}")
    }

    private fun parseInput(sample: Int) {
        when (sample) {
            0 -> {
                // "target area: x=139..187, y=-148..-89"
                xRange = Pair(139, 187)
                yRange = Pair(-148, -89)
            }
            1 -> {
                //  "target area: x=20..30, y=-10..-5"
                xRange = Pair(20, 30)
                yRange = Pair(-10, -5)
            }
            else -> {
                xRange = Pair(0, 0)
                yRange = Pair(0, 0)
            }
        }
    }

    private var xRange = Pair(0, 0)
    private var yRange = Pair(0, 0)
}