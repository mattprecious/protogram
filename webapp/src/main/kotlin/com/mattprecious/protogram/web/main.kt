package com.mattprecious.protogram.web

import com.mattprecious.protogram.printProto
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLTextAreaElement
import kotlin.browser.document
import okio.ByteString.Companion.decodeHex

fun main() {
  val input = document.getElementById("input") as HTMLTextAreaElement
  val output = document.getElementById("output") as HTMLTextAreaElement
  val convert = document.getElementById("convert") as HTMLButtonElement

  convert.removeAttribute("disabled")
  convert.addEventListener("click", callback = {
    val hex = input.value.trim()
    val tree = try {
      printProto(hex.decodeHex())
    } catch (e: Exception) {
      console.error(e)
      "Unable to decode hex\n\nError: ${e::class.simpleName} ${e.message ?: ""}"
    }
    output.value = tree
  })
}
