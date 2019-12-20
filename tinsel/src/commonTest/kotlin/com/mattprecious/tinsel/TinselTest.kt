package com.mattprecious.tinsel

import com.mattprecious.protogram.test.buildRecursiveTree
import kotlin.test.Test
import kotlin.test.assertEquals

class TinselTest {
  @Test fun empty() {
    val actual = tree {}.render()
    val expected = ""
    assertEquals(expected, actual)
  }

  @Test fun crazyNesting() {
    val recursiveTree = buildRecursiveTree(label = "2", leafValue = "(empty)", depth = 63)
    val actual = tree {
      "2" to recursiveTree
      "1" to "456"
    }.render()
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
