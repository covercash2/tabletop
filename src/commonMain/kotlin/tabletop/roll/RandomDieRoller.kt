/* (C)2023 */
package tabletop.roll

import kotlin.random.Random
import kotlin.random.nextUInt

interface DieRoller {
    fun roll(die: Die): UInt
}

fun DieRoller.d20(): UInt = roll(Die.d20)

class RandomDieRoller(
    private val rand: Random,
) : DieRoller {
    override fun roll(die: Die) = rand.nextUInt(1u..die.sides)
}
