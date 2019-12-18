package com.mattprecious.tinsel

// TODO: Pull out into its own lib.
internal object Tinsel {
  fun render(nodes: List<Node>): String {
    if (nodes.isEmpty()) return ""
    return buildString { appendNodes(nodes) }
  }

  private fun StringBuilder.appendNodes(nodes: List<Node>, indent: String = "") {
    val tagWidth = nodes.map { it.label.length }.max()!!

    nodes.forEachIndexed { index, node ->
      val indented = indent != ""
      val isFirst = index == 0
      val isLast = index == nodes.size - 1

      append(indent)
      append(
        when {
          isFirst && isLast && !indented -> "──"
          isFirst && !indented -> "┌─"
          isLast -> "╰-"
          else -> "├─"
        }
      )

      val paddedLabel = node.label.padStart(tagWidth + 1)
      if (node is Node.InternalNode) {
        append("$paddedLabel ┐\n")

        val nextIndentPrefix = if (!isLast) '│' else ' '
        val paddedNextIndent = nextIndentPrefix.toString().padEnd(4 + tagWidth)

        appendNodes(node.children, indent = "$indent$paddedNextIndent")
      } else if (node is Node.LeafNode) {
        append("$paddedLabel: ${node.value}\n")
      }
    }
  }
}