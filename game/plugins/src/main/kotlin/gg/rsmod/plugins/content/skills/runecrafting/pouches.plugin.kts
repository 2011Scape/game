package gg.rsmod.plugins.content.skills.runecrafting

import kotlin.math.*

    enum class Pouch(val id: Int, val capacity: Int, val requiredLevel: Int) {
        SMALL_POUCH(Items.SMALL_POUCH, 3, 1),
        MEDIUM_POUCH(Items.MEDIUM_POUCH, 6, 25),
        LARGE_POUCH(Items.LARGE_POUCH, 9, 50),
        GIANT_POUCH(Items.GIANT_POUCH, 12, 75)
    }

    val SMALL_POUCH_VARP = 760
    val MEDIUM_POUCH_VARP = 761
    val LARGE_POUCH_VARP = 762
    val GIANT_POUCH_VARP = 763

    val SMALL_POUCH_DEGRADATION_VARP = 770
    val MEDIUM_POUCH_DEGRADATION_VARP = 771
    val LARGE_POUCH_DEGRADATION_VARP = 772
    val GIANT_POUCH_DEGRADATION_VARP = 773

    val darkMage = Npcs.DARK_MAGE_2262

    on_npc_option(darkMage, option = "repair-pouches") {
        player.queue {
            restoreAllPouches(player)
        }
    }

    on_item_option(item = Items.GIANT_POUCH, option = "fill") {
        player.queue {
            fillPouch(this, player, Pouch.GIANT_POUCH)
        }
    }

    on_item_option(item = Items.LARGE_POUCH, option = "fill") {
        player.queue {
            fillPouch(this, player, Pouch.LARGE_POUCH)
        }
    }

    on_item_option(item = Items.MEDIUM_POUCH, option = "fill") {
        player.queue {
            fillPouch(this, player, Pouch.MEDIUM_POUCH)
        }
    }

    on_item_option(item = Items.SMALL_POUCH, option = "fill") {
        player.queue {
            fillPouch(this, player, Pouch.SMALL_POUCH)
        }
    }

    on_item_option(item = Items.GIANT_POUCH_5515, option = "fill") {
        player.message("Your giant pouch is worn out. Talk to the Dark mage to repair it.")
    }

    on_item_option(item = Items.LARGE_POUCH_5513, option = "fill") {
        player.message("Your large pouch is worn out. Talk to the Dark mage to repair it.")
    }

    on_item_option(item = Items.MEDIUM_POUCH_5511, option = "fill") {
        player.message("Your medium pouch is worn out. Talk to the Dark mage to repair it.")
    }

    on_item_option(item = Items.GIANT_POUCH, option = "empty") {
        player.queue {
            emptyPouch(this, player, Pouch.GIANT_POUCH)
        }
    }

    on_item_option(item = Items.LARGE_POUCH, option = "empty") {
        player.queue {
            emptyPouch(this, player, Pouch.LARGE_POUCH)
        }
    }

    on_item_option(item = Items.MEDIUM_POUCH, option = "empty") {
        player.queue {
            emptyPouch(this, player, Pouch.MEDIUM_POUCH)
        }
    }
    on_item_option(item = Items.SMALL_POUCH, option = "empty") {
        player.queue {
            emptyPouch(this, player, Pouch.SMALL_POUCH)
        }
    }

    on_item_option(item = Items.GIANT_POUCH_5515, option = "empty") {
        player.message("Your giant pouch is worn out. Talk to the Dark mage to repair it.")
    }

    on_item_option(item = Items.LARGE_POUCH_5513, option = "empty") {
        player.message("Your large pouch is worn out. Talk to the Dark mage to repair it.")
    }

    on_item_option(item = Items.MEDIUM_POUCH_5511, option = "empty") {
        player.message("Your medium pouch is worn out. Talk to the Dark mage to repair it.")
    }

    on_item_option(item = Items.GIANT_POUCH, option = "check") {
        player.queue {
            checkPouch(player, Pouch.GIANT_POUCH)
        }
    }

    on_item_option(item = Items.LARGE_POUCH, option = "check") {
        player.queue {
            checkPouch(player, Pouch.LARGE_POUCH)
        }
    }

    on_item_option(item = Items.MEDIUM_POUCH, option = "check") {
        player.queue {
            checkPouch(player, Pouch.MEDIUM_POUCH)
        }
    }

    on_item_option(item = Items.SMALL_POUCH, option = "check") {
        player.queue {
            checkPouch(player, Pouch.SMALL_POUCH)
        }
    }

    on_item_option(item = Items.GIANT_POUCH_5515, option = "check") {
        player.message("Your giant pouch is worn out. Talk to the Dark mage to repair it.")
        restorePouch(player, Pouch.GIANT_POUCH)
    }

    on_item_option(item = Items.LARGE_POUCH_5513, option = "check") {
        player.message("Your giant pouch is worn out. Talk to the Dark mage to repair it.")
        restorePouch(player, Pouch.LARGE_POUCH)
    }

    on_item_option(item = Items.MEDIUM_POUCH_5511, option = "check") {
        player.message("Your medium pouch is worn out. Talk to the Dark mage to repair it.")
        restorePouch(player, Pouch.GIANT_POUCH)
    }

    fun restorePouch(player: Player, pouch: Pouch) {
        if (pouch == Pouch.GIANT_POUCH) {
            player.setVarp(pouchDegradationVarpId(pouch), 0)
            player.message("Your ${pouch.name.lowercase().replace("_", " ")} has been restored.")
        }
        if (pouch == Pouch.LARGE_POUCH) {
            player.setVarp(pouchDegradationVarpId(pouch), 0)
            player.message("Your ${pouch.name.lowercase().replace("_", " ")} has been restored.")
        }
        if (pouch == Pouch.MEDIUM_POUCH) {
            player.setVarp(pouchDegradationVarpId(pouch), 0)
            player.message("Your ${pouch.name.lowercase().replace("_", " ")} has been restored.")
        }else {
            player.message("This pouch does not degrade.")
        }
    }

    fun restoreAllPouches(player: Player) {
        val pouchesToRepair = listOf(
            PouchRepairInfo(Items.GIANT_POUCH, Items.GIANT_POUCH, Pouch.GIANT_POUCH, "giant"),
            PouchRepairInfo(Items.LARGE_POUCH, Items.LARGE_POUCH, Pouch.LARGE_POUCH, "large"),
            PouchRepairInfo(Items.MEDIUM_POUCH, Items.MEDIUM_POUCH, Pouch.MEDIUM_POUCH, "medium") ,
            PouchRepairInfo(Items.GIANT_POUCH_5515, Items.GIANT_POUCH, Pouch.GIANT_POUCH, "giant"),
            PouchRepairInfo(Items.LARGE_POUCH_5513, Items.LARGE_POUCH, Pouch.LARGE_POUCH, "large"),
            PouchRepairInfo(Items.MEDIUM_POUCH_5511, Items.MEDIUM_POUCH, Pouch.MEDIUM_POUCH, "medium")
        )

        pouchesToRepair.forEach { pouchInfo ->
            if (player.inventory.contains(pouchInfo.degradedItemId)) {
                player.inventory.remove(pouchInfo.degradedItemId)
                player.inventory.add(pouchInfo.repairedItemId)
                player.setVarp(pouchDegradationVarpId(pouchInfo.pouch), 0)
                player.message("The Dark mage has repaired your ${pouchInfo.pouchSize} pouch.")
            }
        }
    }

    data class PouchRepairInfo(
        val degradedItemId: Int,
        val repairedItemId: Int,
        val pouch: Pouch,
        val pouchSize: String
    )

    suspend fun fillPouch(it: QueueTask, player: Player, pouch: Pouch) {
        if (player.skills.getMaxLevel(Skills.RUNECRAFTING) < pouch.requiredLevel) {
            it.messageBox("You need a Runecrafting level of ${pouch.requiredLevel} to use this pouch.")
            return
        }

        if (!player.inventory.contains(Items.RUNE_ESSENCE) && !player.inventory.contains(Items.PURE_ESSENCE)) {
            player.message("You don't have any essence to fill your ${pouch.name.lowercase().replace("_", " ")} with.")
            return
        }

        val currentAmount = player.getVarp(pouchVarpId(pouch))
        if (currentAmount >= pouch.capacity) {
            player.message("Your ${pouch.name.lowercase().replace("_", " ")} is already full.")
            return
        }

        val degradationCount = player.getPouchDegradation(pouch)
        val degradationCapacityLoss = when (pouch) {
            Pouch.GIANT_POUCH -> degradationCount - 10
            Pouch.LARGE_POUCH -> degradationCount - 34
            Pouch.MEDIUM_POUCH -> degradationCount - 44
            else -> 0
        }
        val remainingCapacity = maxOf(pouch.capacity - degradationCapacityLoss, 0)

        val maxCapacity = remainingCapacity - currentAmount

        val runeEssenceToAdd = min(maxCapacity, player.inventory.getItemCount(Items.RUNE_ESSENCE))
        val pureEssenceToAdd = min(maxCapacity - runeEssenceToAdd, player.inventory.getItemCount(Items.PURE_ESSENCE))
        val totalEssenceToAdd = runeEssenceToAdd + pureEssenceToAdd

        player.inventory.remove(Items.RUNE_ESSENCE, runeEssenceToAdd)
        player.inventory.remove(Items.PURE_ESSENCE, pureEssenceToAdd)

        player.setVarp(pouchVarpId(pouch), currentAmount + totalEssenceToAdd)
        player.message("You fill your ${pouch.name.lowercase().replace("_", " ")} with $totalEssenceToAdd essence.")

        degradePouch(player, pouch)
    }

    suspend fun emptyPouch(it: QueueTask, player: Player, pouch: Pouch) {
        val currentAmount = player.getPouchAmount(pouch)
        if (currentAmount == 0) {
            player.message("Your ${pouch.name.lowercase().replace("_", " ")} is already empty.")
            return
        }

        if (player.inventory.isFull) {
            it.messageBox("Your inventory is too full to take anything out of the pouch.")
            return
        }

        val spaceInInventory = player.inventory.freeSlotCount
        val essenceToAdd = minOf(spaceInInventory, currentAmount)

        player.inventory.add(Items.PURE_ESSENCE, essenceToAdd)
        player.setPouchAmount(pouch, currentAmount - essenceToAdd)

        player.message("You empty $essenceToAdd essence from your ${pouch.name.lowercase().replace("_", " ")} to your inventory.")
    }

    fun checkPouch(player: Player, pouch: Pouch) {
        val currentAmount = player.getPouchAmount(pouch)
        player.message("You currently have $currentAmount essence stored in your ${pouch.name.lowercase().replace("_", " ")}.")
    }

    fun degradePouch(player: Player, pouch: Pouch) {
        if (pouch == Pouch.GIANT_POUCH) {
            val degradationCount = player.getPouchDegradation(pouch)
            if (degradationCount >= 10) {
                val remainingCapacity = pouch.capacity - (degradationCount - 10)
                if (remainingCapacity > 0) {
                    player.setPouchAmount(pouch, minOf(player.getPouchAmount(pouch), remainingCapacity))
                    player.message("Your ${pouch.name.lowercase().replace("_", " ")} has degraded and can now hold fewer essence.")
                } else {
                    player.inventory.remove(pouch.id)
                    player.inventory.add(Items.GIANT_POUCH_5515)
                    player.message("Your ${pouch.name.lowercase().replace("_", " ")} has degraded completely and can no longer hold essence.")
                }
            }
            player.setPouchDegradation(pouch, degradationCount + 1)
        }
        if (pouch == Pouch.LARGE_POUCH) {
            val degradationCount = player.getPouchDegradation(pouch)
            if (degradationCount >= 34) {
                val remainingCapacity = pouch.capacity - (degradationCount - 34)
                if (remainingCapacity > 0) {
                    player.setPouchAmount(pouch, minOf(player.getPouchAmount(pouch), remainingCapacity))
                    player.message("Your ${pouch.name.lowercase().replace("_", " ")} has degraded and can now hold fewer essence.")
                } else {
                    player.inventory.remove(pouch.id)
                    player.inventory.add(Items.LARGE_POUCH_5513)
                    player.message("Your ${pouch.name.lowercase().replace("_", " ")} has degraded completely and can no longer hold essence.")
                }
            }
            player.setPouchDegradation(pouch, degradationCount + 1)
        }
        if (pouch == Pouch.MEDIUM_POUCH) {
            val degradationCount = player.getPouchDegradation(pouch)
            if (degradationCount >= 44) {
                val remainingCapacity = pouch.capacity - (degradationCount - 44)
                if (remainingCapacity > 0) {
                    player.setPouchAmount(pouch, minOf(player.getPouchAmount(pouch), remainingCapacity))
                    player.message("Your ${pouch.name.lowercase().replace("_", " ")} has degraded and can now hold fewer essence.")
                } else {
                    player.inventory.remove(pouch.id)
                    player.inventory.add(Items.MEDIUM_POUCH_5511)
                    player.message("Your ${pouch.name.lowercase().replace("_", " ")} has degraded completely and can no longer hold essence.")
                }
            }
            player.setPouchDegradation(pouch, degradationCount + 1)
        }
    }

    fun Player.getPouchAmount(pouch: Pouch): Int {
        return this.getVarp(pouchVarpId(pouch))
    }

    fun Player.setPouchAmount(pouch: Pouch, amount: Int) {
        this.setVarp(pouchVarpId(pouch), amount)
    }

    fun Player.getPouchDegradation(pouch: Pouch): Int {
        return this.getVarp(pouchDegradationVarpId(pouch))
    }

    fun Player.setPouchDegradation(pouch: Pouch, degradation: Int) {
        this.setVarp(pouchDegradationVarpId(pouch), degradation)
    }

    fun pouchVarpId(pouch: Pouch): Int {
        return when (pouch) {
            Pouch.SMALL_POUCH -> SMALL_POUCH_VARP
            Pouch.MEDIUM_POUCH -> MEDIUM_POUCH_VARP
            Pouch.LARGE_POUCH -> LARGE_POUCH_VARP
            Pouch.GIANT_POUCH -> GIANT_POUCH_VARP
        }
    }

    fun pouchDegradationVarpId(pouch: Pouch): Int {
        return when (pouch) {
            Pouch.SMALL_POUCH -> SMALL_POUCH_DEGRADATION_VARP
            Pouch.MEDIUM_POUCH -> MEDIUM_POUCH_DEGRADATION_VARP
            Pouch.LARGE_POUCH -> LARGE_POUCH_DEGRADATION_VARP
            Pouch.GIANT_POUCH -> GIANT_POUCH_DEGRADATION_VARP
        }
    }