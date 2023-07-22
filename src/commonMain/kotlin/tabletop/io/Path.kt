/* (C)2023 */
package tabletop.io

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import okio.Path.Companion.toPath
import tabletop.result.Err
import tabletop.result.Ok
import tabletop.result.toResult
import kotlin.jvm.JvmInline

sealed interface Path {
    val okioPath: okio.Path
}

@Serializable
@JvmInline
value class DirPath internal constructor(
    @Serializable(with = OkioPathSerializer::class)
    override val okioPath: okio.Path,
) : Path

@Serializable
data class Directory internal constructor(
    @Serializable(with = OkioPathSerializer::class)
    override val okioPath: okio.Path,
    val contents: List<Path> = emptyList(),
) : Path {
    fun dirPath(): DirPath = DirPath(okioPath)
}

@Serializable
@JvmInline
value class File internal constructor(
    @Serializable(with = OkioPathSerializer::class)
    override val okioPath: okio.Path,
) : Path

@Serializable
@JvmInline
value class TomlFile internal constructor(
    @Serializable(with = OkioPathSerializer::class)
    override val okioPath: okio.Path,
) : Path {
    init {
        require(okioPath.extension == "toml")
    }
}

@Serializable
@JvmInline
value class RawPath(
    @Serializable(with = OkioPathSerializer::class)
    override val okioPath: okio.Path,
) : Path

fun String.path(): Path = RawPath(this.toPath())
fun Path.asRaw(): RawPath = RawPath(okioPath)

val Path.extension: String?
    get() = when (this) {
        is Directory -> null
        else -> okioPath.extension
    }

/**
 * Get the file extension or return an error if it does not exist
 */
fun Path.tryExtension(): FileResult<String> =
    when (this) {
        is Directory -> Err(FileError.FoundDir(this))
        is TomlFile -> Ok("toml")
        else -> {
            okioPath.extension.toResult(FileError.NoExtension(this))
        }
    }

val okio.Path.extension: String?
    get() =
        segments.lastOrNull()?.substringAfterLast(".")?.ifBlank { null }

class OkioPathSerializer : KSerializer<okio.Path> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Path", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): okio.Path =
        decoder.decodeString().toPath()

    override fun serialize(encoder: Encoder, value: okio.Path) =
        encoder.encodeString(value.name)
}
