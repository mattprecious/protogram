package com.mattprecious.protogram.web

import com.mattprecious.protogram.printProto
import okio.Buffer
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.files.Blob
import org.w3c.files.File
import org.w3c.files.FileList
import kotlin.browser.document
import kotlin.js.Promise

fun main() {
  val input = document.getElementById("input") as HTMLTextAreaElement
  val file = document.getElementById("file") as HTMLInputElement
  val output = document.getElementById("output") as HTMLTextAreaElement
  val convert = document.getElementById("convert") as HTMLButtonElement

  file.addEventListener("change", callback = {
    val files = it.target.asDynamic().files as FileList
    files.firstOrNull()
        ?.arrayBuffer()
        ?.then {
          input.value = it.toByteString().hex()
          convert.click()
          file.asDynamic().value = null
        }
  })

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

private fun FileList.firstOrNull(): File? {
  return if (length > 0) item(0) else null
}

private fun Blob.arrayBuffer() = asDynamic().arrayBuffer() as Promise<ArrayBuffer>

private fun ArrayBuffer.toByteString(): ByteString {
  val uint8Array = Uint8Array(this)
  val buffer = Buffer()
  for (i in 0 until uint8Array.length) {
    buffer.writeByte(uint8Array[i].toInt())
  }
  return buffer.readByteString()
}
