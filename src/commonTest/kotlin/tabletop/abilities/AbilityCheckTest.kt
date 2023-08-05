/* (C)2023 */
package tabletop.abilities

import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.configureWorld
import tabletop.FakeRandom
import tabletop.log.ConsoleLogSystem
import tabletop.log.KotlinStdoutLogger
import tabletop.log.Log
import tabletop.log.Logger
import tabletop.roll.DiceRoller
import tabletop.stats.AbilityCheck
import tabletop.stats.AbilityCheckSystem
import tabletop.stats.AbilityType
import tabletop.stats.StatBlock
import tabletop.stats.trivialStatBlock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AbilityCheckTest {
    @Test
    fun `AbilityCheckSystem happy path`() {
        val diceRollResult = 10
        val world = configureWorld {
            injectables {
                add(DiceRoller(FakeRandom(expectedInt = diceRollResult)))
                add(KotlinStdoutLogger() as Logger)
            }
            families {
                val abilityCheckFamily = World.family {
                    all(StatBlock, AbilityCheck)
                }
            }

            systems {
                add(AbilityCheckSystem())
                add(ConsoleLogSystem())
            }
        }

        val statBlock = trivialStatBlock()
        val abilityCheck = AbilityCheck(
            difficultyClass = 15u,
            abilityType = AbilityType.Strength,
        )

        val entity = world.entity {
            it += statBlock
            it += abilityCheck
        }

        assertEquals(1, world.numEntities)

        val components = world.snapshot()[entity]!!

        val statBlockComponent = components.find { it is StatBlock }!!
        val abilityCheckComponent = components.find { it is AbilityCheck }!!

        assertEquals(statBlock, statBlockComponent)
        assertEquals(abilityCheck, abilityCheckComponent)

        world.update(10f)

        val updatedComponents = world.snapshot()[entity]!!

        val updatedStatBlock = updatedComponents.find { it is StatBlock }!!
        val updatedAbilityCheck = updatedComponents.find { it is AbilityCheck }
        val logComponent = updatedComponents.find { it is Log }!!

        assertNull(updatedAbilityCheck)
        assertEquals(statBlock, updatedStatBlock)
    }
}
