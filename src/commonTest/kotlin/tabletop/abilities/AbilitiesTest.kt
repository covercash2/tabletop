/* (C)2023 */
package tabletop.abilities

import tabletop.stats.abilityModifier
import kotlin.test.Test
import kotlin.test.assertEquals

class AbilitiesTest {
    fun testModifier(ability: UInt, expectedModifier: Int) {
        val modifier = ability.abilityModifier()

        assertEquals(expectedModifier, modifier)
    }

    @Test
    fun `modifiers are determined correctly`() {
        testModifier(1u, expectedModifier = -5)
        testModifier(2u, expectedModifier = -4)
        testModifier(3u, expectedModifier = -4)
        testModifier(4u, expectedModifier = -3)
        testModifier(5u, expectedModifier = -3)
        testModifier(6u, expectedModifier = -2)
        testModifier(7u, expectedModifier = -2)
        testModifier(8u, expectedModifier = -1)
        testModifier(9u, expectedModifier = -1)
        testModifier(10u, expectedModifier = 0)
        testModifier(11u, expectedModifier = 0)
        testModifier(12u, expectedModifier = 1)
        testModifier(13u, expectedModifier = 1)
        testModifier(14u, expectedModifier = 2)
        testModifier(15u, expectedModifier = 2)
        testModifier(16u, expectedModifier = 3)
        testModifier(17u, expectedModifier = 3)
        testModifier(18u, expectedModifier = 4)
        testModifier(19u, expectedModifier = 4)
        testModifier(20u, expectedModifier = 5)
        testModifier(21u, expectedModifier = 5)
        testModifier(22u, expectedModifier = 6)
        testModifier(23u, expectedModifier = 6)
        testModifier(24u, expectedModifier = 7)
        testModifier(25u, expectedModifier = 7)
        testModifier(26u, expectedModifier = 8)
        testModifier(27u, expectedModifier = 8)
        testModifier(28u, expectedModifier = 9)
        testModifier(29u, expectedModifier = 9)
        testModifier(30u, expectedModifier = 10)
    }
}
