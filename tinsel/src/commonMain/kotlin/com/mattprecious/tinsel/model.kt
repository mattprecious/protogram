package com.mattprecious.tinsel

data class Tree(val nodes: List<Node> = emptyList())

sealed class Node {
  abstract val label: String

  data class Branch(
    override val label: String,
    val children: List<Node>
  ) : Node()

  data class Leaf(
    override val label: String,
    val value: String
  ) : Node()
}
