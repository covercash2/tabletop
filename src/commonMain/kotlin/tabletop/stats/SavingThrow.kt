/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World
import tabletop.damage.Dead
import tabletop.damage.Down
import tabletop.log.CheckLog
import tabletop.log.Log
import tabletop.log.LogVisibility
import tabletop.result.Err
import tabletop.result.Ok
import tabletop.roll.DiceRoller

class SavingThrowSystem(
    private val diceRoller: DiceRoller = World.inject(),
) : IteratingSystem(
    World.family { all(AbilityCheck.SavingThrow, StatBlock).none(Dead, Down) },
) {
    override fun onTickEntity(entity: Entity) {
        val statBlock = entity[StatBlock]
        val abilityCheck: SavingThrow = entity[AbilityCheck.SavingThrow] as SavingThrow

        val roll = diceRoller.d20()
        val result = statBlock.savingThrow(
            roll = roll,
            difficultyClass = abilityCheck.difficultyClass,
            abilityType = abilityCheck.abilityType,
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

fun StatBlock.savingThrow(
    roll: UInt,
    difficultyClass: UInt,
    abilityType: AbilityType,
): CheckResult {
    val hasProficiency = proficiencies.contains(abilityType)
    val bonus = if (hasProficiency) {
        baseProficiencyBonus.toInt()
    } else {
        0
    }

    val check = getModifierFor(abilityType) + bonus + roll.toInt()

    return if (check < difficultyClass.toInt()) {
        Err(check)
    } else {
        Ok(check)
    }
}
