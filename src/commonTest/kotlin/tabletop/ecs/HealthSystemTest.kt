/* (C)2023 */
package tabletop.ecs

import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.configureWorld
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class HealthSystemTest {
    @Test
    fun `TakeDamageSystem happy path`() {
        val world = configureWorld {
            families {
                val healthFamily = World.family {
                    all(Health, Damage)
                }
            }

            systems {
                add(TakeDamageSystem())
            }
        }

        val initialHealth = Health(10, 10u)
        val initialDamage = Damage(5u)

        val entity = world.entity {
            it += initialHealth
            it += initialDamage
        }

        assertEquals(1, world.numEntities)

        val components = world.snapshot()[entity]!!
        val health = components.find { it is Health }
        val damage = components.find { it is Damage }

        assertEquals(initialHealth, health)
        assertEquals(initialDamage, damage)

        world.update(10f)

        val updatedComponents = world.snapshot()[entity]!!

        val updatedHealth = updatedComponents.find { it is Health }
        val updatedDamage = updatedComponents.find { it is Damage }

        val expectedHealth = initialHealth.takeDamage(initialDamage)

        assertEquals(expectedHealth, updatedHealth)
        assertNull(updatedDamage)
    }
}
