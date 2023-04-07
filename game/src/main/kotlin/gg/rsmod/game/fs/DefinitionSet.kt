package gg.rsmod.game.fs

import com.displee.cache.CacheLibrary
import gg.rsmod.game.fs.def.*
import gg.rsmod.game.model.Direction
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World
import gg.rsmod.game.model.collision.CollisionManager
import gg.rsmod.game.model.collision.CollisionUpdate
import gg.rsmod.game.model.entity.StaticObject
import gg.rsmod.game.model.region.ChunkSet
import gg.rsmod.game.service.xtea.XteaKeyService
import io.netty.buffer.Unpooled
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import mu.KLogging
import net.runelite.cache.IndexType
import net.runelite.cache.definitions.loaders.LocationsLoader
import net.runelite.cache.definitions.loaders.MapLoader
import java.io.IOException

/**
 * A [DefinitionSet] is responsible for loading any relevant metadata found in
 * the game resources.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class DefinitionSet {

    /**
     * A [Map] holding all definitions with their [Class] as key.
     */
    private val defs = Object2ObjectOpenHashMap<Class<out Definition>, Map<Int, *>>()

    private var xteaService: XteaKeyService? = null

    fun loadAll(store: CacheLibrary) {


        /*
       * Load [VarpDefs]s.
       */
        load(store, VarbitDef::class.java, NonTransmittedVarbitDefinitionProvider.varbits)
        logger.info("Loaded ${getCount(VarbitDef::class.java)} varbit definitions.")

        /*
        * Load [VarpDefs]s.
        */
        load(store, VarpDef::class.java, NonTransmittedVarpDefinitionProvider.varps)
        logger.info("Loaded ${getCount(VarpDef::class.java)} varp definitions.")

        /*
        * Load [EnumDefs]s.
        */
        load(store, EnumDef::class.java)
        logger.info("Loaded ${getCount(EnumDef::class.java)} enum definitions.")

        /*
        * Load [StructDefs]s.
        */
        load(store, StructDef::class.java)
        logger.info("Loaded ${getCount(StructDef::class.java)} struct definitions.")

        /*
       * Load [AnimDefs]s.
       */
        load(store, AnimDef::class.java)
        logger.info("Loaded ${getCount(AnimDef::class.java)} animation definitions.")

        /*
         * Load [ItemDef]s.
         */
        load(store, ItemDef::class.java)
        logger.info("Loaded ${getCount(ItemDef::class.java)} item definitions.")

        /*
         * Load [ObjectDef]s.
         */
        load(store, ObjectDef::class.java)
        logger.info("Loaded ${getCount(ObjectDef::class.java)} object definitions.")

        /*
        * Load [NpcDef]s.
        */
        load(store, NpcDef::class.java)
        logger.info("Loaded ${getCount(NpcDef::class.java)} npc definitions.")
    }

    fun loadRegions(world: World, chunks: ChunkSet, regions: IntArray) {
        val start = System.currentTimeMillis()

        var loaded = 0
        regions.forEach { region ->
            if (chunks.activeRegions.add(region)) {
                if (createRegion(world, region)) {
                    loaded++
                }
            }
        }
        logger.info { "Loaded $loaded regions in ${System.currentTimeMillis() - start}ms" }
    }

    fun <T : Definition> load(store: CacheLibrary, type: Class<out T>, custom: List<T> = listOf()) {
        val archiveType: ArchiveType = when (type) {
            ItemDef::class.java -> ArchiveType.ITEM
            StructDef::class.java -> ArchiveType.STRUCT
            EnumDef::class.java -> ArchiveType.ENUM
            VarpDef::class.java -> ArchiveType.VARP
            VarbitDef::class.java -> ArchiveType.VARBIT
            ObjectDef::class.java -> ArchiveType.OBJECT
            AnimDef::class.java -> ArchiveType.ANIM
            NpcDef::class.java -> ArchiveType.NPC
            else -> throw IllegalArgumentException("Unhandled class type ${type::class.java}.")
        }

        if(archiveType.modernArchive) {
            val length = getIndexSize(store, archiveType.id) + custom.size
            val definitions = Int2ObjectOpenHashMap<T?>(length + 1)
            for (i in 0 until length) {
                val data = store.data(archiveType.id, i ushr archiveType.archiveOperand, i and archiveType.fileOperand) ?: continue
                val def = createDefinition(type, i, data)
                definitions[i] = def
            }
            for (def in custom) {
                definitions[def.id] = def
            }
            defs[type] = definitions
        } else {
            val configs = store.index(archiveType.id)
            val archive = configs.archive(archiveType.subId)
            val files = archive!!.files

            val definitions = Int2ObjectOpenHashMap<T?>(files.size + 1 + custom.size)
            for (i in 0 until files.size) {
                val file = files[i] ?: continue
                val data = file.data ?: continue
                val def = createDefinition(type, file.id, data)
                definitions[file.id] = def
            }
            for (def in custom) {
                definitions[def.id] = def
            }
            defs[type] = definitions
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Definition> createDefinition(type: Class<out T>, id: Int, data: ByteArray): T {
        val def: Definition = when (type) {
            ItemDef::class.java -> ItemDef(id)
            StructDef::class.java -> StructDef(id)
            EnumDef::class.java -> EnumDef(id)
            VarpDef::class.java -> VarpDef(id)
            VarbitDef::class.java -> VarbitDef(id)
            ObjectDef::class.java -> ObjectDef(id)
            AnimDef::class.java -> AnimDef(id)
            NpcDef::class.java -> NpcDef(id)
            else -> throw IllegalArgumentException("Unhandled class type ${type::class.java}.")
        }
        val buf = Unpooled.wrappedBuffer(data)
        def.decode(buf)
        buf.release()
        return def as T
    }

    fun getCount(type: Class<*>) = defs[type]!!.size

    @Suppress("UNCHECKED_CAST")
    fun <T : Definition> get(type: Class<out T>, id: Int): T {
        return (defs[type]!!)[id] as T
    }

    fun <T : Definition> getAllKeys(type: Class<out T>): Set<Int> = defs[type]!!.keys

    @Suppress("UNCHECKED_CAST")
    fun <T : Definition> getNullable(type: Class<out T>, id: Int): T? {
        return (defs[type]!!)[id] as T?
    }

    /**
     * Creates an 8x8 [gg.rsmod.game.model.region.Chunk] region.
     */
    fun createRegion(world: World, id: Int): Boolean {
        if (xteaService == null) {
            xteaService = world.getService(XteaKeyService::class.java)
        }

        val x = id shr 8
        val z = id and 0xFF

        val mapData = world.filestore.data(IndexType.MAPS.number, "m${x}_$z") ?: return false
        val mapDefinition = MapLoader().load(x, z, mapData)

        val cacheRegion = net.runelite.cache.region.Region(id)
        cacheRegion.loadTerrain(mapDefinition)

        val blocked = hashSetOf<Tile>()
        val bridges = hashSetOf<Tile>()
        val water = hashSetOf<Tile>()
        for (height in 0 until 4) {
            for (lx in 0 until 64) {
                for (lz in 0 until 64) {
                    val tileSetting = cacheRegion.getTileSetting(height, lx, lz)
                    val tileOverlay = cacheRegion.getOverlayId(height, lx, lz)
                    val tileOverlayPath = cacheRegion.getOverlayPath(height, lx, lz)
                    val tile = Tile(cacheRegion.baseX + lx, cacheRegion.baseY + lz, height)

                    if ((tileSetting.toInt() and CollisionManager.BLOCKED_TILE) == CollisionManager.BLOCKED_TILE) {
                        blocked.add(tile)
                    }


                    if ((tileSetting.toInt() and CollisionManager.UNKNOWN_TILE) == CollisionManager.UNKNOWN_TILE) {
                        blocked.add(tile.transform(-1))
                    }

                    // Note, Alycia* Grabbing the tile setting (0x200000) should be the "proper" way to do this, but tileSetting
                    // isn't returning water tiles properly. As this is purely to make water npcs "swim", I'm not going to
                    // get too deep into it for now. TODO: do this properly
                    if (tileOverlay == 112 && tileOverlayPath.toInt() == 0) {
                        water.add(tile)
                    }

                    if ((tileSetting.toInt() and CollisionManager.BRIDGE_TILE) == CollisionManager.BRIDGE_TILE) {
                        bridges.add(tile)
                        water.remove(tile)
                        /*
                         * We don't want the bottom of the bridge to be blocked,
                         * so remove the blocked tile if applicable.
                         */
                        if(tileSetting.toInt() != 3) {
                            blocked.remove(tile.transform(-1))
                        }
                        water.remove(tile.transform(-1))
                    }
                }
            }
        }

        /*
         * Apply the blocked tiles to the collision detection.
         */
        val blockedTileBuilder = CollisionUpdate.Builder()
        blockedTileBuilder.setType(CollisionUpdate.Type.ADD)
        blocked.forEach { tile ->
            world.chunks.getOrCreate(tile).blockedTiles.add(tile)
            blockedTileBuilder.putTile(tile, false, *Direction.NESW)
        }
        water.forEach { tile ->
            world.chunks.getOrCreate(tile).waterTiles.add(tile)
        }
        world.collision.applyUpdate(blockedTileBuilder.build())

        if (xteaService == null) {
            /*
             * If we don't have an [XteaKeyService], then we assume we don't
             * need to decrypt the files through xteas. This means the objects
             * from each region has to be decrypted a different way.
             *
             * If this is the case, you need to use [gg.rsmod.game.model.region.Chunk.addEntity]
             * to add the object to the world for collision detection.
             */
            return true
        }

        val keys = xteaService?.get(id) ?: XteaKeyService.EMPTY_KEYS
        try {
            val landData = world.filestore.data(IndexType.MAPS.number, "l${x}_$z", keys) ?: return false
            val locDef = LocationsLoader().load(x, z, landData)
            cacheRegion.loadLocations(locDef)

            cacheRegion.locations.forEach { loc ->
                val tile = Tile(loc.position.x, loc.position.y, loc.position.z)
                if (bridges.contains(tile.transform(1))) {
                    return@forEach
                }
                val obj = StaticObject(loc.id, loc.type, loc.orientation, if (bridges.contains(tile)) tile.transform(-1) else tile)
                world.chunks.getOrCreate(tile).addEntity(world, obj, obj.tile)
            }
            return true
        } catch (e: IOException) {
            logger.error("Could not decrypt map region {}.", id)
            return false
        }
    }

    /**
     * Calculate the size of any given index in the cache
     */
    private fun getIndexSize(store: CacheLibrary, index: Int) : Int {
        val lastArchiveId = store.index(index).archives().size
        return lastArchiveId * 0x3ff
    }

    companion object : KLogging()
}