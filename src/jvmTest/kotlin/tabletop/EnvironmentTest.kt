/* (C)2023 */
package tabletop

import okio.FileSystem
import okio.fakefilesystem.FakeFileSystem
import tabletop.io.FileIo
import tabletop.io.asRaw
import tabletop.io.tryCreateDirs
import tabletop.result.getOrThrow
import kotlin.test.BeforeTest
import kotlin.test.Test

class EnvironmentTest {
    private lateinit var fileSystem: FileSystem
    private lateinit var fileIo: FileIo

    @BeforeTest
    fun setupFileIo() {
        fileSystem = FakeFileSystem()
        fileIo = FileIo(fileSystem)
    }

    @Test
    fun `load local environment`() {
        fileSystem.getDirLocations().all
            .forEach {
                fileSystem.tryCreateDirs(it.asRaw()).getOrThrow()
            }

        val environment = fileIo.loadEnvironment().getOrThrow()
        println(environment)
    }
}
