/* (C)2023 */
package tabletop.io

import okio.FileSystem

actual fun fileIo() = FileIo(fileSystem = FileSystem.SYSTEM)
