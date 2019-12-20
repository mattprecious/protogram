package com.mattprecious.protogram.test

import com.mattprecious.tinsel.Node
import com.mattprecious.tinsel.Node.Branch
import com.mattprecious.tinsel.Node.Leaf

fun buildRecursiveTree(
  label: String,
  leafValue: String,
  depth: Int
): Node {
  return if (depth == 1) {
    Leaf(label, leafValue)
  } else {
    Branch(label, listOf(buildRecursiveTree(label, leafValue, depth - 1)))
  }
}
