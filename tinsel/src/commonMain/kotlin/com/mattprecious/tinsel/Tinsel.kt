package com.mattprecious.tinsel

object Tinsel {
  fun render(tree: Tree): String {
    if (tree.nodes.isEmpty()) return ""
    return buildString { appendNodes(tree.nodes) }
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
      if (node is Node.Branch) {
        append("$paddedLabel ┐\n")

        val nextIndentPrefix = if (!isLast) '│' else ' '
        val paddedNextIndent = nextIndentPrefix.toString().padEnd(4 + tagWidth)

        appendNodes(node.children, indent = "$indent$paddedNextIndent")
      } else if (node is Node.Leaf) {
        append("$paddedLabel: ${node.value}\n")
      }
    }
  }
}
