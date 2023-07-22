/* (C)2023 */
package tabletop

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val distanceUnit: Int = 5,
    val distanceUnitName: String = "feet",
)

fun defaultConfig(): Config = Config()
