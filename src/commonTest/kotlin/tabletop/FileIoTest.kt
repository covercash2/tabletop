/* (C)2023 */
package tabletop

import kotlinx.serialization.Serializable
import okio.fakefilesystem.FakeFileSystem
import tabletop.io.FileIo
import tabletop.io.Path
import tabletop.io.path
import tabletop.io.tryExtension
import tabletop.result.errOrThrow
import tabletop.result.getOrThrow
import kotlin.jvm.JvmInline
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FileIoTest {
    private lateinit var fileIo: FileIo

    @BeforeTest
    fun setupFileIo() {
        fileIo = FileIo(FakeFileSystem())
    }

    @Test
    fun `TOML file extension`() {
        val goodFileName: Path = "file.toml".path()
        val goodResult = goodFileName.tryExtension().getOrThrow()

        assertEquals("toml", goodResult)

        val emptyFileName: Path = "".path()
        val emptyResult = emptyFileName.tryExtension().errOrThrow()

        val longFileName = "/root/path/to/file.txt".path()
        val longResult = longFileName.tryExtension().getOrThrow()

        assertEquals("txt", longResult)
    }
}

@Serializable
@JvmInline
value class Num(val int: Int)

@Serializable
data class Nums(
    val num1: Num,
    val num2: Num,
)
