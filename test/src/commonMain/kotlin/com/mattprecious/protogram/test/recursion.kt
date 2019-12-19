package com.mattprecious.protogram.test

import com.mattprecious.tinsel.Node
import com.mattprecious.tinsel.Node.InternalNode
import com.mattprecious.tinsel.Node.LeafNode

fun buildRecursiveTree(
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
