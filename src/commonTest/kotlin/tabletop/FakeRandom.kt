/* (C)2023 */
package tabletop

import kotlin.random.Random
import kotlin.random.nextUInt

class FakeRandom(
    expectedInt: Int,
) : Random() {
    var expectedInt = expectedInt

    override fun nextBits(bitCount: Int): Int {
        nextUInt(1u..2u)
        TODO("Not yet implemented")
    }

    override fun nextInt(from: Int, until: Int): Int = expectedInt
}
