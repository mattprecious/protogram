@file:JvmName("Main")

package com.mattprecious.protogram

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.cooccurring
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.path
import com.squareup.wire.schema.ProtoType
import com.squareup.wire.schema.Schema
import com.squareup.wire.schema.SchemaLoader
import java.io.InputStream
import java.io.PrintStream
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import okio.ByteString.Companion.decodeHex
import okio.buffer
import okio.source

fun main(vararg args: String) {
  ProtogramCli(FileSystems.getDefault(), System.`in`, System.out, System.err).main(args.toList())
}

internal class ProtogramCli(
  fs: FileSystem,
  private val stdin: InputStream,
  private val stdout: PrintStream,
  private val stderr: PrintStream
) : CliktCommand(name = "protogram") {

  private class ProtoOptions(fs: FileSystem) : OptionGroup("Protos") {
    val dirs by option("--source", help = "Directory of proto files")
        .path(exists = true, fileOkay = false, readable = true, fileSystem = fs)
        .multiple()

    val type by option("--type", help = "Message or enum qualified type").required()
  }

  private val protoOptions by ProtoOptions(fs).cooccurring()

  private val bytes by argument("hex", "Encoded proto bytes as hex")
      .convert { it.decodeHex() }
      .optional()

  override fun run() {
    if (bytes == null && stdin.available() == 0) {
      stderr.println("Error: Attempted to read bytes from stdin but was empty.\n")
      throw PrintHelpMessage(this)
    }
    val decodeBytes = bytes ?: stdin.source().buffer().readByteString()

    var schema: Schema? = null
    var type: ProtoType? = null
    protoOptions?.let { protoOptions ->
      if (protoOptions.dirs.isEmpty()) {
        throw UsageError("At least one --source is required with --type")
      }
      val schemaLoader = SchemaLoader()
      protoOptions.dirs.forEach {
        schemaLoader.addSource(it)
      }
      schema = schemaLoader.load()
      type = schema!!.getType(protoOptions.type)?.type
    }

    stdout.print(printProto(decodeBytes, schema, type))
  }
}
