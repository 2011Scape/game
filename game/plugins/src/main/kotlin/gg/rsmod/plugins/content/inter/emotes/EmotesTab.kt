package gg.rsmod.plugins.content.inter.emotes

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.messageBox
import gg.rsmod.plugins.api.ext.setVarbit

/**
 * @author Tom <rspsmods@gmail.com>
 */
object EmotesTab {

    const val COMPONENT_ID = 464

    const val GOBLIN_EMOTES_VARBIT = 532
    const val GLASS_BOX_EMOTE_VARBIT = 1368
    const val CLIMB_ROPE_EMOTE_VARBIT = 1369
    const val LEAN_EMOTE_VARBIT = 1370
    const val GLASS_WALL_EMOTE_VARBIT = 1367
    const val IDEA_EMOTE_VARBIT = 2311
    const val STAMP_EMOTE_VARBIT = 2312
    const val FLAP_EMOTE_VARBIT = 2309
    const val SLAP_HEAD_EMOTE_VARBIT = 2310
    const val ZOMBIE_WALK_EMOTE_VARBIT = 1921
    const val ZOMBIE_DANCE_EMOTE_VARBIT = 1920
    const val SCARED_EMOTE_VARBIT = 1371
    const val RABBIT_HOP_EMOTE_VARBIT = 2055
    const val ZOMBIE_HAND_EMOTE_VARBIT = 4075
    const val SKILLCAPE_EMOTE_VARBIT = 2787
    const val SNOWMAN_EMOTE_VARBIT = 4202
    const val AIR_GUITAR_EMOTE_VARBIT = 4394
    const val SAFETY_FIRST_EMOTE_VARBIT = 4476
    const val EXPLORE_EMOTE_VARBIT = 4884
    const val TRICK_EMOTE_VARBIT = 5490
    const val FREEZE_EMOTE_VARBIT = 5732
    const val GIVE_THANKS_EMOTE_VARBIT = 5641
    const val AROUND_THE_WORLD_EMOTE_VARBIT = 6014 // requires 85 value
    const val DRAMATIC_POINT_EMOTE_VARBIT = 6936
    const val FAINT_EMOTE_VARBIT = 6095
    const val PUPPET_MASTER_EMOTE_VARBIT = 8300 // requires 20 value
    // TODO: assume 8601 is task completed amount varbit
    const val TASK_MASTER_EMOTE_VARBIT = 8601 // requires 428 value
    const val SEAL_OF_APPROVAL_EMOTE_VARBIT = 8688
    const val INVOKE_SPRING_EMOTE_VARBIT = 9198 // requires 60 value




    fun unlockAll(p: Player) {
        p.setVarbit(GOBLIN_EMOTES_VARBIT, 8)
        p.setVarbit(GLASS_BOX_EMOTE_VARBIT, 1)
        p.setVarbit(CLIMB_ROPE_EMOTE_VARBIT, 1)
        p.setVarbit(LEAN_EMOTE_VARBIT, 1)
        p.setVarbit(GLASS_WALL_EMOTE_VARBIT, 1)
        p.setVarbit(IDEA_EMOTE_VARBIT, 1)
        p.setVarbit(STAMP_EMOTE_VARBIT, 1)
        p.setVarbit(FLAP_EMOTE_VARBIT, 1)
        p.setVarbit(ZOMBIE_WALK_EMOTE_VARBIT, 1)
        p.setVarbit(ZOMBIE_DANCE_EMOTE_VARBIT, 1)
        p.setVarbit(SCARED_EMOTE_VARBIT, 1)
        p.setVarbit(ZOMBIE_HAND_EMOTE_VARBIT, 1)

    }

    fun performEmote(p: Player, emote: Emote) {
        if (emote.varbit != -1 && p.getVarbit(emote.varbit) != emote.requiredVarbitValue) {
            val description = emote.unlockDescription ?: "You have not unlocked this emote yet."
            p.queue { messageBox(description) }
            return
        }

        if (emote.anim != -1) {
            p.animate(emote.anim)
        }

        if (emote.gfx != -1) {
            p.graphic(emote.gfx)
        }
    }
}