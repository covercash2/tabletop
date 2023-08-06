/* (C)2023 */
package tabletop.stats

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import tabletop.damage.Dead
import tabletop.damage.Down
import tabletop.log.CheckLog
import tabletop.log.Log
import tabletop.log.LogVisibility
import tabletop.result.Err
import tabletop.result.Ok
import tabletop.roll.DieRoller
import tabletop.roll.d20

class SkillCheckSystem(
    private val diceRoller: DieRoller = inject(),
) : IteratingSystem(
    family { all(AbilityCheck.SkillCheck, StatBlock).none(Dead, Down) },
) {
    override fun onTickEntity(entity: Entity) {
        val statBlock = entity[StatBlock]
        val skillCheck: SkillCheck = entity[AbilityCheck.SkillCheck] as SkillCheck

        val roll = diceRoller.d20()
        val result = statBlock.skillCheck(
            roll = roll,
            difficultyClass = skillCheck.difficultyClass,
            skill = skillCheck.skill,
        )

        val log: Log = CheckLog(
            check = skillCheck,
            roll = roll,
            result = result,
            visibility = LogVisibility.Public,
        )

        entity.configure {
            it -= skillCheck.type()
            it += log
        }
    }
}

fun StatBlock.skillCheck(
    roll: UInt,
    difficultyClass: UInt,
    skill: Skill,
): CheckResult {
    val hasProficiency = proficiencies.contains(skill)
    val bonus = if (hasProficiency) {
        baseProficiencyBonus.toInt()
    } else {
        0
    }

    val abilityModifier = getModifierFor(skill.ability)
    val check = abilityModifier + bonus + roll.toInt()

    return if (check < difficultyClass.toInt()) {
        Err(check)
    } else {
        Ok(check)
    }
}
