/* (C)2023 */
package tabletop.ecs

import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.configureWorld
import tabletop.board.Position
import tabletop.damage.Damage
import tabletop.damage.Dead
import tabletop.damage.Down
import tabletop.damage.TakeDamageSystem
import tabletop.log.ConsoleLogSystem
import tabletop.log.KotlinStdoutLogger
import tabletop.log.Logger
import tabletop.roll.DieRoller
import tabletop.roll.RandomDieRoller
import tabletop.stats.AbilityCheck
import tabletop.stats.AbilityCheckSystem
import tabletop.stats.StatBlock
import kotlin.random.Random

fun getWorld(): World = configureWorld(entityCapacity = 1000) {
    injectables {
        add(KotlinStdoutLogger() as Logger)
        add(
            RandomDieRoller(Random.Default) as DieRoller,
        )
    }

    families {
        val positionFamily = family {
            all(Position)
        }
        val healthFamily = family {
            all(Health, Damage).none(Dead, Down)
        }
        val abilityCheckFamily = family {
            all(AbilityCheck.StraightCheck, StatBlock).none(Dead, Down)
        }
    }

    systems {
        add(MoveSystem())
        add(AbilityCheckSystem())
        add(TakeDamageSystem())
        add(ConsoleLogSystem())
    }
}
