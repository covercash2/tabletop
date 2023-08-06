/* (C)2023 */
package tabletop.creature

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.componentTypeOf
import kotlinx.serialization.Serializable

@Serializable
sealed class ChallengeRating : Component<ChallengeRating> {
    abstract val value: UInt
    override fun type(): ComponentType<ChallengeRating> = when (this) {
        is Fractional -> Fractional
        is Level -> Level
    }

    companion object {
        val Fractional = componentTypeOf<ChallengeRating>()
        val Level = componentTypeOf<ChallengeRating>()
    }
}

@Serializable
data class Fractional(private val denominator: UInt) : ChallengeRating() {
    override val value: UInt
        get() = 0u

    override fun toString(): String = "1/$denominator"
}

@Serializable
data class Level(val level: UInt) : ChallengeRating() {
    override val value: UInt
        get() = level

    override fun toString(): String = "$level"
}
