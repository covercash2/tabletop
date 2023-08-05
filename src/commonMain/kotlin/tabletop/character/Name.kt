/* (C)2023 */
package tabletop.character

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable

@Serializable
data class Name(
    val nameParts: List<String>,
) : Component<Name> {
    val full: String by lazy {
        nameParts.joinToString(" ")
    }

    override fun type(): ComponentType<Name> = Name

    companion object : ComponentType<Name>()
}
