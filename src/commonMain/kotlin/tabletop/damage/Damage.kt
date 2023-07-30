/* (C)2023 */
package tabletop.damage

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

@Serializable
data class Damage(
    val amount: UInt,
) : Component<Damage> {
    override fun type(): ComponentType<Damage> = Damage

    companion object : ComponentType<Damage>()
}

@Serializable
data class Down(
    val saves: UInt,
    val fails: UInt,
) : Component<Down> {
    override fun type(): ComponentType<Down> = Down

    companion object : ComponentType<Down>()
}

@Serializable
class Dead : Component<Dead> {
    override fun type(): ComponentType<Dead> = Dead

    companion object : ComponentType<Dead>()
}
