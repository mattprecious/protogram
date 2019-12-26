@file:JvmName("Main")

package com.mattprecious.protogram

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.arguments.optional
import java.io.InputStream
import java.io.PrintStream
import okio.ByteString.Companion.decodeHex
import okio.buffer
import okio.source

fun main(vararg args: String) {
  ProtogramCli(System.`in`, System.out, System.err).main(args.toList())
}

internal class ProtogramCli(
  private val stdin: InputStream,
  private val stdout: PrintStream,
  private val stderr: PrintStream
) : CliktCommand(name = "protogram") {

  private val bytes by argument("hex", "Encoded proto bytes as hex")
      .convert { it.decodeHex() }
      .optional()

  override fun run() {
    if (bytes == null && stdin.available() == 0) {
      stderr.println("Error: Attempted to read bytes from stdin but was empty.\n")
      throw PrintHelpMessage(this)
    }
    val decodeBytes = bytes ?: stdin.source().buffer().readByteString()
    stdout.print(printProto(decodeBytes))
  }
}
