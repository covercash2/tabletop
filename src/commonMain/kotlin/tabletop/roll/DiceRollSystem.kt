/* (C)2023 */
package tabletop.roll

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
data class Dice(
    val dice: List<Die>,
) : Component<Dice> {
    override fun type(): ComponentType<Dice> = Dice

    companion object : ComponentType<Dice>() {
        val d20 = Dice(dice = listOf(Die.d20))
    }
}

@Serializable
@JvmInline
value class Die(val sides: UInt) {
    companion object {
        val d20 = Die(20u)
    }
}

@Serializable
data class DieRoll(
    val sides: UInt,
    val value: UInt,
)

@Serializable
data class DiceRollResult(
    val results: List<DieRoll>,
) : Component<DiceRollResult> {
    val value: UInt by lazy {
        results.sumOf { it.value }
    }
    override fun type(): ComponentType<DiceRollResult> = DiceRollResult

    companion object : ComponentType<DiceRollResult>()
}

class DiceRollSystem(
    private val roller: DieRoller = inject(),
) : IteratingSystem(
    family { all(Dice) },
) {
    override fun onTickEntity(entity: Entity) {
        val roll = entity[Dice]

        val rolls: List<DieRoll> = roll.dice.map { die ->
            val result = roller.roll(die)
            DieRoll(
                sides = die.sides,
                value = result,
            )
        }

        entity.configure {
            it -= roll.type()
            it += DiceRollResult(results = rolls)
        }
    }
}
