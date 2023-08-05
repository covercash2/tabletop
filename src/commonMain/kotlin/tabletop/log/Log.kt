/* (C)2023 */
package tabletop.log

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.componentTypeOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import tabletop.result.Result
import tabletop.stats.AbilityCheck

@Serializable
sealed class Log : Component<Log> {
    abstract fun withEntity(entity: Entity?): String

    override fun type(): ComponentType<Log> = when (this) {
        is ConsoleLog -> ConsoleLog
        is CheckLog -> CheckLog
    }

    companion object {
        val ConsoleLog = componentTypeOf<Log>()
        val CheckLog = componentTypeOf<Log>()
    }
}

@Serializable
data class ConsoleLog(
    val message: String,
    val severity: LogSeverity,
    val visibility: LogVisibility,
    val timestamp: Instant = Clock.System.now(),
) : Log() {
    override fun withEntity(entity: Entity?): String {
        val entityString = if (entity != null) "(${entity.id})" else ""
        return "[${timestamp.toLocalDateTime(TimeZone.currentSystemDefault())}]" +
            " {${severity.symbol}} $entityString: $message"
    }
}

@Serializable
data class CheckLog(
    val check: AbilityCheck,
    val result: Result<UInt, UInt>,
    val visibility: LogVisibility,
    val timestamp: Instant = Clock.System.now(),
) : Log() {
    override fun withEntity(entity: Entity?): String = "${timestamp.defaultString()} $result $check"
}

fun Instant.defaultString(): String = "[${toLocalDateTime(TimeZone.currentSystemDefault())}]"

sealed interface LogSeverity {
    data object Debug : LogSeverity
    data object Info : LogSeverity
    data object Error : LogSeverity
}

val LogSeverity.symbol: String
    get() = when (this) {
        LogSeverity.Debug -> "D"
        LogSeverity.Error -> "E"
        LogSeverity.Info -> "I"
    }

sealed interface LogVisibility {
    data object Private : LogVisibility
    data object Public : LogVisibility
}
