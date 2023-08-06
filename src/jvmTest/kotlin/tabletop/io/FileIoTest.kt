/* (C)2023 */
package tabletop.io

import okio.fakefilesystem.FakeFileSystem
import org.junit.jupiter.api.Test
import tabletop.creature.Level
import tabletop.getDirLocations
import tabletop.loadEnvironment
import tabletop.result.getOrThrow
import tabletop.stats.StatBlock
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
            challengeRating = Level(1u),
            strength = 10u,
            dexterity = 10u,
            constitution = 10u,
            wisdom = 10u,
            intelligence = 10u,
            charisma = 10u,
        )

        val environment = fileIo.loadEnvironment().getOrThrow()

        val path = "stats_test.toml".path()
        val file = fileIo.writeSerial(stats, path).getOrThrow()
        val string = fileIo.loadString(file).getOrThrow()

        val loadedStats = fileIo.loadSerial<StatBlock>(file).getOrThrow()

        assertEquals(stats, loadedStats)
    }
}
