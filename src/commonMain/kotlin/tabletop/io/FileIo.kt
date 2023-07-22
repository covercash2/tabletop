package tabletop.io

import com.akuleshov7.ktoml.Toml
import okio.*
import tabletop.result.*
import kotlin.jvm.JvmInline

typealias FileResult<T> = Result<T, FileError>

expect fun fileIo(): FileIo

class FileIo(
    val fileSystem: FileSystem,
)

fun FileIo.useSink(path: Path, writeOp: BufferedSink.() -> Unit): FileResult<Unit> {
    return fileSystem.catchIoError {
        write(path.okioPath) {
            writeOp()
        }
    }
}

fun FileIo.tryFile(path: Path): FileResult<File> =
    fileSystem.catchIoError(path) {
        exists(it)
    }.andThen { exists ->
        if (exists)
            Ok(File(path.okioPath))
        else
            Err(FileError.FileNotFound(path))
    }

fun FileIo.writeString(contents: String, path: Path): Result<File, FileError> =
    useSink(path) {
        writeUtf8(contents)
    }.andThen { tryFile(path) }

inline fun <reified T> FileIo.writeToml(
    data: T,
    path: Path,
    toml: Toml = Toml,
): FileResult<TomlFile> =
    toml.tryEncodeString(data)
        .mapErr { FileError.Toml(it) as FileError }
        .andThen { content ->
            writeString(content, path)
        }
        .andThen {
            tryTomlFile(it)
        }

fun FileIo.tryTomlFile(path: Path): FileResult<TomlFile> =
    tryFile(path)
        .andThen {
            it.extension
        }.andThen { extension ->
            if (extension == "toml") {
                Ok(TomlFile(path.okioPath))
            } else {
                Err(FileError.WrongExtension(
                    got = extension,
                    expected = "toml",
                ))
            }
        }


sealed interface FileError {
    data class FileNotFound(val path: Path) : FileError
    data class OkioException(val exception: IOException) : FileError
    data class Toml(val error: TomlError) : FileError
    data class WrongExtension(val got: String, val expected: String) : FileError
    data class NoExtension(val path: Path) : FileError
    data class FoundDir(val path: Path) : FileError
}

internal fun <T> FileSystem.catchIoError(fn: FileSystem.() -> T): FileResult<T> {
    return try {
        Ok(fn())
    } catch (e: IOException) {
        Err(FileError.OkioException(e))
    }
}

internal fun <T> FileSystem.catchIoError(path: Path, fn: FileSystem.(okio.Path) -> T): FileResult<T> {
    return try {
        Ok(fn(path.okioPath))
    } catch (e: FileNotFoundException) {
        Err(FileError.FileNotFound(path))
    } catch (e: IOException) {
        Err(FileError.OkioException(e))
    }
}
