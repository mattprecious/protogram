package com.mattprecious.protogram

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Path

fun Path.writeText(text: String, charset: Charset = UTF_8, vararg options: OpenOption) {
  Files.write(this, text.toByteArray(charset), *options)
}
