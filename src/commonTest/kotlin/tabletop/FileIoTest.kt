/* (C)2023 */
package tabletop

import okio.fakefilesystem.FakeFileSystem
import tabletop.io.FileIo
import tabletop.io.Path
import tabletop.io.TomlFile
import tabletop.io.path
import tabletop.io.tryDir
import tabletop.io.tryExtension
import tabletop.io.writeToml
import tabletop.result.errOrThrow
import tabletop.result.getOrThrow
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

    @Test
    fun `save TOML file`() {
        val tomlPath: Path = "file.toml".path()
        val data = StatBlock(
            maxHitPoints = 10u,
            armorClass = 10u,
        )
        val statResult: TomlFile = fileIo.writeToml(data, tomlPath).getOrThrow()

        val configDirPath = "/test/dir/.config".path()
        val configDir = fileIo.tryDir(configDirPath, create = true).getOrThrow()

        val config = Config(
            dataDir = configDir.dirPath(),
        )

        val configPath = "config.toml".path()
        val configResult: TomlFile = fileIo.writeToml(config, configPath).getOrThrow()
    }
}
