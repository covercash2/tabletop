/* (C)2023 */
package tabletop.abilities

import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.configureWorld
import tabletop.damage.Dead
import tabletop.damage.Down
import tabletop.log.CheckLog
import tabletop.log.Log
import tabletop.result.Ok
import tabletop.roll.DieRoller
import tabletop.roll.RandomDieRoller
import tabletop.stats.AbilityCheck
import tabletop.stats.CheckResult
import tabletop.stats.SavingThrow
import tabletop.stats.SavingThrowSystem
import tabletop.stats.StatBlock
import tabletop.stats.Strength
import tabletop.stats.trivialStatBlock
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SavingThrowTest {
    @Test
    fun `SavingThrowSystem happy path`() {
        val diceRollResult = 15u
        val world = configureWorld {
            injectables {
                add(
                    RandomDieRoller(Random(0)) as DieRoller,
                )
            }
            families {
                val abilityCheckFamily = World.family {
                    all(StatBlock, AbilityCheck.SavingThrow).none(Dead, Down)
                }
            }

            systems {
                add(SavingThrowSystem())
            }
        }

        val ability = Strength

        val statBlock = trivialStatBlock(
            proficiencies = setOf(ability),
        )
        val abilityCheck = SavingThrow(
            difficultyClass = 15u,
            ability = ability,
        )

        val entity = world.entity {
            it += statBlock
            it += abilityCheck
        }

        assertEquals(1, world.numEntities)

        val components = world.snapshot()[entity]!!

        val statBlockComponent = components.find { it is StatBlock }!!
        val abilityCheckComponent = components.find { it is SavingThrow }!!

        assertEquals(statBlock, statBlockComponent)
        assertEquals(abilityCheck, abilityCheckComponent)

        world.update(10f)

        val updatedComponents = world.snapshot()[entity]!!

        val updatedStatBlock = updatedComponents.find { it.type() == StatBlock }!!
        val updatedAbilityCheck = updatedComponents.find { it.type() == AbilityCheck.SavingThrow }
        val logComponent: CheckLog = updatedComponents.find { it.type() == Log.CheckLog }!! as CheckLog

        assertNull(updatedAbilityCheck)
        assertEquals(statBlock, updatedStatBlock)

        val expectedResult: CheckResult = Ok(diceRollResult.toInt() + statBlock.baseProficiencyBonus.toInt())

        assertEquals(abilityCheck, logComponent.check)
        assertEquals(diceRollResult, logComponent.roll)
        assertEquals(expectedResult, logComponent.result)
    }
}
