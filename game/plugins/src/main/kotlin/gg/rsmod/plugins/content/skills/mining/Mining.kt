package gg.rsmod.plugins.content.skills.mining

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.fs.def.ObjectDef
import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.GameObject
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.*

object Mining {

    suspend fun mineRock(it: QueueTask, obj: GameObject, rock: RockType) {
        val player = it.player
        if (!canMine(it, player, obj, rock)) {
            return
        }
        val pick = PickaxeType.values.reversed().firstOrNull {
            player.getSkills()
                .getMaxLevel(Skills.MINING) >= it.level && (player.equipment.contains(it.item) || player.inventory.contains(
                it.item
            ))
        }!!
        player.filterableMessage("You swing your pick at the rock.")
        while (canMine(it, player, obj, rock)) {
            player.animate(pick.animation)
            it.wait(pick.ticksBetweenRolls)
            val level = player.getSkills().getCurrentLevel(Skills.MINING)
            if (interpolate(rock.lowChance, rock.highChance, level) > RANDOM.nextInt(255)) {
                onSuccess(it, obj, rock)
                break
            }
        }
        player.animate(-1)
    }

    private suspend fun onSuccess(it: QueueTask, obj: GameObject, rock: RockType) {

        val player = it.player
        val oreName = player.world.definitions.get(ItemDef::class.java, rock.reward).name.lowercase()
        val depletedRockId = player.world.definitions.get(ObjectDef::class.java, obj.id).depleted
        var chanceOfGem = player.world.random(256)
        val world = player.world
        val depletedOre = DynamicObject(obj, depletedRockId)
        val oreObject = DynamicObject(obj)

        player.filterableMessage("You manage to mine some $oreName.")

        if (player.hasEquipped(
                EquipmentType.AMULET,
                Items.AMULET_OF_GLORY_1,
                Items.AMULET_OF_GLORY_2,
                Items.AMULET_OF_GLORY_3,
                Items.AMULET_OF_GLORY_4,
                Items.AMULET_OF_GLORY_T,
                Items.AMULET_OF_GLORY_T1,
                Items.AMULET_OF_GLORY_T2,
                Items.AMULET_OF_GLORY_T3,
                Items.AMULET_OF_GLORY_T4,
                Items.AMULET_OF_GLORY_T_10719,
                Items.AMULET_OF_GLORY_8283
            )
        ) {
            chanceOfGem = player.world.random(86)
        }

        if (chanceOfGem == 1) {
            player.inventory.add(Items.UNCUT_DIAMOND + (player.world.random(0..3) * 2))
        }

        if (player.hasEquipped(
                EquipmentType.CHEST,
                Items.VARROCK_ARMOUR_1,
                Items.VARROCK_ARMOUR_2,
                Items.VARROCK_ARMOUR_3,
                Items.VARROCK_ARMOUR_4
            )
        ) {
            if ((rock.varrockArmourAffected - (player.getEquipment(EquipmentType.CHEST)?.id ?: -1)) >= 0) {
                player.inventory.add(rock.reward)
            }
        }

        player.inventory.add(rock.reward)
        player.addXp(Skills.MINING, rock.experience)
        player.playSound(3600)
        player.animate(-1)

        if (depletedRockId != -1) {
            world.remove(oreObject)
            world.queue {
                world.spawn(depletedOre)
                wait(rock.respawnDelay) //TODO: add support mining guild runite ore respawn timer
                world.remove(depletedOre)
                world.spawn(oreObject)
            }
        }

    }

    private suspend fun canMine(it: QueueTask, player: Player, obj: GameObject, rock: RockType): Boolean {
        if (!player.world.isSpawned(obj)) {
            return false
        }
        val pick = PickaxeType.values.reversed().firstOrNull {
            player.getSkills()
                .getMaxLevel(Skills.MINING) >= it.level && (player.equipment.contains(it.item) || player.inventory.contains(
                it.item
            ))
        }
        if (pick == null) {
            it.messageBox("You need a pickaxe to mine this rock. You do not have a pickaxe<br><br>which you have the Mining level to use.")
            return false
        }
        if (player.getSkills().getMaxLevel(Skills.MINING) < rock.level) {
            it.messageBox("You need a Mining level of ${rock.level} to mine this rock.")
            return false
        }
        if (player.inventory.isFull) {
            it.messageBox("Your inventory is too full to hold any more ores.")
            return false
        }
        return true
    }

}