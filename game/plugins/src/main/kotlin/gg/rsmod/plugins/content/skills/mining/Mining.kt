package gg.rsmod.plugins.content.skills.mining

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.GameObject
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.api.ext.interpolate
import gg.rsmod.plugins.api.ext.messageBox
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.api.ext.pluralSuffix
import java.util.*

object Mining {
    data class Rock(val type: RockType, val obj: Int, val depletedRock: Int)
    suspend fun mineRock(it: QueueTask, obj: GameObject, rock: RockType, depletedRockId: Int) {
        val p = it.player
        if (!canMine(it, p, obj, rock)) {
            return
        }
        val oreName = p.world.definitions.get(ItemDef::class.java, rock.ore).name
        val pick = PickaxeType.values.firstOrNull {
            p.getSkills()
                .getMaxLevel(Skills.MINING) >= it.level && (p.equipment.contains(it.item) || p.inventory.contains(it.item))
        }!!
        p.filterableMessage("You swing your pick at the rock.")
        while (true) {
            p.animate(pick.animation)
            it.wait(2)
            if (!canMine(it, p, obj, rock)) {
                p.animate(-1)
                break
            }
            val level = p.getSkills().getCurrentLevel(Skills.MINING)
            if (level.interpolate(minChance = 60, maxChance = 190, minLvl = 1, maxLvl = 99, cap = 255)) {
                p.filterableMessage("You manage to get some ${oreName.pluralSuffix(2).lowercase(Locale.getDefault())}.")

                if (p.hasEquipped(
                        EquipmentType.AMULET, Items.AMULET_OF_GLORY_1, Items.AMULET_OF_GLORY_2,
                        Items.AMULET_OF_GLORY_3, Items.AMULET_OF_GLORY_4
                    )
                ) {
                    val chanceOfFindingGem = (1..86).random()
                    if (chanceOfFindingGem == 86) {
                        p.inventory.add(Items.UNCUT_DIAMOND + ((0..7).random() * 2))
                    }
                } else {
                    val chanceOfFindingGem = (1..256).random()
                    if (chanceOfFindingGem == 256) {
                        p.inventory.add(Items.UNCUT_DIAMOND + ((0..7).random() * 2))
                    }
                }
                p.inventory.add(rock.ore)
                p.addXp(Skills.MINING, rock.xp)
                p.animate(-1)
                if (depletedRockId != -1) {
                    val world = p.world
                    world.queue {
                        val depletedOre = DynamicObject(obj, depletedRockId)
                        world.remove(obj)
                        world.spawn(depletedOre)
                        wait(rock.respawnTime.random())
                        world.remove(depletedOre)
                        world.spawn(DynamicObject(obj))
                    }
                }
                break
            }
            it.wait(2)
        }
    }
    private suspend fun canMine(it: QueueTask, p: Player, obj: GameObject, rock: RockType): Boolean {
        if (!p.world.isSpawned(obj)) {
            return false
        }
        val pick = PickaxeType.values.firstOrNull {
            p.getSkills()
                .getMaxLevel(Skills.MINING) >= it.level && (p.equipment.contains(it.item) || p.inventory.contains(it.item))
        }
        if (pick == null) {
            it.messageBox("You need a pickaxe to mine this rock. You do not have a pickaxe which you have the Mining level to use.")
            return false
        }
        if (p.getSkills().getMaxLevel(Skills.MINING) < rock.level) {
            it.messageBox("You need a Mining level of ${rock.level} to mine this rock.")
            return false
        }
        if (p.inventory.isFull) {
            it.messageBox("Your inventory is too full to hold any more ores.")
            return false
        }
        return true
    }
}