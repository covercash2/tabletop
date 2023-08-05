package tabletop.abilities

import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.configureWorld
import tabletop.damage.Dead
import tabletop.damage.Down
import tabletop.log.CheckLog
import tabletop.log.Log
import tabletop.result.Ok
import tabletop.roll.DiceRoller
import tabletop.stats.AbilityCheck
import tabletop.stats.AbilityCheckSystem
import tabletop.stats.AbilityType
import tabletop.stats.CheckResult
import tabletop.stats.SavingThrow
import tabletop.stats.SavingThrowSystem
import tabletop.stats.StatBlock
import tabletop.stats.StraightCheck
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
                add(DiceRoller(Random(0)))
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

        val abilityType = AbilityType.Strength

        val statBlock = trivialStatBlock(
            proficiencies = setOf(abilityType)
        )
        val abilityCheck = SavingThrow(
            difficultyClass = 15u,
            abilityType = abilityType,
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