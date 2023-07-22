package tabletop.io

import com.akuleshov7.ktoml.Toml
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import tabletop.StatBlock
import tabletop.result.errOrThrow
import tabletop.result.getOrThrow
import kotlin.io.path.Path
import kotlin.io.path.extension
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
        val goodFileName: okio.Path = "file.toml".toPath()
        val goodResult = goodFileName.extension.getOrThrow()

        assertEquals("toml", goodResult)

        val emptyFileName: okio.Path = "".toPath()
        val emptyResult = emptyFileName.extension.errOrThrow()

        val longFileName = "/root/path/to/file.txt".toPath()
        val longResult = longFileName.extension.getOrThrow()

        assertEquals("txt", longResult)
    }

    @Test
    fun `save TOML file`() {
        val tomlPath: Path = "file.toml".path()
        val data = StatBlock(
            maxHitPoints = 10u,
            armorClass = 10u,
        )
        val saveResult: TomlFile = fileIo.writeToml(data, tomlPath).getOrThrow()
    }
}