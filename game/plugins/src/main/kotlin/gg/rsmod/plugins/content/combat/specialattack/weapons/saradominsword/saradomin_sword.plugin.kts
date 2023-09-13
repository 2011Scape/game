package gg.rsmod.plugins.content.combat.specialattack.weapons.saradominsword

import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.getCombatTarget
import gg.rsmod.plugins.content.combat.specialattack.SpecialAttacks

val SPECIAL_REQUIREMENT = 0
val SARASWORD_SPEC_ANIMATION = 11993
val SARASWORD_SPEC_PLAYER_GFX = 2115
val SARASWORD_SPEC_TARGET_GFX = 1194
val MAGIC_DAMAGE_MAX_HIT = 16.0
val SARASWORD_SPEC_SFX_ID1 = 3887 // According to https://www.runelister.com/forum/topic/osrs-sound-effect-list/
val SARASWORD_SPEC_SFX_ID2 = 3869
/**
 * The Saradomin sword has a special attack, Saradomin's Lightning, that deals 10% more melee damage and 1-16 extra Magic damage.
 * This special attack consumes 100% of the wielder's special attack energy.
 *
 * The special attack rolls against the opponent's Slash defence bonus using the attack bonus of the stance the player is currently in.
 * If the melee attack misses, then the Magic attack will also fail. However, if the melee hit is a successful hit but rolls a 0, the Magic damage will still be applied.
 * Players receive 2 Magic experience for each point of damage caused by the extra Magic damage.
 *
 * The magical part of Saradomin's Lightning will always splash when directed at players using Protect from Magic.
 */

SpecialAttacks.register(SPECIAL_REQUIREMENT, Items.SARADOMIN_SWORD) {
    val maxHit = MeleeCombatFormula.getMaxHit(player, target, specialAttackMultiplier = 1.10)
    val accuracy = MeleeCombatFormula.getAccuracy(player, target)
    val landHit = accuracy >= world.randomDouble()
    val delay = 1
    val firstHit = player.dealHit(
        target = target,
        maxHit = maxHit,
        landHit = landHit,
        delay = delay,
        hitType = HitType.MELEE
    )

    if (firstHit.landed) {
        world.spawn(AreaSound(tile = player.tile, id = SARASWORD_SPEC_SFX_ID1, radius = 10, volume = 1))
        world.spawn(AreaSound(tile = player.tile, id = SARASWORD_SPEC_SFX_ID2, radius = 10, volume = 1))

        player.animate(id = SARASWORD_SPEC_ANIMATION)
        player.graphic(SARASWORD_SPEC_PLAYER_GFX)
        target.graphic(SARASWORD_SPEC_TARGET_GFX)

        val magicDamage = if (target.prayerIcon == PrayerIcon.PROTECT_FROM_MAGIC.id) 0 else world.random(0..16)
        player.addXp(Skills.MAGIC, 2.0*magicDamage, modifiers = false)

        player.dealHit(
            target = target,
            maxHit = MAGIC_DAMAGE_MAX_HIT,
            landHit = true,
            delay = 1,
            hitType = HitType.MAGIC
        )
    }
}