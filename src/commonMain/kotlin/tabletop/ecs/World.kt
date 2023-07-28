/* (C)2023 */
package tabletop.ecs

import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.configureWorld

fun getWorld(): World = configureWorld(entityCapacity = 1000) {
}
