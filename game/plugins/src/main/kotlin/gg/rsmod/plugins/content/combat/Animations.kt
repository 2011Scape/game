package gg.rsmod.plugins.content.combat

import gg.rsmod.game.model.Animation

enum class Animations(
    val start: Animation = Animation(-1),
    val end: Animation = Animation(-1),
    val stab: Animation = Animation(-1),
    val slash: Animation = Animation(-1),
    val crush: Animation = Animation(-1),
    val block: Animation = Animation(-1),
) {

    /*Weapons*/
    PUNCH(crush = Animation(422)),
    KICK(crush = Animation(423)),
    BLOCK(block = Animation(424)),
    BOW(start = Animation(426)),
    CROSSBOW(start = Animation(4230)),
    THROWING_KNIFE(start = Animation(929)),
    THROWING_DART(start = Animation(6600)),
    TOKTZ_XIL_UL(start = Animation(2614)),

    DRAGON_DAGGER(
        block = Animation(378),
        slash = Animation(377),
        stab = Animation(376)
    ),
    SHORTSWORD(
        block = Animation(378),
        stab = Animation(386),
        slash = Animation(390),
    ),
    SLING(
        start = Animation(789),
        block = Animation(11974)
    ),
    TWO_HANDED(
        block = Animation(7050),
        slash = Animation(7041),
        crush = Animation(7048),
    ),
    PICKAXE(
        block = Animation(378),
        slash = Animation(407),
        crush = Animation(410)
    ),
    HALBERD(
        block = Animation(435),
        slash = Animation(440),
        stab = Animation(438)
    ),
    MACE(
        block = Animation(403),
        stab = Animation(400),
        crush = Animation(401),
    ),
    SPEAR(
        block = Animation(430),
        stab = Animation(428),
        slash = Animation(429),
        crush = Animation(383),
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
        block = Animation(420)
    ),
    LONGSWORD(
        stab = Animation(15072),
        slash = Animation(15071),
        block = Animation(15074),
    ),

    /*Shields*/
    DEFENDER(block = Animation(4177)),
    SHIELD(block = Animation(1156)),
}