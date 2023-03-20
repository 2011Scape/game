package gg.rsmod.plugins.content.skills.farming.data

import gg.rsmod.game.fs.def.ObjectDef
import gg.rsmod.game.model.World
import gg.rsmod.plugins.api.cfg.Objs

enum class Patch(val id: Int, val patchName: String, vararg val seedTypes: SeedType) {
    CatherbyHerb(Objs.HERB_PATCH_8151, "herb patch", SeedType.Herb);

    var varbit: Int = -1
        private set

    val persistenceId = id.toString()

    companion object {
        /**
         * Initializes the definitions and varbits for all patches. This ensures this only needs to be done on startup
         */
        fun initialize(world: World) {
            values().forEach {
                it.varbit = world.definitions.get(ObjectDef::class.java, it.id).varbit
            }
        }

        fun forObject(objectId: Int) = values().firstOrNull { it.id == objectId }

        fun fromPersistenceId(persistenceId: String) = values().first { it.persistenceId == persistenceId }
    }
}
