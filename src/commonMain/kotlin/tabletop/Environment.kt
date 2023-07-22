/* (C)2023 */
package tabletop

import okio.FileSystem
import tabletop.io.DirPath
import tabletop.io.Directory
import tabletop.io.FileIo
import tabletop.io.FileResult
import tabletop.io.Path
import tabletop.io.RawPath
import tabletop.io.div
import tabletop.io.tryDir
import tabletop.result.Err
import tabletop.result.Ok
import tabletop.result.extract

data class Environment(
    val home: Directory,
    val configHome: Directory,
    val dataHome: Directory,
)

fun FileIo.loadEnvironment(): FileResult<Environment> {
    val dirs: List<Directory> = fileSystem.getDirLocations().all
        .map { path: Path ->
            tryDir(path)
        }
        .toList()
        .map { result ->
            result.extract { err ->
                return Err(err)
            }
        }
    val locations = fileSystem.getDirLocations()

    val home = tryDir(locations.home)
        .extract {
            return Err(it)
        }
    val configHome = tryDir(locations.configHome)
        .extract {
            return Err(it)
        }
    val dataHome = tryDir(locations.dataHome)
        .extract {
            return Err(it)
        }

    return Ok(
        Environment(
            home = home,
            configHome = configHome,
            dataHome = dataHome,
        ),
    )
}

/**
 * A wrapper around XDG base directories
 * https://wiki.archlinux.org/title/XDG_Base_Directory
 */
data class DirLocations(
    val home: Path,
    /**
     * Corresponds to `XDG_CONFIG_HOME` in POSIX systems
     */
    val configHome: Path,
    /**
     * Corresponds to `XDG_DATA_HOME` in POSIX systems
     */
    val dataHome: Path,
) {
    val all: Sequence<Path> = sequenceOf(home, configHome, dataHome)
}

fun xdgConfigHome(home: Path): RawPath = home / ".config" / "tabletop"
fun xdgDataHome(home: Path): RawPath = home / ".local/share" / "tabletop"

expect fun FileSystem.getHome(): DirPath

fun FileSystem.getDirLocations(): DirLocations {
    val home = getHome()
    val configHome = xdgConfigHome(home)
    val dataHome = xdgDataHome(home)

    return DirLocations(
        home = RawPath(home.okioPath),
        configHome = RawPath(configHome.okioPath),
        dataHome = RawPath(dataHome.okioPath),
    )
}
