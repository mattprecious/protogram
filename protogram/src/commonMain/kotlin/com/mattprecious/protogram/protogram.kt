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
import com.squareup.wire.schema.EnumType
import com.squareup.wire.schema.MessageType
import com.squareup.wire.schema.ProtoType
import com.squareup.wire.schema.Schema
import kotlin.jvm.JvmName
import okio.Buffer
import okio.ByteString

fun printProto(bytes: ByteString, schema: Schema? = null, type: ProtoType? = null): String {
  return bytes.readProtoTree(schema, type).render()
}

internal fun ByteString.readProtoTree(schema: Schema? = null, type: ProtoType? = null): Tree {
  return ProtoReader(Buffer().write(this)).readTree(schema, type)
}

private fun ProtoReader.readTree(schema: Schema?, type: ProtoType?): Tree {
  return generateFieldSequence()
      .flatMap { (tag, encoding) ->
        var fieldType: ProtoType? = null
        var fieldName = tag.toString()
        if (schema != null && type != null) {
          when (val schemaType = schema.getType(type)) {
            is MessageType -> {
              val field = schemaType.field(tag)
              if (field != null) {
                fieldType = field.type
                fieldName = field.name
              }
            }
            is EnumType -> {
              val constant = schemaType.constant(tag)
              if (constant != null) {
                fieldName = constant.name
              }
            }
          }
        }

        // TODO use fieldType (if present) to aid in parsing and display of value.
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
                val nestedTree = bytes.readProtoTree(schema, fieldType)
                return@flatMap listOf(Branch(fieldName, nestedTree.nodes)).asSequence()
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

        return@flatMap nodeValues.map { Leaf(fieldName, it) }.asSequence()
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
    if (codePoint.isLikelyBinary()) {
      return false
    }
  }

  return true
}

private fun Int.isLikelyBinary(): Boolean {
  return when (this) {
    0xFFFD -> true // Replacement code point.
    ' '.toInt(), '\t'.toInt(), '\r'.toInt(), '\n'.toInt() -> false
    else -> (this in 0x00..0x1F) or (this in 0x7F..0x9F)
  }
}

private fun String.escape(): String {
  return replace("\n", "\\n")
}
