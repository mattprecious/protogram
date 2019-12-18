import com.squareup.wire.FieldEncoding
import com.squareup.wire.ProtoReader

internal data class ProtoField(
  val tag: Int,
  val encoding: FieldEncoding
)

internal fun ProtoReader.generateFieldSequence(): Sequence<ProtoField> {
  beginMessage()
  return generateSequence {
    val tag = nextTag()
    return@generateSequence if (tag == -1) {
      null
    } else {
      ProtoField(tag, peekFieldEncoding()!!)
    }
  }
}