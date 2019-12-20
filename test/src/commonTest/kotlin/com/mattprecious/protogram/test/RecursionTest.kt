package com.mattprecious.protogram.test

import com.mattprecious.tinsel.tree
import kotlin.test.Test
import kotlin.test.assertEquals

class RecursionTest {
  @Test fun recursiveTreeBuilder() {
    var actual = buildRecursiveTree(label = "2", leafValue = "v", depth = 1)
    var expected = tree { "2" to "v" }
    assertEquals(expected, actual)

    actual = buildRecursiveTree(label = "2", leafValue = "v", depth = 2)
    expected = tree { "2" to tree { "2" to "v" } }
    assertEquals(expected, actual)

    actual = buildRecursiveTree(label = "2", leafValue = "v", depth = 5)
    expected = tree { "2" to tree { "2" to tree { "2" to tree { "2" to tree { "2" to "v" } } } } }
    assertEquals(expected, actual)
  }
}
