package com.mattprecious.protogram

import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.core.UsageError
import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import java.io.PrintStream
import okio.Buffer
import okio.ByteString.Companion.decodeHex
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.fail
import org.junit.Test

class ProtogramCliTest {
  private val fs = Jimfs.newFileSystem(Configuration.unix())
  private val fsRoot = fs.rootDirectories.first()
  private val stdin = Buffer()
  private val stdout = Buffer()
  private val stderr = Buffer()

  private val command = ProtogramCli(
      fs,
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

  @Test fun hexWithProto() {
    fsRoot.resolve("test.proto").writeText("""
      message Foo {
        required int32 id = 1;
        required Bar bar = 2;
      }
      message Bar {
        required int32 id = 2;
        optional int32 age = 3;
      }
    """)

    command.parse(listOf("--source", fsRoot.toString(), "--type", "Foo", "0804120610e80718c806"))

    val expected = """
      |┌─  id: 4
      |╰- bar ┐
      |       ├─  id: 1000
      |       ╰- age: 840
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

  @Test fun typeNoSources() {
    try {
      command.parse(listOf("--type", "Foo", "0804120610e80718c806"))
      fail()
    } catch (e: UsageError) {
      assertEquals("At least one --source is required with --type", e.message)
      assertNotEquals(0, e.statusCode)
    }
  }
}
