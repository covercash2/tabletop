/* (C)2023 */
package tabletop.abilities

import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.configureWorld
import tabletop.damage.Dead
import tabletop.damage.Down
import tabletop.log.CheckLog
import tabletop.log.Log
import tabletop.result.Ok
import tabletop.roll.RandomDieRoller
import tabletop.stats.AbilityCheck
import tabletop.stats.Acrobatics
import tabletop.stats.CheckResult
import tabletop.stats.SkillCheck
import tabletop.stats.SkillCheckSystem
import tabletop.stats.StatBlock
import tabletop.stats.trivialStatBlock
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SkillCheckTest {
    @Test
    fun `SkillCheckSystem happy path`() {
        val diceRollResult = 15u
        val world = configureWorld {
            injectables {
                add(RandomDieRoller(Random(0)))
            }
            families {
                val abilityCheckFamily = World.family {
                    all(StatBlock, AbilityCheck.SkillCheck).none(Dead, Down)
                }
            }

            systems {
                add(SkillCheckSystem())
            }
        }

        val skill = Acrobatics

        val statBlock = trivialStatBlock(
            proficiencies = setOf(skill),
        )
        val skillCheck = SkillCheck(
            difficultyClass = 15u,
            skill = skill,
        )

        val entity = world.entity {
            it += statBlock
            it += skillCheck
        }

        assertEquals(1, world.numEntities)

        val components = world.snapshot()[entity]!!

        val statBlockComponent = components.find { it is StatBlock }!!
        val abilityCheckComponent = components.find { it is SkillCheck }!!

        assertEquals(statBlock, statBlockComponent)
        assertEquals(skillCheck, abilityCheckComponent)

        world.update(10f)

        val updatedComponents = world.snapshot()[entity]!!

        val updatedStatBlock = updatedComponents.find { it.type() == StatBlock }!!
        val updatedAbilityCheck = updatedComponents.find { it is SkillCheck }
        val logComponent: CheckLog = updatedComponents.find { it.type() == Log.CheckLog }!! as CheckLog

        assertNull(updatedAbilityCheck)
        assertEquals(statBlock, updatedStatBlock)

        val expectedResult: CheckResult = Ok(diceRollResult.toInt() + statBlock.baseProficiencyBonus.toInt())

        assertEquals(skillCheck, logComponent.check)
        assertEquals(diceRollResult, logComponent.roll)
        assertEquals(expectedResult, logComponent.result)
    }
}
