package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.game.fs.def.ObjectDef
import gg.rsmod.game.model.World
import gg.rsmod.plugins.api.cfg.Objs

enum class Patch(val id: Int) {
    CatherbyHerb(Objs.HERB_PATCH_8151);

    var varbit: Int = -1
        private set

    companion object {

        /**
         * Initializes the varbits for all patches. This ensures this only needs to be done on startup
         */
        fun initialize(world: World) {
            values().forEach {
                it.varbit = world.definitions.get(ObjectDef::class.java, it.id).varbit
            }
        }
    }
}
