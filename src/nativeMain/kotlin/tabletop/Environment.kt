/* (C)2023 */
package tabletop

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import okio.FileSystem
import okio.Path.Companion.toPath
import platform.posix.getenv
import tabletop.io.DirPath

@OptIn(ExperimentalForeignApi::class)
actual fun FileSystem.getHome(): DirPath {
    val homeString: String? = getenv("HOME")?.toKString()
    return DirPath(homeString?.toPath() ?: "./".toPath())
}
