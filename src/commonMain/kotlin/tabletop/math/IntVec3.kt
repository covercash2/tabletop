/* (C)2023 */
package tabletop.math

import kotlinx.serialization.Serializable

@Serializable
data class IntVec3(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    operator fun plus(other: IntVec3) = IntVec3(
        x = x + other.x,
        y = y + other.y,
        z = y + other.z,
    )
}
