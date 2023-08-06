/* (C)2023 */
package tabletop.abilities

import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.configureWorld
import tabletop.log.CheckLog
import tabletop.log.Log
import tabletop.result.Ok
import tabletop.roll.DiceRoller
import tabletop.stats.AbilityCheck
import tabletop.stats.AbilityCheckSystem
import tabletop.stats.CheckResult
import tabletop.stats.StatBlock
import tabletop.stats.StraightCheck
import tabletop.stats.Strength
import tabletop.stats.trivialStatBlock
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AbilityCheckTest {
    @Test
    fun `AbilityCheckSystem happy path`() {
        val diceRollResult = 15u
        val world = configureWorld {
            injectables {
                add(DiceRoller(Random(0)))
            }
            families {
                val abilityCheckFamily = World.family {
                    all(StatBlock, AbilityCheck.StraightCheck)
                }
            }

            systems {
                add(AbilityCheckSystem())
            }
        }

        val statBlock = trivialStatBlock()
        val abilityCheck = StraightCheck(
            difficultyClass = 15u,
            ability = Strength,
        )

        val entity = world.entity {
            it += statBlock
            it += abilityCheck
        }

        assertEquals(1, world.numEntities)

        val components = world.snapshot()[entity]!!

        val statBlockComponent = components.find { it is StatBlock }!!
        val abilityCheckComponent = components.find { it is StraightCheck }!!

        assertEquals(statBlock, statBlockComponent)
        assertEquals(abilityCheck, abilityCheckComponent)

        world.update(10f)

        val updatedComponents = world.snapshot()[entity]!!

        val updatedStatBlock = updatedComponents.find { it.type() == StatBlock }!!
        val updatedAbilityCheck = updatedComponents.find { it.type() == AbilityCheck.StraightCheck }
        val logComponent: CheckLog = updatedComponents.find { it.type() == Log.CheckLog }!! as CheckLog

        assertNull(updatedAbilityCheck)
        assertEquals(statBlock, updatedStatBlock)

        val expectedResult: CheckResult = Ok(diceRollResult.toInt())

        assertEquals(abilityCheck, logComponent.check)
        assertEquals(diceRollResult, logComponent.roll)
        assertEquals(expectedResult, logComponent.result)
    }
}
