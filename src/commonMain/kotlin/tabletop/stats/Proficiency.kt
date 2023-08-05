/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

@Serializable
data class Proficiency(
    val type: AbilityType,
) : Component<Proficiency> {
    override fun type(): ComponentType<Proficiency> = Proficiency

    companion object : ComponentType<Proficiency>()
}
