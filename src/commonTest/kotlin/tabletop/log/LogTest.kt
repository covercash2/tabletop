/* (C)2023 */
package tabletop.log

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.configureWorld
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertNull

class TestLogger : Logger {
    private val logs: MutableList<String> = mutableListOf()
    override fun log(log: Log, entity: Entity?) {
        logs.add(log.withEntity(entity))
    }
}

class LogTest {
    @Test
    fun `log system`() {
        val logger = TestLogger()
        val world = configureWorld {
            injectables {
                add(logger as Logger)
            }
            families {
                val logFamily = World.family {
                    any(Log.ConsoleLog, Log.CheckLog)
                }
            }
            systems {
                add(ConsoleLogSystem())
            }
        }

        val testLog = ConsoleLog(
            message = "message",
            severity = LogSeverity.Info,
            visibility = LogVisibility.Private,
            timestamp = Instant.DISTANT_FUTURE,
        )

        val entity = world.entity {
            it += testLog
        }

        world.update(10f)

        val result = world.snapshot()[entity]!!

        val log = result.find { it is Log }

        assertNull(log)
    }
}
