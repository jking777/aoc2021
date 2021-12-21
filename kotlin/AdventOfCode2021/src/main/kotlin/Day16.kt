import java.io.File

class Day16 {
    fun run() {
        parseInput(0)

        versions.clear()
        val result = decodePacket(0)

        val versionSum = versions.sum()

        println("Day 16 Part 1    Versions sum:  $versionSum")

        println("Day 16 Part 2    Operation result:  ${result.second}")
    }

    private fun decodePacket(startIndex: Int): Pair<Int, Long> {
        var index = startIndex
        var packetValue = 0L

        val version = binaryStringToValue(binary.substring(index, index + 3))
        index += 3
        versions.add(version)

        val typeID = binaryStringToValue(binary.substring(index, index + 3))
        index += 3

        if (typeID == 4) {
            packetValue = 0
            var hasMore = true
            while (hasMore) {
                hasMore = binary[index] == '1'
                val subValue = binaryStringToValue(binary.substring(index + 1, index + 5))
                packetValue = packetValue * 16 + subValue
                index += 5
            }
        }
        else {
            val subPacketValues = mutableListOf<Long>()
            val lengthTypeID = binary[index].digitToInt()
            var subPacketsBitLength = 0
            var subPacketsCount = 0
            index += 1
            if (lengthTypeID == 0) {
                subPacketsBitLength = binaryStringToValue(binary.substring(index, index + 15))
                index += 15

                val subPacketsEnd = index + subPacketsBitLength
                while (index < subPacketsEnd) {
                    val p = decodePacket(index)
                    index = p.first
                    subPacketValues.add(p.second)
                }
            }
            else {
                subPacketsCount = binaryStringToValue(binary.substring(index, index + 11))
                index += 11

                for (packetNum in 1..subPacketsCount) {
                    val p = decodePacket(index)
                    index = p.first
                    subPacketValues.add(p.second)
                }
            }

            // operate on sub-packet values
            when (typeID) {
                0 -> packetValue = subPacketValues.sum()
                1 -> packetValue = subPacketValues.reduce { acc, i -> acc * i }
                2 -> packetValue = subPacketValues.minOrNull()!!
                3 -> packetValue = subPacketValues.maxOrNull()!!
                5 -> packetValue = if (subPacketValues[0] > subPacketValues[1]) 1 else 0
                6 -> packetValue = if (subPacketValues[0] < subPacketValues[1]) 1 else 0
                7 -> packetValue = if (subPacketValues[0] == subPacketValues[1]) 1 else 0
            }
        }

        return Pair(index, packetValue)
    }

    private fun binaryStringToValue(bin: String): Int {
        var value = 0
        bin.forEach { value = value * 2 + it.digitToInt() }
        return value
    }

    private fun parseInput(sample: Int) {
        var bits = ""
        if (sample == 0) {
            val filename = "..\\..\\data\\day16.txt"
            bits = File(filename).readLines()[0]
        }
        else {
            when (sample) {
                1 -> bits = "D2FE28"
                2 -> bits = "38006F45291200"
                3 -> bits = "EE00D40C823060"
                4 -> bits = "8A004A801A8002F478"
                5 -> bits = "620080001611562C8802118E34"
                6 -> bits = "C0015000016115A2E0802F182340"
                7 -> bits = "A0016C880162017C3686B18A3D4780"

                11 -> bits = "C200B40A82"
                12 -> bits = "04005AC33890"
                13 -> bits = "880086C3E88112"
                14 -> bits = "CE00C43D881120"
                15 -> bits = "D8005AC2A8F0"
                16 -> bits = "F600BC2D8F"
                17 -> bits = "9C005AC2F8F0"
                18 -> bits = "9C0141080250320F1802104A08"
                else -> bits = ""
            }
        }

        val hexCharToBinString = mapOf(
            '0' to "0000", '1' to "0001", '2' to "0010", '3' to "0011",
            '4' to "0100", '5' to "0101", '6' to "0110", '7' to "0111",
            '8' to "1000", '9' to "1001", 'A' to "1010", 'B' to "1011",
            'C' to "1100", 'D' to "1101", 'E' to "1110", 'F' to "1111"
        )

        binary = bits.map { hexCharToBinString[it]!! }.joinToString(separator = "")
    }

    private var binary: String = ""

    private val versions = mutableListOf<Int>()
}