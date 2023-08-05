/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import kotlinx.serialization.Serializable
import tabletop.damage.Dead
import tabletop.damage.Down
import tabletop.log.CheckLog
import tabletop.log.Log
import tabletop.log.LogVisibility
import tabletop.result.Err
import tabletop.result.Ok
import tabletop.roll.DiceRoller

@Serializable
data class AbilityCheck(
    val difficultyClass: UInt,
    val abilityType: AbilityType,
) : Component<AbilityCheck> {
    override fun type(): ComponentType<AbilityCheck> = AbilityCheck

    companion object : ComponentType<AbilityCheck>()
}

class AbilityCheckSystem(
    private val diceRoller: DiceRoller = inject(),
) : IteratingSystem(
    family { all(AbilityCheck, StatBlock).none(Dead, Down) },
) {
    override fun onTickEntity(entity: Entity) {
        val statBlock = entity[StatBlock]
        val abilityCheck = entity[AbilityCheck]

        val statValue = statBlock.getValueByAbilityType(abilityCheck.abilityType)
        val roll = diceRoller.d20()
        val checkValue = statValue + roll

        val log: Log = if (checkValue < abilityCheck.difficultyClass) {
            CheckLog(
                check = abilityCheck,
                result = Err(checkValue),
                visibility = LogVisibility.Public,
            )
        } else {
            CheckLog(
                check = abilityCheck,
                result = Ok(checkValue),
                visibility = LogVisibility.Public,
            )
        }

        entity.configure {
            it -= AbilityCheck
            it += log
        }
    }
}
