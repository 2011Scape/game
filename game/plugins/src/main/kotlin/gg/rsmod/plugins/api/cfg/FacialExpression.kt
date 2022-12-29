package gg.rsmod.plugins.api.cfg

/**
 * Represents the facial expressions (the animations the entity does when
 * talking).
 * @author Emperor
 * @author Empathy
 */
enum class FacialExpression(val animationId: Int) {

    NORMAL(9760),
    ANGRY(9792),
    GRUMPY(9784),
    ANNOYED(9812),
    SNEAKY(595),
    SAD(9776),
    DISTRESSED(9820),
    HAPPY(9851),
    NEARLY_CRYING(9836),
    CHILD_QUESTIONABLE(7171),
    CHILD_BACK_AND_FORTH(7172),
    CHILD_NORMAL(7173),
    CHILD_SLOW_NOD(7174),
    CHILD_CRAZY_LAUGH(7175),
    CHILD_THINKING(7176),
    CHILD_SAD(7177),
    CHILD_BIG_EYES(7178),
    CHILD_LOOKING_OUT(7179);
}