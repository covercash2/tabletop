/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

data class Health(
    val current: Int,
    val max: UInt,
) : Component<Health> {
    override fun type(): ComponentType<Health> = Health

    companion object : ComponentType<Health>()
}
