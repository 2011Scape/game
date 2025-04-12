package gg.rsmod.plugins.content.combat

import gg.rsmod.game.model.Animation
import gg.rsmod.plugins.api.cfg.Anims

enum class Animations(
    val default: Animation = Animation(-1),
    val stab: Animation = Animation(-1),
    val slash: Animation = Animation(-1),
    val crush: Animation = Animation(-1),
    val block: Animation = Animation(-1),
) {
    // Weapons
    PUNCH(crush = Animation(Anims.ATTACK_PUNCH)),
    KICK(crush = Animation(Anims.ATTACK_KICK)),
    BLOCK(block = Animation(Anims.BLOCK_UNARMED)),
    BOW(default = Animation(Anims.ATTACK_BOW)),
    CROSSBOW(default = Animation(Anims.ATTACK_CROSSBOW)),
    THROWING_KNIFE(default = Animation(Anims.ATTACK_THROWN)),
    THROWING_DART(default = Animation(Anims.ATTACK_DART)),
    TOKTZ_XIL_UL(default = Animation(Anims.ATTACK_TOKTZXILUL)),

    DRAGON_DAGGER(
        block = Animation(Anims.BLOCK_LOW),
        slash = Animation(Anims.ATTACK_DAGGER_SLASH),
        stab = Animation(Anims.ATTACK_DAGGER_STAB),
    ),
    SHORTSWORD(
        block = Animation(Anims.BLOCK_LOW),
        stab = Animation(Anims.ATTACK_LUNGE),
        slash = Animation(Anims.ATTACK_SLASH),
    ),
    SLING(
        default = Animation(Anims.ATTACK_SLING),
        block = Animation(Anims.BLOCK_WHIP),
    ),
    TWO_HANDED(
        block = Animation(Anims.BLOCK_GODSWORD),
        slash = Animation(Anims.ATTACK_GODSWORD_SLASH),
        crush = Animation(Anims.ATTACK_GODSWORD_WHACK),
    ),
    PICKAXE(
        block = Animation(Anims.BLOCK_LOW),
        slash = Animation(Anims.ATTACK_2H_SLASH),
        crush = Animation(Anims.ATTACK_PICKAXE_CRUSH),
    ),
    HALBERD(
        block = Animation(Anims.BLOCK_HALBERD),
        slash = Animation(Anims.ATTACK_HALBERD_SWIPE),
        stab = Animation(Anims.ATTACK_HALBERD_JAB),
    ),
    MACE(
        block = Animation(Anims.BLOCK_MACE),
        stab = Animation(Anims.ATTACK_STAB),
        crush = Animation(Anims.ATTACK_CRUSH),
    ),
    SPEAR(
        block = Animation(Anims.BLOCK_SPEAR),
        stab = Animation(Anims.ATTACK_SPEAR_STAB),
        slash = Animation(Anims.ATTACK_SPEAR_SLASH),
        crush = Animation(Anims.ATTACK_SPEAR_CRUSH),
    ),

    WARHAMMER(
        block = Animation(403),
        crush = Animation(401),
    ),
    ZAMORAKIAN_SPEAR(
        crush = Animation(380),
        stab = Animation(381),
        slash = Animation(382),
        block = Animation(383),
    ),
    CLAWS(
        slash = Animation(393),
        block = Animation(397),
        stab = Animation(1067),
    ),
    WHIP(
        slash = Animation(11968),
        block = Animation(11974),
    ),
    STAFF(
        crush = Animation(419),
        block = Animation(420),
    ),
    LONGSWORD(
        stab = Animation(15072),
        slash = Animation(15071),
        block = Animation(15074),
    ),
    SCYTHE(
        stab = Animation(440),
        slash = Animation(440),
        crush = Animation(440),
        block = Animation(440),
    ),

    // Shields
    DEFENDER(block = Animation(4177)),
    SHIELD(block = Animation(1156)),
}
