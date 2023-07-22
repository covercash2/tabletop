/* (C)2023 */
package tabletop.ecs

import tabletop.StatBlock

data class World(
    val entities: List<Entity>,
    val components: List<Component>,
    val statBlocks: List<StatBlock>,
    val systems: List<System>,
)

interface Entity

interface Component

interface System {
    fun run(component: StatBlock)
}
