/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class Name(
    val value: String,
) : Component<Name> {
    override fun type(): ComponentType<Name> = Name

    companion object : ComponentType<Name>()
}
