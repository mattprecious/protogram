package com.mattprecious.tinsel

import com.mattprecious.protogram.test.buildRecursiveTree
import com.mattprecious.tinsel.Node.Leaf
import kotlin.test.Test
import kotlin.test.assertEquals

class TinselTest {
  @Test fun empty() {
    val actual = Tree().render()
    val expected = ""
    assertEquals(expected, actual)
  }

  @Test fun crazyNesting() {
    val recursiveTree = buildRecursiveTree(label = "2", leafValue = "(empty)", depth = 64)
    val actual = Tree(listOf(recursiveTree, Leaf("1", "456"))).render()
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
