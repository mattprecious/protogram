package com.mattprecious.protogram

import com.github.ajalt.clikt.core.PrintHelpMessage
import java.io.PrintStream
import okio.Buffer
import okio.ByteString.Companion.decodeHex
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class ProtogramCliTest {
  private val stdin = Buffer()
  private val stdout = Buffer()
  private val stderr = Buffer()

  private val command = ProtogramCli(
      stdin.inputStream(),
      PrintStream(stdout.outputStream()),
      PrintStream(stderr.outputStream())
  )

  @Test fun hex() {
    command.parse(listOf("0804120610e80718c806"))

    val expected = """
      |┌─ 1: 4
      |╰- 2 ┐
      |     ├─ 2: 1000
      |     ╰- 3: 840
      |""".trimMargin()
    assertEquals(expected, stdout.readUtf8())
  }

  @Test fun stdinBytes() {
    stdin.write("0804120610e80718c806".decodeHex())
    command.parse(listOf())

    val expected = """
      |┌─ 1: 4
      |╰- 2 ┐
      |     ├─ 2: 1000
      |     ╰- 3: 840
      |""".trimMargin()
    assertEquals(expected, stdout.readUtf8())
  }

  @Test fun stdinEmpty() {
    try {
      command.parse(listOf())
      fail()
    } catch (_: PrintHelpMessage) {
    }

    assertEquals("Error: Attempted to read bytes from stdin but was empty.", stderr.readUtf8Line())
  }
}
