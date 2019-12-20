package com.mattprecious.protogram.test

import com.mattprecious.tinsel.Node
import com.mattprecious.tinsel.Node.Branch
import com.mattprecious.tinsel.Node.Leaf
import kotlin.test.Test
import kotlin.test.assertEquals

class RecursionTest {
  @Test fun recursiveTreeBuilder() {
    var actual = buildRecursiveTree(label = "2", leafValue = "v", depth = 1)
    var expected: Node = Leaf("2", "v")
    assertEquals(expected, actual)

    actual = buildRecursiveTree("2", "v", 2)
    expected = Branch("2", listOf(Leaf("2", "v")))
    assertEquals(expected, actual)

    actual = buildRecursiveTree(label = "2", leafValue = "v", depth = 5)
    expected = Branch("2", listOf(Branch("2", listOf(Branch("2", listOf(Branch("2", listOf(Leaf("2", "v")))))))))
    assertEquals(expected, actual)
  }
}
