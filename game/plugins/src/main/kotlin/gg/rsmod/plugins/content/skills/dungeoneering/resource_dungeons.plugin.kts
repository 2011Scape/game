package gg.rsmod.plugins.content.skills.dungeoneering

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

/**
 * @author Eikenb00m <https://github.com/eikenb00m>
 */

//Edgeville Dungeon Chaos druids
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52849, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 10) {
        player.queue {
            messageBox("You need a Dungeoneering level of 10 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(991, 4585, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Dwarven Mine
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52855, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 15) {
        player.queue {
            messageBox("You need a Dungeoneering level of 15 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1041, 4575, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Edgeville dungeon hill giants
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52853, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 20) {
        player.queue {
            messageBox("You need a Dungeoneering level of 20 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1134, 4589, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Karamja volcano
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52850, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 25) {
        player.queue {
            messageBox("You need a Dungeoneering level of 25 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1186, 4598, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Daemonheim Peninsula
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52861, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 30) {
        player.queue {
            messageBox("You need a Dungeoneering level of 30 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(3498, 3633, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Baxtorian Falls
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52857, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 35) {
        player.queue {
            messageBox("You need a Dungeoneering level of 35 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1257, 4592, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Mining Guild
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52856, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 45) {
        player.queue {
            messageBox("You need a Dungeoneering level of 45 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1053, 4521, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Taverley Dungeon Hellhounds
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52851, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 55) {
        player.queue {
            messageBox("You need a Dungeoneering level of 55 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1394, 4587, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Taverley Dungeon Blue Dragons
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52852, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 60) {
        player.queue {
            messageBox("You need a Dungeoneering level of 60 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1000, 4522, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Varrock Sewers
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52863, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 65) {
        player.queue {
            messageBox("You need a Dungeoneering level of 65 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1312, 4590, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Chaos tunnels
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52858, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 70) {
        player.queue {
            messageBox("You need a Dungeoneering level of 70 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1238, 4524, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Al kharid
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52860, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 75) {
        player.queue {
            messageBox("You need a Dungeoneering level of 75 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1182, 4515, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Brimhaven
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52854, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 80) {
        player.queue {
            messageBox("You need a Dungeoneering level of 80 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1140, 4422, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}
//Asgarnian Ice Dungeon
on_obj_option(obj = Objs.MYSTERIOUS_ENTRANCE_52859, option = "enter") {
    val obj = player.getInteractingGameObj()
    if (player.skills.getMaxLevel(Skills.DUNGEONEERING) < 85) {
        player.queue {
            messageBox("You need a Dungeoneering level of 85 to enter this.")
        }
        return@on_obj_option
    }
    player.queue {
        player.teleport(Tile(1297, 4510, 0), TeleportType.DUNGEONEERING_GATESTONE)
    }
}