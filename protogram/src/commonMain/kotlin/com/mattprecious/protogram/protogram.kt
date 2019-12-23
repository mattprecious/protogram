@file:JvmName("Protogram")
package com.mattprecious.protogram

import com.mattprecious.tinsel.Node.Branch
import com.mattprecious.tinsel.Node.Leaf
import com.mattprecious.tinsel.Tree
import com.mattprecious.tinsel.render
import com.squareup.wire.FieldEncoding.FIXED32
import com.squareup.wire.FieldEncoding.FIXED64
import com.squareup.wire.FieldEncoding.LENGTH_DELIMITED
import com.squareup.wire.FieldEncoding.VARINT
import com.squareup.wire.ProtoReader
import okio.Buffer
import okio.ByteString
import kotlin.jvm.JvmName

fun printProto(bytes: ByteString): String {
  return bytes.readProtoTree().render()
}

internal fun ByteString.readProtoTree(): Tree {
  return ProtoReader(Buffer().write(this)).readTree()
}

private fun ProtoReader.readTree(): Tree {
  return generateFieldSequence()
      .flatMap { (tag, encoding) ->
        val tagString = tag.toString()

        val nodeValues = when (encoding) {
          VARINT -> listOf(readVarint64().toString())
          FIXED64 -> {
            val long = readFixed64()
            val double = Double.fromBits(long)

            listOf("$long ($double)")
          }
          FIXED32 -> listOf(readFixed32().toString())
          LENGTH_DELIMITED -> {
            val bytes = readBytes()

            if (bytes.size == 0) {
              listOf("(empty)")
            } else {
              try {
                return@flatMap listOf(Branch(tagString, bytes.readProtoTree().nodes)).asSequence()
              } catch (_: Exception) {
                if (bytes.isProbablyUtf8()) {
                  listOf("\"${bytes.utf8().escape()}\"")
                } else {
                  bytes.readPackedVarint64().map { it.toString() }
                }
              }
            }
          }
        }

        return@flatMap nodeValues.map { Leaf(tagString, it) }.asSequence()
      }
      .toList()
      .let { Tree(it) }
}

private fun ByteString.readPackedVarint64(): List<Long> {
  val values = mutableListOf<Long>()

  var bytePos = 0

  // Adapted from ProtoReader#readVarint64().
  infix fun Byte.and(other: Int): Int = toInt() and other
  fun readNextPackedValue(): Long? {
    var shift = 0
    var result: Long = 0
    while (shift < 64 && bytePos < size) {
      val b = this[bytePos++]
      result = result or ((b and 0x7F).toLong() shl shift)
      if (b and 0x80 == 0) {
        return result
      }
      shift += 7
    }

    return null
  }

  while (true) {
    values += readNextPackedValue() ?: break
  }

  return values
}

private fun ByteString.isProbablyUtf8(): Boolean {
  val byteBuffer = Buffer().write(this)
  while (!byteBuffer.exhausted()) {
    val codePoint = byteBuffer.readUtf8CodePoint()
    if (codePoint.isISOControl() && !codePoint.isWhitespace()) {
      return false
    }
  }

  return true
}

private fun Int.isISOControl(): Boolean {
  return (this in 0x00..0x1F) or (this in 0x7F..0x9F)
}

private const val whitespaceChars = "" +
    "\u0020" +
    "\u1680" +
    "\u1680" +
    "\u2000" +
    "\u2001" +
    "\u2002" +
    "\u2003" +
    "\u2004" +
    "\u2005" +
    "\u2006" +
    "\u2008" +
    "\u2009" +
    "\u200A" +
    "\u3000" +
    "\u2028" +
    "\u2029" +
    "\t" +
    "\n" +
    "\u000B" +
    "\r" +
    "\u001C" +
    "\u001D" +
    "\u001E" +
    "\u001F"

private fun Int.isWhitespace() = this.toChar() in whitespaceChars

private fun String.escape(): String {
  return replace("\n", "\\n")
}
