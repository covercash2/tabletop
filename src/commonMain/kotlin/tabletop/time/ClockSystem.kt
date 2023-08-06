/* (C)2023 */
package tabletop.time

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Serializable
data class Time(
    val time: Instant,
) : Component<Time> {
    override fun type(): ComponentType<Time> = Time

    companion object : ComponentType<Time>()
}

sealed interface TimeInterval {
    val duration: Duration
}

data object Turn : TimeInterval {
    override val duration: Duration = 6L.seconds
}

@JvmInline
value class PassTime(override val duration: Duration) : TimeInterval

class ClockSystem : IteratingSystem(
    family = family {
        any()
    },
) {
    override fun onTickEntity(entity: Entity) {
        TODO("Not yet implemented")
    }
}
