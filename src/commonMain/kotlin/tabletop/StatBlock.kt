package tabletop

import tabletop.io.FileIo
import com.akuleshov7.ktoml.Toml
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import tabletop.io.FileError
import tabletop.io.Path
import tabletop.io.writeString
import tabletop.result.Result
import tabletop.result.map
import kotlin.jvm.JvmInline

@Serializable
data class StatBlock(
    val maxHitPoints: UInt,
    val armorClass: UInt,
)
