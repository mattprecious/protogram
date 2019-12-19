package com.mattprecious.tinsel

import com.mattprecious.tinsel.Node.InternalNode
import com.mattprecious.tinsel.Node.LeafNode
import org.junit.Assert.assertEquals
import org.junit.Test

class TinselTest {
  @Test fun empty() {
    val actual = Tinsel.render(emptyList())
    val expected = ""
    assertEquals(expected, actual)
  }

  @Test fun `crazy nesting`() {
    val recursiveTree = buildRecursiveTree(label = "2", leafValue = "(empty)", depth = 64)
    val actual = Tinsel.render(listOf(recursiveTree, LeafNode("1", "456")))
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

  @Test fun `test the recursive tree builder`() {
    var actual = buildRecursiveTree(label = "2", leafValue = "v", depth = 1)
    var expected: Node = LeafNode("2", "v")
    assertEquals(expected, actual)

    actual = buildRecursiveTree("2", "v", 2)
    expected = InternalNode("2", listOf(LeafNode("2", "v")))
    assertEquals(expected, actual)

    actual = buildRecursiveTree(label = "2", leafValue = "v", depth = 5)
    expected = InternalNode("2", listOf(InternalNode("2", listOf(InternalNode("2", listOf(InternalNode("2", listOf(LeafNode("2", "v")))))))))
    assertEquals(expected, actual)
  }
}

internal fun buildRecursiveTree(
  label: String,
  leafValue: String,
  depth: Int
): Node {
  return if (depth == 1) {
    LeafNode(label, leafValue)
  } else {
    InternalNode(label, listOf(buildRecursiveTree(label, leafValue, depth - 1)))
  }
}