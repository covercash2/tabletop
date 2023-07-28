/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

@Serializable
data class StatBlock(
    val strength: Strength,
    val dexterity: Dexterity,
    val constitution: Constitution,
    val intelligence: Intelligence,
    val wisdom: Wisdom,
    val charisma: Charisma,
) : Component<StatBlock> {
    override fun type(): ComponentType<StatBlock> = StatBlock

    companion object : ComponentType<StatBlock>()
}
