package gg.rsmod.plugins.content.npcs.cow

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 140..260

on_global_npc_spawn {
    when (npc.id) {
        Npcs.COW, Npcs.COW_CALF, Npcs.COW_397, Npcs.COW_1767 -> npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
    }
}

on_timer(FORCE_CHAT_TIMER) {
    npc.forceChat("Moo")
    npc.timers[FORCE_CHAT_TIMER] = DELAY.random()
}