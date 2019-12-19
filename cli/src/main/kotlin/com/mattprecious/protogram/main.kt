@file:JvmName("Main")

package com.mattprecious.protogram

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import okio.ByteString.Companion.decodeHex
import java.io.PrintStream

fun main(vararg args: String) {
  ProtogramCli(System.out).main(args.toList())
}

private class ProtogramCli(
  private val out: PrintStream
) : CliktCommand(name = "protogram") {

  private val bytes by argument("hex", "Encoded proto bytes as hex")
      .convert { it.decodeHex() }

  override fun run() {
    out.println(printProto(bytes))
  }
}
