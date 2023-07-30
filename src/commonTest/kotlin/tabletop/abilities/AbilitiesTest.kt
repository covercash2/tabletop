/* (C)2023 */
package tabletop.abilities

import tabletop.stats.Charisma
import tabletop.stats.Constitution
import tabletop.stats.Dexterity
import tabletop.stats.Intelligence
import tabletop.stats.Ability
import tabletop.stats.Strength
import tabletop.stats.Wisdom
import tabletop.stats.modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class AbilitiesTest {
    fun testModifier(ability: Ability, expectedModifier: Int) {
        val modifier = ability.modifier()

        assertEquals(expectedModifier, modifier)
    }

    @Test
    fun `modifiers are determined correctly`() {
        testModifier(Strength(1u), expectedModifier = -5)
        testModifier(Dexterity(2u), expectedModifier = -4)
        testModifier(Wisdom(3u), expectedModifier = -4)
        testModifier(Intelligence(4u), expectedModifier = -3)
        testModifier(Constitution(5u), expectedModifier = -3)
        testModifier(Charisma(6u), expectedModifier = -2)
        testModifier(Strength(7u), expectedModifier = -2)
        testModifier(Dexterity(8u), expectedModifier = -1)
        testModifier(Wisdom(9u), expectedModifier = -1)
        testModifier(Intelligence(10u), expectedModifier = 0)
        testModifier(Constitution(11u), expectedModifier = 0)
        testModifier(Charisma(12u), expectedModifier = 1)
        testModifier(Strength(13u), expectedModifier = 1)
        testModifier(Dexterity(14u), expectedModifier = 2)
        testModifier(Wisdom(15u), expectedModifier = 2)
        testModifier(Intelligence(16u), expectedModifier = 3)
        testModifier(Constitution(17u), expectedModifier = 3)
        testModifier(Charisma(18u), expectedModifier = 4)
        testModifier(Strength(19u), expectedModifier = 4)
        testModifier(Dexterity(20u), expectedModifier = 5)
        testModifier(Wisdom(21u), expectedModifier = 5)
        testModifier(Intelligence(22u), expectedModifier = 6)
        testModifier(Constitution(23u), expectedModifier = 6)
        testModifier(Charisma(24u), expectedModifier = 7)
        testModifier(Strength(25u), expectedModifier = 7)
        testModifier(Dexterity(26u), expectedModifier = 8)
        testModifier(Wisdom(27u), expectedModifier = 8)
        testModifier(Intelligence(28u), expectedModifier = 9)
        testModifier(Constitution(29u), expectedModifier = 9)
        testModifier(Charisma(30u), expectedModifier = 10)
    }
}
