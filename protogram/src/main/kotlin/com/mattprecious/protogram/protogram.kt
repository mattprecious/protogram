@file:JvmName("Protogram")
package com.mattprecious.protogram

import com.mattprecious.tinsel.Node
import com.mattprecious.tinsel.Node.InternalNode
import com.mattprecious.tinsel.Node.LeafNode
import com.mattprecious.tinsel.Tinsel
import com.squareup.wire.FieldEncoding.*
import com.squareup.wire.ProtoReader
import okio.Buffer
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import java.lang.Exception

fun main(vararg args: String) {
  require(args.size == 1) { "Only one arg supported" }
  val bytes = args.single().decodeHex()
  println(printProto(bytes))
}

fun printProto(bytes: ByteString): String {
  return Tinsel.render(bytes.readProtoNodes())
}

private fun ProtoReader.readNodes(): List<Node> {
  return generateFieldSequence()
    .map { (tag, encoding) ->
      val tagString = tag.toString()

      val nodeValue = when (encoding) {
        VARINT -> readVarint64().toString()
        FIXED64 -> readFixed64().toString()
        FIXED32 -> readFixed32().toString()
        LENGTH_DELIMITED -> {
          val bytes = readBytes()

          if (bytes.size == 0) {
            "(empty)"
          } else {
            try {
              return@map InternalNode(tagString, bytes.readProtoNodes())
            } catch (_: Exception) {
              "\"${bytes.utf8().escape()}\""
            }
          }
        }
      }

      return@map LeafNode(tagString, nodeValue)
    }.toList()
}

private fun String.escape(): String {
  return replace("\n", "\\n")
}

internal fun ByteString.readProtoNodes(): List<Node> {
  return ProtoReader(Buffer().write(this)).readNodes()
}
