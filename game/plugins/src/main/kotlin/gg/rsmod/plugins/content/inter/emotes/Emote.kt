package gg.rsmod.plugins.content.inter.emotes

import gg.rsmod.plugins.api.cfg.Anims

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class Emote(
    val component: Int,
    val anim: Int,
    val gfx: Int = -1,
    val varbit: Int = -1,
    val requiredVarbitValue: Int = 1,
    val unlockDescription: String? = null,
) {
    YES(component = 2, anim = Anims.EMOTE_YES),
    NO(component = 3, anim = Anims.EMOTE_NO),
    BOW(component = 4, anim = Anims.EMOTE_BOW),
    ANGRY(component = 5, anim = Anims.EMOTE_ANGRY),
    THINK(component = 6, anim = Anims.EMOTE_THINK),
    WAVE(component = 7, anim = Anims.EMOTE_WAVE),
    SHRUG(component = 8, anim = Anims.EMOTE_SHRUG),
    CHEER(component = 9, anim = Anims.EMOTE_CHEER),
    BECKON(component = 10, anim = Anims.EMOTE_BECKON),
    LAUGH(component = 12, anim = Anims.EMOTE_LAUGH),
    JUMP_FOR_JOY(component = 11, anim = Anims.EMOTE_JUMP_FOR_JOY),
    YAWN(component = 13, anim = Anims.EMOTE_YAWN),
    DANCE(component = 14, anim = Anims.EMOTE_DANCE),
    JIG(component = 15, anim = Anims.EMOTE_JIG),
    SPIN(component = 16, anim = Anims.EMOTE_SPIN),
    HEADBANG(component = 17, anim = Anims.EMOTE_HEADBANG),
    CRY(component = 18, anim = Anims.EMOTE_CRY),
    BLOW_KISS(component = 19, anim = Anims.EMOTE_BLOW_KISS, gfx = 1702),
    PANIC(component = 20, anim = Anims.EMOTE_PANIC),
    RASPBERRY(component = 21, anim = Anims.EMOTE_RASPBERRY),
    CLAP(component = 22, anim = Anims.EMOTE_CLAP),
    SALUTE(component = 23, anim = Anims.EMOTE_SALUTE),
    GOBLIN_BOW(component = 24, anim = Anims.EMOTE_GOBLIN_BOW, varbit = EmotesTab.GOBLIN_EMOTES_VARBIT, requiredVarbitValue = 7),
    GOBLIN_SALUTE(component = 25, anim = Anims.EMOTE_GOBLIN_SALUTE, varbit = EmotesTab.GOBLIN_EMOTES_VARBIT, requiredVarbitValue = 7),
    GLASS_BOX(component = 26, anim = Anims.EMOTE_GLASS_BOX, varbit = EmotesTab.GLASS_BOX_EMOTE_VARBIT),
    CLIMB_ROPE(component = 27, anim = Anims.EMOTE_CLIMB_ROPE, varbit = EmotesTab.CLIMB_ROPE_EMOTE_VARBIT),
    LEAN(component = 28, anim = Anims.EMOTE_LEAN, varbit = EmotesTab.LEAN_EMOTE_VARBIT),
    GLASS_WALL(component = 29, anim = Anims.EMOTE_GLASS_WALL, varbit = EmotesTab.GLASS_WALL_EMOTE_VARBIT),
    IDEA(component = 33, anim = Anims.EMOTE_IDEA, gfx = 712, varbit = EmotesTab.IDEA_EMOTE_VARBIT),
    STAMP(component = 31, anim = Anims.EMOTE_STAMP, varbit = EmotesTab.STAMP_EMOTE_VARBIT),
    FLAP(component = 32, anim = Anims.EMOTE_FLAP, varbit = EmotesTab.FLAP_EMOTE_VARBIT),
    SLAP_HEAD(component = 30, anim = Anims.EMOTE_SLAP_HEAD, varbit = EmotesTab.SLAP_HEAD_EMOTE_VARBIT),
    ZOMBIE_WALK(component = 34, anim = Anims.EMOTE_ZOMBIE_WALK, varbit = EmotesTab.ZOMBIE_WALK_EMOTE_VARBIT),
    ZOMBIE_DANCE(component = 35, anim = Anims.EMOTE_ZOMBIE_DANCE, varbit = EmotesTab.ZOMBIE_DANCE_EMOTE_VARBIT),
    SCARED(component = 37, anim = Anims.EMOTE_SCARED, varbit = EmotesTab.SCARED_EMOTE_VARBIT),
    RABBIT_HOP(component = 38, anim = Anims.EMOTE_RABBIT_HOP, varbit = EmotesTab.RABBIT_HOP_EMOTE_VARBIT),
    ZOMBIE_HAND(component = 36, anim = Anims.EMOTE_ZOMBIE_HAND, gfx = 1244, varbit = EmotesTab.ZOMBIE_HAND_EMOTE_VARBIT),
    SKILLCAPE(component = 39, anim = Anims.RESET, varbit = EmotesTab.SKILLCAPE_EMOTE_VARBIT),
    SNOWMAN_DANCE(component = 40, anim = Anims.EMOTE_SNOWMAN_DANCE, varbit = EmotesTab.SNOWMAN_EMOTE_VARBIT),
    AIR_GUITAR(component = 41, anim = Anims.EMOTE_AIR_GUITAR, gfx = 1537, varbit = EmotesTab.AIR_GUITAR_EMOTE_VARBIT),
    SAFETY_FIRST(component = 42, anim = Anims.EMOTE_SAFETY_FIRST, gfx = 1553, varbit = EmotesTab.SAFETY_FIRST_EMOTE_VARBIT),
    EXPLORE(component = 43, anim = Anims.EMOTE_EXPLORE, gfx = 1734, varbit = EmotesTab.EXPLORE_EMOTE_VARBIT),
    TRICK(component = 44, anim = Anims.EMOTE_TRICK, gfx = 1864, varbit = EmotesTab.TRICK_EMOTE_VARBIT),
    FREEZE(component = 45, anim = Anims.EMOTE_FREEZE, gfx = 1973, varbit = EmotesTab.FREEZE_EMOTE_VARBIT),
    GIVE_THANKS(component = 46, anim = Anims.RESET, varbit = EmotesTab.GIVE_THANKS_EMOTE_VARBIT),
    AROUND_THE_WORLD(
        component = 47,
        anim = Anims.EMOTE_AROUND_THE_WORLD,
        gfx = 2037,
        varbit = EmotesTab.AROUND_THE_WORLD_EMOTE_VARBIT,
        requiredVarbitValue = 85,
    ),
    DRAMATIC_POINT(component = 48, anim = Anims.EMOTE_DRAMATIC_POINT, gfx = 780, varbit = EmotesTab.DRAMATIC_POINT_EMOTE_VARBIT),
    FAINT(component = 49, anim = Anims.EMOTE_FAINT, varbit = EmotesTab.FAINT_EMOTE_VARBIT),
    PUPPET_MASTER(
        component = 50,
        anim = Anims.EMOTE_PUPPET_MASTER,
        gfx = 2837,
        varbit = EmotesTab.PUPPET_MASTER_EMOTE_VARBIT,
        requiredVarbitValue = 20,
    ),
    TASK_MASTER(
        component = 51,
        anim = Anims.EMOTE_TASK_MASTER,
        gfx = 2930,
        varbit = EmotesTab.TASK_MASTER_EMOTE_VARBIT,
        requiredVarbitValue = 428,
    ),
    SEAL_OF_APPROVAL(component = 52, anim = Anims.RESET, varbit = EmotesTab.SEAL_OF_APPROVAL_EMOTE_VARBIT),
    INVOKE_SPRING(
        component = 53,
        anim = 15357,
        gfx = 1391,
        varbit = EmotesTab.INVOKE_SPRING_EMOTE_VARBIT,
        requiredVarbitValue = 60,
    ),
    ;

    companion object {
        val values = enumValues<Emote>()
    }
}
