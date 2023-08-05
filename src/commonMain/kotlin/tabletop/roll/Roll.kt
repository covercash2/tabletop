/* (C)2023 */
package tabletop.roll

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
data class Roll(
    val dice: List<Die>,
) : Component<Roll> {
    override fun type(): ComponentType<Roll> = Roll

    companion object : ComponentType<Roll>()
}

@Serializable
@JvmInline
value class Die(val faces: UInt)

class RollSystem : IteratingSystem(
    family { all(Roll) },
) {
    override fun onTickEntity(entity: Entity) {
        val roll = entity[Roll]
    }
}
