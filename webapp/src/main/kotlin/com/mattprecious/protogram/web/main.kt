package com.mattprecious.protogram.web

import com.mattprecious.protogram.printProto
import kotlinx.coroutines.await
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okio.Buffer
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget
import org.w3c.files.Blob
import org.w3c.files.File
import org.w3c.files.FileList
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Promise

suspend fun main() = coroutineScope<Unit> {
  val input = document.getElementById("input") as HTMLTextAreaElement
  val file = document.getElementById("file") as HTMLInputElement
  val output = document.getElementById("output") as HTMLTextAreaElement

  fun setInput(value: String) {
    input.value = value
    // Setting the value programmatically does not fire the 'input' event.
    input.dispatchEvent(Event("input"))
  }

  fun renderHex(value: String) {
    val tree = try {
      printProto(value.decodeHex())
    } catch (e: Exception) {
      console.error(e)
      "Unable to decode hex\n\nError: ${e::class.simpleName} ${e.message ?: ""}"
    }
    output.value = tree
  }

  launch {
    file.events("change")
        .map {
          val inputFiles = it.target.asDynamic().files as FileList
          inputFiles.firstOrNull()
              ?.arrayBuffer()
              ?.await()
              ?.toByteString()
              ?.hex() ?: ""
        }
        .collect {
          // Clear input file. We use dynamic because the Kotlin type is not nullable.
          file.asDynamic().value = null
          setInput(it)
        }
  }

  launch {
    input.events("input")
        .map { input.value.trim() }
        .collect {
          window.history.replaceState(it, "", "?$it")
          renderHex(it)
        }
  }

  val defaultHex = window.location.search.trimStart('?')
  // We directly set the input and render to avoid replacing the initial history state.
  input.value = defaultHex
  renderHex(defaultHex)
}

private fun EventTarget.events(event: String) = callbackFlow {
  val listener: (Event) -> Unit = {
    offer(it)
  }
  addEventListener(event, listener)
  awaitClose {
    removeEventListener(event, listener)
  }
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
