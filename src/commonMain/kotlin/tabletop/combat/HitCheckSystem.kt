/* (C)2023 */
package tabletop.combat

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import kotlinx.serialization.Serializable

@Serializable
sealed interface CheckRollType

data object StraightRoll : CheckRollType
data object Advantage : CheckRollType
data object Disadvantage : CheckRollType

@Serializable
data class AttackRoll(
    val rollType: CheckRollType,
) : Component<AttackRoll> {
    override fun type(): ComponentType<AttackRoll> = AttackRoll

    companion object : ComponentType<AttackRoll>()
}

class HitCheckSystem : IteratingSystem(
    family {},
) {
    override fun onTickEntity(entity: Entity) {
        TODO("Not yet implemented")
    }
}
