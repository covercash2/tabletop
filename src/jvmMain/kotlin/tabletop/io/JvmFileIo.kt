package tabletop.io

import tabletop.io.FileIo
import okio.FileSystem

actual fun fileIo() = FileIo(fileSystem = FileSystem.SYSTEM)