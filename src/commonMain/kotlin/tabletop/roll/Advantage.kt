/* (C)2023 */
package tabletop.roll

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

@Serializable
class Advantage : Component<Advantage> {
    override fun type(): ComponentType<Advantage> = Advantage

    companion object : ComponentType<Advantage>()
}

@Serializable
class Disadvantage : Component<Disadvantage> {
    override fun type(): ComponentType<Disadvantage> = Disadvantage

    companion object : ComponentType<Disadvantage>()
}
