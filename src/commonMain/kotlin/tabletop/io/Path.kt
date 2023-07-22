package tabletop.io

import okio.Path.Companion.toPath
import tabletop.result.*
import kotlin.jvm.JvmInline

sealed interface Path {
    val okioPath: okio.Path
}

@JvmInline
value class Directory internal constructor(
    override val okioPath: okio.Path,
) : Path

@JvmInline
value class File internal constructor(
    override val okioPath: okio.Path,
) : Path


@JvmInline
value class TomlFile internal constructor(
    override val okioPath: okio.Path,
): Path {
    init {
        require(okioPath.extension.getOrThrow() == "toml")
    }
}

@JvmInline
value class RawPath(
    override val okioPath: okio.Path,
) : Path

fun String.path(): Path = RawPath(this.toPath())

val Path.extension: Result<String, FileError> get() = when (this) {
    is Directory -> Err(FileError.FoundDir(this))
    is File -> okioPath.extension
    is RawPath -> okioPath.extension
    is TomlFile -> Ok("toml")
}

val okio.Path.extension: Result<String, FileError> get() {
    val result = segments.last().substringAfterLast(".")

    return if (result.isBlank())
        Err(FileError.NoExtension(RawPath(this)))
    else
        Ok(result)
}