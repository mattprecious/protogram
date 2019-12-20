package com.mattprecious.protogram

import com.mattprecious.protogram.test.buildRecursiveTree
import com.mattprecious.tinsel.Node.Branch
import com.mattprecious.tinsel.Node.Leaf
import com.mattprecious.tinsel.Tree
import okio.ByteString.Companion.decodeHex
import kotlin.test.Test
import kotlin.test.assertEquals

class ProtogramTest {
  @Test fun empty() {
    val actual = "".decodeHex().readProtoTree()
    val expected = Tree()
    assertEquals(expected, actual)
  }

  @Test fun simple() {
    val actual = "0a0344616e120911000000000000e03f70107a021001".decodeHex().readProtoTree()
    val expected = Tree(
        listOf(
          Leaf("1", "\"Dan\""),
          Branch("2", listOf(
              Leaf("2", "4602678819172646912 (0.5)")
          )),
          Leaf("14", "16"),
          Branch("15", listOf(
              Leaf("2", "1")
          ))
        )
    )
    assertEquals(expected, actual)
  }

  @Test fun crazyNesting() {
    val actual = "127e127c127a12781276127412721270126e126c126a12681266126412621260125e125c125a12581256125412521250124e124c124a12481246124412421240123e123c123a12381236123412321230122e122c122a12281226122412221220121e121c121a12181216121412121210120e120c120a1208120612041202120008c803".decodeHex().readProtoTree()

    val recursiveTree = buildRecursiveTree(label = "2", leafValue = "(empty)", depth = 64)
    val expected = Tree(listOf(recursiveTree, Leaf("1", "456")))

    assertEquals(expected, actual)
  }
}
