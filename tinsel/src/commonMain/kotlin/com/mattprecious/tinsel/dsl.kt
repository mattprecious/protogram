@file:JvmName("-DslKt")

package com.mattprecious.tinsel

import com.mattprecious.tinsel.Node.Branch
import com.mattprecious.tinsel.Node.Leaf
import kotlin.jvm.JvmName

@DslMarker
private annotation class TinselDsl

fun tree(content: TreeDsl.() -> Unit) = TreeDslImpl().apply(content).create()

@TinselDsl
interface TreeDsl : BranchDsl {
  fun branch(
    label: String,
    content: BranchDsl.() -> Unit
  )

  infix fun String.to(tree: Tree)
}

@TinselDsl
interface BranchDsl {
  fun leaf(
    label: String,
    value: String
  )

  infix fun String.to(value: String)
}

private class TreeDslImpl : BranchDslImpl(), TreeDsl {
  override fun branch(
    label: String,
    content: BranchDsl.() -> Unit
  ) {
    nodes += Branch(label, BranchDslImpl().apply(content).nodes)
  }

  override fun String.to(tree: Tree) {
    nodes += Branch(this, tree.nodes)
  }

  fun create(): Tree {
    return Tree(nodes)
  }
}

private open class BranchDslImpl : BranchDsl {
  val nodes = mutableListOf<Node>()

  override fun leaf(
    label: String,
    value: String
  ) {
    nodes += Leaf(label, value)
  }

  override fun String.to(value: String) {
    leaf(this, value)
  }
}
