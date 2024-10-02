package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.game.model.attr.INTERACTING_PLAYER_ATTR
import gg.rsmod.plugins.content.combat.createProjectile
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.LostCity
import java.lang.ref.WeakReference

val lostCity = LostCity

on_npc_option(npc = Npcs.SHAMUS, option = "talk-to") {
    player.queue {
        when (player.getCurrentStage(lostCity)) {
            LostCity.NOT_STARTED -> {}
            LostCity.FINDING_SHAMUS -> {}
            LostCity.FOUND_SHAMUS -> foundShamus(this)
            LostCity.ENTRANA_DUNGEON -> postEntrana(this)
            LostCity.CUT_DRAMEN_TREE -> postEntrana(this)
            LostCity.CREATE_DRAMEN_BRANCH -> postEntrana(this)
            LostCity.QUEST_COMPLETE -> shamusTeleport(this)
        }
    }
}

suspend fun foundShamus(it: QueueTask) {
    it.chatNpc(
        "Ah, yer big elephant! Yer've caught me! What",
        "would an elephant like yer be wanting wid ol'",
        "Shamus, then?",
    )
    it.chatPlayer("I want to find Zanaris.")
    it.chatNpc(
        "Zanaris, is it now? Well, well, well... You'll be needing",
        "that funny little shed out there in the swamp, so you",
        "will.",
    )
    it.chatPlayer(
        "Shed? I thought Zanaris was a city.",
    )
    it.chatNpc("Aye, that it is!")
    when (
        it.options(
            "How does it fit in a shed, then?",
            "I've been in that shed and I didn't see a city.",
        )
    ) {
        1 -> {
            it.chatPlayer("How does it fit in a shed, then?")
            it.chatNpc(
                "Ah, yer stupid elephant! The city isn't IN the shed! The",
                "doorway to the shed is a portal to Zanaris, so it is.",
            )
            it.chatPlayer("So, I just walk into the shed and end up in Zanaris?")
            it.chatNpc(
                "Oh, was I forgetting to say? Ya need to be carrying a",
                "dramen staff to be getting there! Otherwise, yer'll just",
                "be ending up in the shed.",
            )
            it.chatPlayer("Where could I get such a staff?")
            it.chatNpc(
                "Dramen staves are crafted from branches of the",
                "dramen tree, so they are. I hear there's a dramen tree",
                "in a cave over on the island of Entrana.",
            )
            it.chatNpc(
                "There would probably be a good place for an elephant",
                "like yer to look, I reckon",
            )
            it.chatNpc(
                "The monks are running a ship from Port Sarim to",
                "Entrana, so I hear.",
            )
            shamusTeleport(it)
        }
        2 -> {
            it.chatPlayer("I've been in that shed and I didn't see a city.")
            it.chatNpc(
                "Ah, yer stupid elephant! The city isn't IN the shed! The",
                "doorway to the shed is a portal to Zanaris, so it is.",
            )
            it.chatPlayer("So, I just walk into the shed and end up in Zanaris?")
            it.chatNpc(
                "Oh, was I forgetting to say? Ya need to be carrying a",
                "dramen staff to be getting there! Otherwise, yer'll just",
                "be ending up in the shed.",
            )
            it.chatPlayer("Where could I get such a staff?")
            it.chatNpc(
                "Dramen staves are crafted from branches of the",
                "dramen tree, so they are. I hear there's a dramen tree",
                "in a cave over on the island of Entrana.",
            )
            it.chatNpc(
                "There would probably be a good place for an elephant",
                "like yer to look, I reckon",
            )
            it.chatNpc(
                "The monks are running a ship from Port Sarim to",
                "Entrana, so I hear.",
            )
            shamusTeleport(it)
        }
    }
}

suspend fun postEntrana(it: QueueTask) {
    val shamus = it.player.getInteractingNpc()
    it.chatNpc(
        "Ah, yer big elephant! Yer've caught me! What",
        "would an elephant like yer be wanting wid ol'",
        "Shamus, then?",
    )
    when (it.options("I'm not sure.", "How do I get to Zanaris again?")) {
        1 -> {
            it.chatPlayer("I'm not sure.")
            it.chatNpc(
                "Ha! Look at yer! Look at the stupid elephant who tries to",
                "go catching a leprechaun when he don't even be knowing",
                "what he wants!",
            )
            world.remove(shamus)
        }
        2 -> {
            it.chatPlayer("How do I get to Zanaris again?")
            it.chatNpc(
                "Yer stupid elephant! I'll tell yer again! Yer need to",
                "be entering the shed in the middle of the swamp while",
                "holding a dramenwood staff! Yer can make the Dramen",
            )
            it.chatNpc(
                "staff from a tree branch, and there's a Dramen",
                "tree on Entrana! Now leave me alone yer great",
                "elephant!",
            )
            world.remove(shamus)
        }
    }
}

suspend fun shamusTeleport(it: QueueTask) {
    val portSarimTile = Tile(3041, 3247, 0)
    it.player.advanceToNextStage(LostCity)
    it.chatNpc("Did yer need a teleport over to Port Sarim?")
    when (
        it.options(
            "Yes, please a teleport would be useful.",
            "No, thanks, I'll get there on my own.",
            title = "     What do you say?     ",
        )
    ) {
        1 -> {
            it.chatPlayer("Yes, please, a teleport would be useful.")
            it.chatNpc("Right yer are. Hold on!")
            portSarimTeleport(it.player, "Avach Sarimporto!", targetTile = portSarimTile)
        }
    }
}

fun portSarimTeleport(
    player: Player,
    dialogue: String = "",
    targetTile: Tile,
) {
    val npc = player.getInteractingNpc()
    npc.attr[INTERACTING_PLAYER_ATTR] = WeakReference(player)
    val p = npc.getInteractingPlayer()
    npc.queue {
        npc.facePawn(npc.getInteractingPlayer())
        npc.forceChat(dialogue)
        npc.graphic(108)
        val projectile = npc.createProjectile(p, 109, ProjectileType.MAGIC)
        p.world.spawn(projectile)
        p.playSound(Sfx.CURSE_CAST_AND_FIRE)
        wait(MagicCombatStrategy.getHitDelay(npc.tile, p.tile) + 1)
        p.moveTo(targetTile)
        wait(1)
        p.graphic(110)
        p.playSound(Sfx.CURSE_HIT)
    }
}
