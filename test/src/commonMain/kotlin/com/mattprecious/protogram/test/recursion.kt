package com.mattprecious.protogram.test

import com.mattprecious.tinsel.Node
import com.mattprecious.tinsel.Node.Branch
import com.mattprecious.tinsel.Node.Leaf
import com.mattprecious.tinsel.Tree

fun buildRecursiveTree(
  label: String,
  leafValue: String,
  depth: Int
): Tree {
  return Tree(listOf(buildRecursiveNodes(label, leafValue, depth)))
}

private fun buildRecursiveNodes(
  label: String,
  leafValue: String,
  depth: Int
): Node {
  return if (depth == 1) {
    Leaf(label, leafValue)
  } else {
    Branch(label, listOf(buildRecursiveNodes(label, leafValue, depth - 1)))
  }
}
