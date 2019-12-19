@file:JvmName("Main")

package com.mattprecious.protogram

import okio.ByteString.Companion.decodeHex

fun main(vararg args: String) {
  require(args.size == 1) { "Only one arg supported" }
  val bytes = args.single().decodeHex()
  println(printProto(bytes))
}
