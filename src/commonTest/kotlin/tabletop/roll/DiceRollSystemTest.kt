/* (C)2023 */
package tabletop.roll

import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.configureWorld
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DiceRollSystemTest {
    @Test
    fun `DiceRollSystem happy path`() {
        val expectedDiceValue = 15u
        val dieRoller = TestDieRoller(
            mapOf(
                Die(20u) to expectedDiceValue,
            ),
        )
        val world = configureWorld {
            injectables {
                add(
                    dieRoller as DieRoller,
                )
            }

            families {
                val diceFamily = World.family {
                    all(Dice)
                }
            }

            systems {
                add(DiceRollSystem())
            }
        }

        val dice = listOf(Die.d20)
        val rollD20 = Dice(dice)

        val entity = world.entity {
            it += rollD20
        }

        assertEquals(1, world.numEntities)

        val components = world.snapshot()[entity]!!
        val roll = components.find { it is Dice }

        assertEquals(rollD20, roll)

        world.update(10f)

        val updatedComponents = world.snapshot()[entity]!!

        val updatedRoll = updatedComponents.find { it is Dice }
        val diceRollResult = updatedComponents.find { it is DiceRollResult }!!

        val expectedResult = DiceRollResult(results = dice.map { DieRoll(sides = it.sides, value = expectedDiceValue) })

        assertNull(updatedRoll)
        assertEquals(expectedResult, diceRollResult)
    }
}
