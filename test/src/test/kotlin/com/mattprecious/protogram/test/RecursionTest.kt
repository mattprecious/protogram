package com.mattprecious.protogram.test

import buildRecursiveTree
import com.mattprecious.tinsel.Node
import com.mattprecious.tinsel.Node.InternalNode
import com.mattprecious.tinsel.Node.LeafNode
import org.junit.Assert.assertEquals
import org.junit.Test

class RecursionTest {
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