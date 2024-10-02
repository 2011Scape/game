package gg.rsmod.plugins.content.drops

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.cfg.Items
import io.mockk.every
import io.mockk.mockk
import java.security.SecureRandom
import kotlin.test.Test
import kotlin.test.assertEquals

class DropTableFactoryTest {
    private val factory = DropTableFactory
    private val randomMock: SecureRandom = mockk()
    private val player: Player = mockk()

    private val npcId = 10

    init {
        factory.prng = randomMock
    }

    @Test
    fun test() {
        val indices = listOf(0, 50, 239, 359, 360, 449, 450, 451, 1000, 1023)
        every { randomMock.nextInt(1024) } returnsMany indices

        val table =
            factory.build {
                main {
                    total(1024)

                    obj(Items.TIN_ORE, slots = 240)
                    obj(Items.COPPER_ORE, slots = 120)
                    obj(Items.IRON_ORE, slots = 60)
                    obj(Items.COAL, slots = 30)
                    obj(Items.GOLD_ORE, slots = 1)
                    nothing(573)
                }
            }

        factory.register(table, npcId)

        val drops = indices.map { factory.getDrop(player, npcId) }

        assertEquals(Items.TIN_ORE, drops[0]!!.single().id)
        assertEquals(Items.TIN_ORE, drops[1]!!.single().id)
        assertEquals(Items.TIN_ORE, drops[2]!!.single().id)
        assertEquals(Items.COPPER_ORE, drops[3]!!.single().id)
        assertEquals(Items.IRON_ORE, drops[4]!!.single().id)
        assertEquals(Items.COAL, drops[5]!!.single().id)
        assertEquals(Items.GOLD_ORE, drops[6]!!.single().id)
        assertEquals(0, drops[7]!!.size)
        assertEquals(0, drops[8]!!.size)
        assertEquals(0, drops[9]!!.size)
    }
}
