/* (C)2023 */
package tabletop.roll

class TestDieRoller(
    expectedRolls: Map<Die, UInt>,
) : DieRoller {
    val rolls: MutableMap<Die, UInt> = expectedRolls.toMutableMap()

    override fun roll(die: Die): UInt = rolls[die]!!
}
