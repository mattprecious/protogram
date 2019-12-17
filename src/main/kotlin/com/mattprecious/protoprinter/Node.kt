package com.mattprecious.protoprinter

internal sealed class Node {
  abstract val label: String

  data class InternalNode(
    override val label: String,
    val children: List<Node>
  ) : Node()

  data class LeafNode(
    override val label: String,
    val value: String
  ) : Node()
}