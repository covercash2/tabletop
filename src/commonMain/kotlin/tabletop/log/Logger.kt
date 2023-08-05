/* (C)2023 */
package tabletop.log

import com.github.quillraven.fleks.Entity

interface Logger {
    fun log(log: Log, entity: Entity? = null)
}

class KotlinStdoutLogger : Logger {
    override fun log(log: Log, entity: Entity?) {
        val logString = if (entity != null) log.withEntity(entity) else log.toString()
        println(logString)
    }
}
