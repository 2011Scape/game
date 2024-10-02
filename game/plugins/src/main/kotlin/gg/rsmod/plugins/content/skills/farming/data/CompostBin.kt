package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.game.fs.def.ObjectDef
import gg.rsmod.game.model.World
import gg.rsmod.plugins.api.cfg.Objs

enum class CompostBin(
    val id: Int,
) {
    Falador(Objs.COMPOST_BIN_7836),
    Catherby(Objs.COMPOST_BIN_7837),
    Morytania(Objs.COMPOST_BIN_7838),
    Ardougne(Objs.COMPOST_BIN_7839),
    ;

    var varbit: Int = -1
        private set

    companion object {
        /**
         * Initializes the definitions and varbits for all compost bins. This ensures this only needs to be done on startup
         */
        fun initialize(world: World) {
            values().forEach {
                it.varbit = world.definitions.get(ObjectDef::class.java, it.id).varbit
            }
        }

        fun byCompostBinId(id: Int) = values().firstOrNull { it.id == id }
    }
}
