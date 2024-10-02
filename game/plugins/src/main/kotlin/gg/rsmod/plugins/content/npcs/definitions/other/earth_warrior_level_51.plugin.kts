package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Seeds

val NPC_ID = Npcs.EARTH_WARRIOR

val table = DropTableFactory
val earth_warrior =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(128)

            obj(Items.STEEL_SPEAR, slots = 3)
            obj(Items.STAFF_OF_EARTH, slots = 2)
            obj(Items.EARTH_RUNE, quantity = 12, slots = 13)
            obj(Items.NATURE_RUNE, quantity = 3, slots = 9)
            obj(Items.CHAOS_RUNE, quantity = 3, slots = 7)
            obj(Items.LAW_RUNE, quantity = 2, slots = 6)
            obj(Items.DEATH_RUNE, quantity = 2, slots = 4)
            obj(Items.BLOOD_RUNE, quantity = 2, slots = 3)
            obj(Items.EARTH_RUNE, quantity = 2, slots = 1)
            obj(Items.COINS_995, quantity = 12, slots = 18)

            table(Herbs.minorHerbTable, slots = 14)
            table(Seeds.uncommonSeedTable, slots = 18)
            table(Gems.gemTable, slots = 2)

            nothing(28)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 90)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 50)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 110)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 7)
            nothing(slots = 743)
        }
    }

table.register(earth_warrior, NPC_ID)

on_npc_pre_death(NPC_ID) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(NPC_ID) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(npc = NPC_ID) {
    configs {
        attackSpeed = 4
        respawnDelay = 30
    }
    stats {
        hitpoints = 540
        attack = 42
        strength = 42
        defence = 42
    }
    bonuses {
        defenceStab = 30
        defenceSlash = 40
        defenceCrush = 20
        defenceMagic = 10
        defenceRanged = 30
    }
    anims {
        attack = 401
        block = 403
        death = 836
    }
}
