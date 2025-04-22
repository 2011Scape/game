package gg.rsmod.plugins.content.inter.emotes

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Varbits
import gg.rsmod.plugins.api.ext.getVarbit
import gg.rsmod.plugins.api.ext.messageBox
import gg.rsmod.plugins.api.ext.setVarbit

/**
 * @author Tom <rspsmods@gmail.com>
 */
object EmotesTab {
    const val COMPONENT_ID = 464

    const val GOBLIN_EMOTES_VARBIT = Varbits.THE_LOST_TRIBE_PROGRESS
    const val GLASS_BOX_EMOTE_VARBIT = Varbits.GLASS_BOX_EMOTE
    const val CLIMB_ROPE_EMOTE_VARBIT = Varbits.CLIMB_ROPE_EMOTE
    const val LEAN_EMOTE_VARBIT = Varbits.LEAN_EMOTE
    const val GLASS_WALL_EMOTE_VARBIT = Varbits.GLASS_WALL_EMOTE
    const val IDEA_EMOTE_VARBIT = Varbits.IDEA_EMOTE
    const val STAMP_EMOTE_VARBIT = Varbits.STAMP_EMOTE
    const val FLAP_EMOTE_VARBIT = Varbits.FLAP_EMOTE
    const val SLAP_HEAD_EMOTE_VARBIT = Varbits.SLAP_HEAD_EMOTE
    const val ZOMBIE_WALK_EMOTE_VARBIT = Varbits.ZOMBIE_WALK_EMOTE
    const val ZOMBIE_DANCE_EMOTE_VARBIT = Varbits.ZOMBIE_DANCE_EMOTE
    const val SCARED_EMOTE_VARBIT = Varbits.SCARED_EMOTE
    const val RABBIT_HOP_EMOTE_VARBIT = Varbits.RABBIT_HOP_EMOTE
    const val ZOMBIE_HAND_EMOTE_VARBIT = Varbits.ZOMBIE_HAND_EMOTE
    const val SKILLCAPE_EMOTE_VARBIT = Varbits.SKILLCAPE_EMOTE
    const val SNOWMAN_EMOTE_VARBIT = Varbits.SNOWMAN_EMOTE
    const val AIR_GUITAR_EMOTE_VARBIT = Varbits.AIR_GUITAR_EMOTE
    const val SAFETY_FIRST_EMOTE_VARBIT = Varbits.SAFETY_FIRST_EMOTE
    const val EXPLORE_EMOTE_VARBIT = Varbits.EXPLORE_EMOTE
    const val TRICK_EMOTE_VARBIT = Varbits.TRICK_EMOTE
    const val FREEZE_EMOTE_VARBIT = Varbits.FREEZE_EMOTE
    const val GIVE_THANKS_EMOTE_VARBIT = Varbits.GIVE_THANKS_EMOTE
    const val AROUND_THE_WORLD_EMOTE_VARBIT = Varbits.AROUND_THE_WORLD_EMOTE // requires 85 value
    const val DRAMATIC_POINT_EMOTE_VARBIT = Varbits.DRAMATIC_POINT_EMOTE
    const val FAINT_EMOTE_VARBIT = Varbits.FAINT_EMOTE
    const val PUPPET_MASTER_EMOTE_VARBIT = Varbits.PUPPET_MASTER_EMOTE // requires 20 value

    // TODO: assume 8601 is task completed amount varbit
    const val TASK_MASTER_EMOTE_VARBIT = Varbits.TASK_MASTER_EMOTE // requires 428 value
    const val SEAL_OF_APPROVAL_EMOTE_VARBIT = Varbits.SEAL_OF_APPROVAL_EMOTE
    const val INVOKE_SPRING_EMOTE_VARBIT = Varbits.INVOKE_SPRING_EMOTE // requires 60 value

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

    fun performEmote(
        p: Player,
        emote: Emote,
    ) {
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
