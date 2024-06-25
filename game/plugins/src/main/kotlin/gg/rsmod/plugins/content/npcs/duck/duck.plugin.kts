package gg.rsmod.plugins.content.npcs.duck

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 60..120

on_global_npc_spawn {
    if(npc.id == Npcs.DUCK || npc.id == Npcs.DUCK_2693 || npc.id == Npcs.DUCK_6113 || npc.id == Npcs.DUCKLING
        || npc.id == Npcs.DUCKLINGS) {
        npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
        npc.canSwim = true
    }
}

on_timer(FORCE_CHAT_TIMER) {
    if(world.random(1) == 0) {
        npc.forceChat("Quack!")
    } else {
        npc.forceChat("Quack?")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}