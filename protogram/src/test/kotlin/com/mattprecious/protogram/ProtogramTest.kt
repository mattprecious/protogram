package com.mattprecious.protogram

import okio.ByteString.Companion.decodeHex
import org.junit.Assert.assertEquals
import org.junit.Test

class ProtogramTest {
  @Test fun empty() {
    val actual = printProto("".decodeHex())
    val expected = ""
    assertEquals(expected, actual)
  }

  @Test fun crazyNesting() {
    val actual = printProto("127e127c127a12781276127412721270126e126c126a12681266126412621260125e125c125a12581256125412521250124e124c124a12481246124412421240123e123c123a12381236123412321230122e122c122a12281226122412221220121e121c121a12181216121412121210120e120c120a1208120612041202120008c803".decodeHex())
    val expected = """
      |┌─ 2 ┐
      |│    ╰- 2 ┐
      |│         ╰- 2 ┐
      |│              ╰- 2 ┐
      |│                   ╰- 2 ┐
      |│                        ╰- 2 ┐
      |│                             ╰- 2 ┐
      |│                                  ╰- 2 ┐
      |│                                       ╰- 2 ┐
      |│                                            ╰- 2 ┐
      |│                                                 ╰- 2 ┐
      |│                                                      ╰- 2 ┐
      |│                                                           ╰- 2 ┐
      |│                                                                ╰- 2 ┐
      |│                                                                     ╰- 2 ┐
      |│                                                                          ╰- 2 ┐
      |│                                                                               ╰- 2 ┐
      |│                                                                                    ╰- 2 ┐
      |│                                                                                         ╰- 2 ┐
      |│                                                                                              ╰- 2 ┐
      |│                                                                                                   ╰- 2 ┐
      |│                                                                                                        ╰- 2 ┐
      |│                                                                                                             ╰- 2 ┐
      |│                                                                                                                  ╰- 2 ┐
      |│                                                                                                                       ╰- 2 ┐
      |│                                                                                                                            ╰- 2 ┐
      |│                                                                                                                                 ╰- 2 ┐
      |│                                                                                                                                      ╰- 2 ┐
      |│                                                                                                                                           ╰- 2 ┐
      |│                                                                                                                                                ╰- 2 ┐
      |│                                                                                                                                                     ╰- 2 ┐
      |│                                                                                                                                                          ╰- 2 ┐
      |│                                                                                                                                                               ╰- 2 ┐
      |│                                                                                                                                                                    ╰- 2 ┐
      |│                                                                                                                                                                         ╰- 2 ┐
      |│                                                                                                                                                                              ╰- 2 ┐
      |│                                                                                                                                                                                   ╰- 2 ┐
      |│                                                                                                                                                                                        ╰- 2 ┐
      |│                                                                                                                                                                                             ╰- 2 ┐
      |│                                                                                                                                                                                                  ╰- 2 ┐
      |│                                                                                                                                                                                                       ╰- 2 ┐
      |│                                                                                                                                                                                                            ╰- 2 ┐
      |│                                                                                                                                                                                                                 ╰- 2 ┐
      |│                                                                                                                                                                                                                      ╰- 2 ┐
      |│                                                                                                                                                                                                                           ╰- 2 ┐
      |│                                                                                                                                                                                                                                ╰- 2 ┐
      |│                                                                                                                                                                                                                                     ╰- 2 ┐
      |│                                                                                                                                                                                                                                          ╰- 2 ┐
      |│                                                                                                                                                                                                                                               ╰- 2 ┐
      |│                                                                                                                                                                                                                                                    ╰- 2 ┐
      |│                                                                                                                                                                                                                                                         ╰- 2 ┐
      |│                                                                                                                                                                                                                                                              ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                   ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                        ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                             ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                                  ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                                       ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                                            ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                                                 ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                                                      ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                                                           ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                                                                ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                                                                     ╰- 2 ┐
      |│                                                                                                                                                                                                                                                                                                                          ╰- 2: (empty)
      |╰- 1: 456
      |""".trimMargin()
    assertEquals(expected, actual)
  }
}
