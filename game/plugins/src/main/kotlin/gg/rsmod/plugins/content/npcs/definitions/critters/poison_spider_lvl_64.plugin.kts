package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Seeds

/**
 * @author Alycia <https://github.com/alycii>
 */

val ids = intArrayOf(Npcs.POISON_SPIDER)

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 30
            attackStyle = StyleType.STAB
            poisonDamage = 6
        }
        stats {
            hitpoints = 640
            attack = 50
            strength = 58
            defence = 52
        }
        bonuses {
            defenceStab = 20
            defenceSlash = 17
            defenceCrush = 10
            defenceMagic = 14
            defenceRanged = 14
        }
        anims {
            attack = 5327
            block = 5328
            death = 5329
        }
        aggro {
            radius = 4
        }
        slayerData {
            slayerAssignment = SlayerAssignment.SPIDER
            xp = 64.0
            levelRequirement = 1
        }
    }
}