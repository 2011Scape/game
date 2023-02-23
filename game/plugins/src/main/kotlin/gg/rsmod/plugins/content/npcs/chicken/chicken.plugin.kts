package gg.rsmod.plugins.content.npcs.chicken

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 150..300

on_global_npc_spawn {
    when (npc.id) {
        //TODO: Add more chickens, these are the only two types spawned at the moment.
        Npcs.CHICKEN, Npcs.CHICKEN_1017, Npcs.CHICKEN -> npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
    }
}

on_timer(FORCE_CHAT_TIMER) {
    npc.forceChat("Squawk!")
    npc.timers[FORCE_CHAT_TIMER] = DELAY.random()
}