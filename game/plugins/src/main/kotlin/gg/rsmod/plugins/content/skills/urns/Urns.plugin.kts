package gg.rsmod.plugins.content.skills.urns

object Urns {
/*
    var handleTeleportUrns: ItemClickHandler? =
        ItemClickHandler(Urn.FULL_IDS.keySet().toArray(), kotlin.arrayOf<kotlin.String?>("Teleport urn")) { e ->
            val urn: Urn? = Urn.forFullId(e.getItem().getId())
            e.getPlayer().getInventory().deleteItem(e.getItem().getId(), 1)
            e.getPlayer().setNextAnimation(urn.getTeleAnim())
            e.getPlayer().getSkills().addXpLamp(urn.getSkill(), urn.getTeleXp())
        }

    var handleCheckUrns: ItemClickHandler? =
        ItemClickHandler(Urn.FILL_IDS.keySet().toArray(), kotlin.arrayOf<kotlin.String?>("Check level")) { e ->
            val urn: Urn? = Urn.forFillId(e.getItem().getId())
            e.getPlayer().sendMessage(
                ("The urn is filled " + Utils.formatDouble(
                    e.getItem().getMetaDataD("xp") / urn.getFillXp() * 100.0
                )).toString() + "%."
            )
        }

    var addRune: ItemOnItemHandler? =
        ItemOnItemHandler(Urn.NR_IDS.keySet().stream().mapToInt { i -> i }.toArray()) label@{ e ->
            val u: Urn? = Urn.forNRId(e.getItem1().getId())
            if (u == null) u = Urn.forNRId(e.getItem2().getId())
            if (u == null) return@label
            val urn: Urn = u
            val rune: Rune = Rune.forId(e.getUsedWith(urn.nrId()).getId())
            if (rune === urn.getRune()) e.getPlayer().startConversation(Conversation(e.getPlayer())
                .addNext(MakeXStatement(kotlin.intArrayOf(urn.rId())))
                .addNext {
                    val amount: Int = e.getPlayer().getInventory().getNumberOf(urn.nrId())
                    if (amount > e.getPlayer().getInventory().getNumberOf(urn.getRune().id())) amount =
                        e.getPlayer().getInventory().getNumberOf(urn.getRune().id())
                    e.getPlayer().getInventory().deleteItem(urn.nrId(), amount)
                    e.getPlayer().getInventory().deleteItem(urn.getRune().id(), amount)
                    e.getPlayer().getInventory().addItem(urn.rId(), amount)
                    e.getPlayer().getSkills().addXpLamp(urn.getSkill(), amount)
                    e.getPlayer().setNextAnimation(urn.getReadyAnim())
                })
            else e.getPlayer().sendMessage(
                ("You must use " + Utils.addArticle(
                    urn.getRune().name().toLowerCase()
                )).toString() + " rune to activate this urn."
            )
        }

    var handleUrnXp: XPGainHandler? = XPGainHandler { e ->
        when (e.getSkillId()) {
            Constants.SMITHING, Constants.WOODCUTTING, Constants.FISHING, Constants.COOKING, Constants.MINING -> Urns.addXPToUrn(
                e.getPlayer(),
                getUrn(e.getPlayer(), e.getSkillId()),
                e.getXp()
            )
        }
    }

    var handle: NPCDropHandler? = NPCDropHandler(null, kotlin.arrayOf<kotlin.Any?>(20264, 20266, 20268)) { e ->
        when (e.getItem().getId()) {
            20264 -> if (Urns.addXPToUrn(
                    e.getPlayer(),
                    Urns.getUrn(e.getPlayer(), Urn.IMPIOUS, Urn.ACCURSED, Urn.INFERNAL),
                    4.0
                )
            ) e.deleteItem()

            20266 -> if (Urns.addXPToUrn(
                    e.getPlayer(),
                    Urns.getUrn(e.getPlayer(), Urn.ACCURSED, Urn.INFERNAL),
                    12.5
                )
            ) e.deleteItem()

            20268 -> if (Urns.addXPToUrn(
                    e.getPlayer(),
                    getUrn(e.getPlayer(), Urn.INFERNAL),
                    62.5
                )
            ) e.deleteItem()
        }
    }

    fun getUrn(player: Player?, skillId: kotlin.Int): Urn? {
        for (urn in Urn.values()) if (urn.getSkill() === skillId && player.getInventory()
                .containsOneItem(urn.rId(), urn.fillId())
        ) return urn
        return null
    }

    fun getUrn(player: Player?, vararg urns: Urn?): Urn? {
        for (urn in urns) if (player.getInventory().containsOneItem(urn.rId(), urn.fillId())) return urn
        return null
    }

    fun addXPToUrn(player: Player?, urn: Urn?, xp: kotlin.Double): kotlin.Boolean {
        if (urn == null) return false
        val item: Item = player.getItemFromInv(urn.fillId())
        if (item != null) {
            val newXp: kotlin.Double = item.getMetaDataD("xp") + xp
            if (newXp >= urn.getFillXp()) {
                newXp = urn.getFillXp()
                player.incrementCount(item.getName() + " filled")
                item.setId(urn.fullId())
                item.deleteMetaData()
                player.getInventory().refresh()
            } else item.addMetaData("xp", newXp)
            player.sendMessage(
                ("<col=e69d00><shad=000000>Your urn is filled " + Utils.formatDouble(newXp / urn.getFillXp() * 100)).toString() + "%.",
                true
            )
            return true
        }
        item = player.getItemFromInv(urn.rId())
        if (item != null) {
            if (!player.getInventory().hasFreeSlots()) return false
            player.getInventory().deleteItem(urn.rId(), 1)
            val fUrn: Item = Item(urn.fillId(), 1)
            fUrn.addMetaData("xp", xp)
            player.sendMessage("<col=e69d00><shad=000000>You start a new urn.", true)
            player.getInventory().addItem(fUrn)
            return true
        }
        return false

    }*/
}
