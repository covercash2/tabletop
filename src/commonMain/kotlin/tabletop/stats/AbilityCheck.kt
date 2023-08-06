/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import com.github.quillraven.fleks.componentTypeOf
import kotlinx.serialization.Serializable
import tabletop.damage.Dead
import tabletop.damage.Down
import tabletop.log.CheckLog
import tabletop.log.Log
import tabletop.log.LogVisibility
import tabletop.result.Err
import tabletop.result.Ok
import tabletop.result.Result
import tabletop.roll.DiceRoller

typealias CheckResult = Result<Int, Int>

@Serializable
sealed class AbilityCheck : Component<AbilityCheck> {
    abstract val difficultyClass: UInt

    override fun type(): ComponentType<AbilityCheck> = when (this) {
        is SavingThrow -> SavingThrow
        is StraightCheck -> StraightCheck
        is SkillCheck -> SkillCheck
    }

    companion object {
        val StraightCheck = componentTypeOf<AbilityCheck>()
        val SavingThrow = componentTypeOf<AbilityCheck>()
        val SkillCheck = componentTypeOf<AbilityCheck>()
    }
}

data class StraightCheck(
    val ability: Ability,
    override val difficultyClass: UInt,
) : AbilityCheck()

data class SavingThrow(
    val ability: Ability,
    override val difficultyClass: UInt,
) : AbilityCheck()

data class SkillCheck(
    val skill: Skill,
    override val difficultyClass: UInt,
) : AbilityCheck()

class AbilityCheckSystem(
    private val diceRoller: DiceRoller = inject(),
) : IteratingSystem(
    family { all(AbilityCheck.StraightCheck, StatBlock).none(Dead, Down) },
) {
    override fun onTickEntity(entity: Entity) {
        val statBlock = entity[StatBlock]
        val abilityCheck: StraightCheck = entity[AbilityCheck.StraightCheck] as StraightCheck

        val roll = diceRoller.d20()
        val result = statBlock.abilityCheck(
            roll = roll,
            difficultyClass = abilityCheck.difficultyClass,
            ability = abilityCheck.ability,
        )

        val log: Log = CheckLog(
            check = abilityCheck,
            roll = roll,
            result = result,
            visibility = LogVisibility.Public,
        )

        entity.configure {
            it -= abilityCheck.type()
            it += log
        }
    }
}

fun StatBlock.abilityCheck(
    roll: UInt,
    difficultyClass: UInt,
    ability: Ability,
): CheckResult {
    val modifier = getModifierFor(ability)
    val result = roll.toInt() + modifier

    return if (result < difficultyClass.toInt()) {
        Err(result)
    } else {
        Ok(result)
    }
}
