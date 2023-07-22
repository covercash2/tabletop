package tabletop.io

import okio.FileSystem

actual fun fileIo() = FileIo(FileSystem.SYSTEM)