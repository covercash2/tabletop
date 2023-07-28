/* (C)2023 */
package tabletop.io

import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.FileMetadata
import okio.FileNotFoundException
import okio.FileSystem
import okio.IOException
import okio.buffer
import okio.use
import tabletop.result.Err
import tabletop.result.Ok
import tabletop.result.Result
import tabletop.result.andThen
import tabletop.result.falseErr
import tabletop.result.map
import tabletop.result.mapErr
import tabletop.result.partitionErrors

typealias FileResult<T> = Result<T, FileError>

expect fun fileIo(): FileIo

class FileIo(
    val fileSystem: FileSystem,
    val serializer: StringFormat = Json,
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
        if (exists) {
            Ok(File(path.okioPath))
        } else {
            Err(FileError.FileNotFound(path))
        }
    }

fun FileSystem.tryCreateDirs(path: RawPath): FileResult<Directory> =
    catchIoError(path) {
        createDirectories(path.okioPath, mustCreate = true)
    }.andThen {
        list(path)
    }

fun FileSystem.tryMetadata(path: Path): FileResult<FileMetadata> =
    catchIoError(path) {
        metadata(it)
    }

fun FileSystem.typePath(path: RawPath): FileResult<Path> =
    tryMetadata(path)
        .map {
            when {
                it.isDirectory -> DirPath(path.okioPath)
                it.isRegularFile -> File(path.okioPath)
                else -> path
            }
        }

fun FileSystem.list(path: RawPath): FileResult<Directory> =
    catchIoError(path) {
        list(it)
    }.andThen { paths: List<okio.Path> ->
        val (contents, errors) = paths.asSequence().map { okPath: okio.Path ->
            typePath(RawPath(okPath))
        }.partitionErrors()

        errors.forEach {
            println("file error: $it")
        }

        Ok(
            Directory(
                okioPath = path.okioPath,
                contents = contents.toList(),
            ),
        )
    }

fun FileIo.tryDir(path: Path, create: Boolean = false): FileResult<Directory> {
    val metadata = fileSystem.catchIoError(path) {
        metadata(it)
    }
    return when (metadata) {
        is Ok -> {
            when {
                metadata.value.isDirectory -> fileSystem.list(path.asRaw())
                else -> Err(FileError.ExpectedDir(path))
            }
        }

        is Err -> {
            when {
                create -> fileSystem.tryCreateDirs(path.asRaw())
                else -> Err(metadata.error)
            }
        }
    }
}

fun FileIo.writeString(contents: String, path: Path): Result<File, FileError> =
    useSink(path) {
        writeUtf8(contents)
    }.andThen { tryFile(path) }

inline fun <reified T> FileIo.writeSerial(
    data: T,
    path: Path,
): FileResult<File> =
    serializer.encode(data)
        .mapErr { FileError.Serialization(it) as FileError }
        .andThen { content ->
            writeString(content, path)
        }

fun FileIo.tryTomlFile(path: Path): FileResult<File> =
    tryFile(path)
        .andThen {
            it.tryExtension()
        }.andThen { extension ->
            (extension == "toml").falseErr(
                FileError.WrongExtension(
                    got = extension,
                    expected = "toml",
                ),
            )
        }.map {
            File(path.okioPath)
        }

fun FileSystem.loadString(path: Path): FileResult<String> =
    catchIoError(path) {
        source(it).buffer().use { source ->
            source.readUtf8()
        }
    }

fun FileIo.loadString(file: File): FileResult<String> =
    fileSystem.loadString(file)

inline fun <reified T> FileIo.loadSerial(
    file: File,
): FileResult<T> =
    fileSystem.loadString(file)
        .andThen { content ->
            serializer.decode<T>(content)
                .mapErr { FileError.Serialization(it) }
        }

sealed interface FileError {
    data class FileNotFound(val path: Path) : FileError
    data class OkioException(val exception: IOException) : FileError
    data class Serialization(val error: SerializationError) : FileError
    data class WrongExtension(val got: String, val expected: String) : FileError
    data class NoExtension(val path: Path) : FileError
    data class FoundDir(val path: Path) : FileError
    data class ExpectedDir(val path: Path) : FileError
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
