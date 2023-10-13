package de.jball.aoc2021.day16

import de.jball.AdventOfCodeDay

class Day16(test: Boolean = false): AdventOfCodeDay<Long>(test, 31, 54) {
    private val binary = input[0]
        .chunked(2)
        .map { it.toUInt(16) }
        .joinToString(separator = "") { it.toString(2).padStart(8, '0') }

    override fun part1(): Long {
        val message = decodeNextPacket(binary)
        return message.first.getVersionSum().toLong()
    }

    override fun part2(): Long {
        val message = decodeNextPacket(binary)
        return message.first.getData()
    }

    private fun decodeNextPacket(subPacketsString: String): Pair<Packet, Int> {
        val version = subPacketsString.substring(0, 3).toByte(2)
        val typeId = subPacketsString.substring(3, 6).toByte(2)
        val data: Long?
        val subPackets: List<Packet>
        val length: Int
        if (typeId == (4).toByte()) {
            val (_data, _length) = decodeLiteralValue(subPacketsString.substring(6))
            data = _data
            length = _length
            subPackets = listOf()
        } else {
            val (_subPackets, _length) = decodeOperatorPacket(subPacketsString.substring(6))
            subPackets = _subPackets
            length = _length
            data = null
        }
        return Pair(Packet(version, typeId, data, subPackets), length + 6)
    }

    private fun decodeLiteralValue(literalString: String): Pair<Long, Int> {
        val groups = literalString.chunked(5)
        var binaryNumber = ""
        var length = 0
        for (group in groups) {
            binaryNumber += group.substring(1..4)
            length += 5
            if (group[0] == '0') {
                break
            }
        }
        return Pair(binaryNumber.toLong(2), length)
    }

    private fun decodeOperatorPacket(packetString: String): Pair<List<Packet>, Int> {
        when (val lengthTypeId = packetString.substring(0, 1)) {
            "0" -> {
                val bitLength = packetString.substring(1, 16).toInt(2)
                val (subPackets, length) = decodeSubPackets(packetString.substring(16, 16 + bitLength))
                return Pair(subPackets, length + 16)
            }

            "1" -> {
                val subPacketCount = packetString.substring(1, 12).toInt(2)
                val subPackets: MutableList<Packet> = mutableListOf()
                var tmp = packetString.substring(12)
                var subPacketsLength = 12
                while (subPackets.size < subPacketCount) {
                    val (subPacket, length) = decodeNextPacket(tmp)
                    tmp = tmp.substring(length)
                    subPackets.add(subPacket)
                    subPacketsLength += length
                }
                return Pair(subPackets, subPacketsLength)
            }

            else -> {
                throw IllegalArgumentException("Unexpected lengthTypeId $lengthTypeId")
            }
        }
    }

    private fun decodeSubPackets(subPacketsString: String): Pair<List<Packet>, Int> {
        var tmp = subPacketsString
        val result = mutableListOf<Packet>()
        var length = 0
        while (tmp.length > 6) {
            val (subPacket, subPacketLength) = decodeNextPacket(tmp)
            result.add(subPacket)
            tmp = tmp.substring(subPacketLength)
            length += subPacketLength
        }
        return Pair(result, length)
    }
}

private data class Packet(
    val version: Byte,
    val typeId: Byte,
    val data: Long?,
    val subPackets: List<Packet>
) {
    fun getVersionSum(): Int {
        return subPackets.sumOf { it.getVersionSum() } + version
    }

    fun getData(): Long {
        return data ?: performOperation()
    }

    private fun performOperation(): Long {
        return when (typeId.toInt()) {
            0 -> subPackets.sumOf { it.getData() }
            1 -> subPackets.map { it.getData() }.reduce { acc, l -> acc * l }
            2 -> subPackets.minOf { it.getData() }
            3 -> subPackets.maxOf { it.getData() }
            5 -> if (subPackets[0].getData() > subPackets[1].getData()) 1 else 0
            6 -> if (subPackets[0].getData() < subPackets[1].getData()) 1 else 0
            7 -> if (subPackets[0].getData() == subPackets[1].getData()) 1 else 0
            else -> throw UnsupportedOperationException("TypeId $typeId not supported.")
        }
    }
}

fun main() {
    Day16(false).run()
}
