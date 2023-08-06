/* (C)2023 */
package tabletop.equipment

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

@Serializable
data class Equipment(
    val items: List<Item>,
) : Component<Equipment> {
    override fun type(): ComponentType<Equipment> = Equipment

    companion object : ComponentType<Equipment>()
}

sealed interface Item
