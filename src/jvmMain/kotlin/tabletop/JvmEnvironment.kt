/* (C)2023 */
package tabletop

import okio.FileSystem
import tabletop.io.DirPath
import tabletop.io.path
import java.lang.System

actual fun FileSystem.getHome(): DirPath =
    DirPath((System.getenv("HOME")?.path() ?: "./".path()).okioPath)
