/* (C)2023 */
package tabletop

import kotlin.random.Random

class FakeRandom(
    expectedInt: Int,
) : Random() {
    var expectedInt = expectedInt

    override fun nextBits(bitCount: Int): Int {
        TODO("Not yet implemented")
    }

    override fun nextInt(): Int = expectedInt
}
