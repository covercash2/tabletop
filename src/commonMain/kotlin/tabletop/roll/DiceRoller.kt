/* (C)2023 */
package tabletop.roll

import kotlin.random.Random
import kotlin.random.nextUInt

class DiceRoller(
    private val rand: Random,
) {
    fun d100(): UInt = rand.nextUInt(1u..100u)
    fun d20(): UInt = rand.nextUInt(1u..20u)
    fun d10(): UInt = rand.nextUInt(1u..10u)
    fun d8(): UInt = rand.nextUInt(1u..8u)
}
