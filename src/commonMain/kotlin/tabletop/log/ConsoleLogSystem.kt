/* (C)2023 */
package tabletop.log

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World

/**
 * An [IteratingSystem] that performs logging systemically instead of eagerly.
 * Most logs should be added to an entity in the appropriate system
 * ```kt
 * val log: Log = generateLog(systemResult)
 * entity.configure {
 *   it += log
 * }
 * ```
 */
class ConsoleLogSystem(
    private val logger: Logger = World.inject(),
) : IteratingSystem(
    World.family { any(Log.ConsoleLog, Log.CheckLog) },
) {
    override fun onTickEntity(entity: Entity) {
        val log: Log = entity.getOrNull(Log.ConsoleLog) ?: entity[Log.CheckLog]

        logger.log(log, entity)

        entity.configure {
            it -= Log.ConsoleLog
        }
    }
}
