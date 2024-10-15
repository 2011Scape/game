package gg.rsmod.plugins.content.objs

on_item_on_obj(obj = Objs.CAULDRON_OF_THUNDER, item = Items.RAW_BEEF) { // adds interaction between meats and cauldron in tav dungeon
    player.animate(883) // animation for dipping meat into cauldron
    player.inventory.remove(Items.RAW_BEEF)
    player.inventory.add(Items.ENCHANTED_BEEF)
}
on_item_on_obj(obj = Objs.CAULDRON_OF_THUNDER, item = Items.RAW_CHICKEN) {
    player.animate(883)
    player.inventory.remove(Items.RAW_CHICKEN)
    player.inventory.add(Items.ENCHANTED_CHICKEN)
}
on_item_on_obj(obj = Objs.CAULDRON_OF_THUNDER, item = Items.RAW_BEAR_MEAT) {
    player.animate(883)
    player.inventory.remove(Items.RAW_BEAR_MEAT)
    player.inventory.add(Items.ENCHANTED_BEAR_MEAT)
}
on_item_on_obj(obj = Objs.CAULDRON_OF_THUNDER, item = Items.RAW_RAT_MEAT) {
    player.animate(883)
    player.inventory.remove(Items.RAW_RAT_MEAT)
    player.inventory.add(Items.ENCHANTED_RAT_MEAT)
}
