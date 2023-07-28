/* (C)2023 */
package tabletop.io

import okio.fakefilesystem.FakeFileSystem
import org.junit.jupiter.api.Test
import tabletop.getDirLocations
import tabletop.loadEnvironment
import tabletop.result.getOrThrow
import tabletop.stats.Charisma
import tabletop.stats.Constitution
import tabletop.stats.Dexterity
import tabletop.stats.Intelligence
import tabletop.stats.StatBlock
import tabletop.stats.Strength
import tabletop.stats.Wisdom
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class FileIoTest {
    private lateinit var fileIo: FileIo

    @BeforeTest
    fun setupFileIo() {
        fileIo = FileIo(FakeFileSystem())
        val dirs = fileIo.fileSystem.getDirLocations()
        fileIo.tryDir(dirs.home, create = true).getOrThrow()
    }

    @Test
    fun test() {
        val stats = StatBlock(
            strength = Strength(10u),
            dexterity = Dexterity(10u),
            constitution = Constitution(10u),
            wisdom = Wisdom(10u),
            intelligence = Intelligence(10u),
            charisma = Charisma(10u),
        )

        val environment = fileIo.loadEnvironment().getOrThrow()

        val path = "stats_test.toml".path()
        val file = fileIo.writeSerial(stats, path).getOrThrow()
        val string = fileIo.loadString(file).getOrThrow()

        val loadedStats = fileIo.loadSerial<StatBlock>(file).getOrThrow()

        assertEquals(stats, loadedStats)
    }
}
