package gg.rsmod.game.model.attr

import gg.rsmod.game.model.container.ItemTransaction
import gg.rsmod.game.model.entity.*
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.shop.Shop
import java.lang.ref.WeakReference

/**
 * A decoupled file that holds AttributeKeys that require read-access from our
 * game module. Any attributes that can be stored on the plugin classes themselves,
 * should do so. When storing them in a class, remember the AttributeKey must be
 * a singleton, meaning it should only have a single state.
 *
 * @author Tom <rspsmods@gmail.com>
 */

/**
 * A flag which indicates if the player's account was just created/logged in for
 * the first time.
 */
val NEW_ACCOUNT_ATTR = AttributeKey<Boolean>()

/**
 * A flag which indicates that the player will not take collision into account
 * when walking.
 */
val NO_CLIP_ATTR = AttributeKey<Boolean>()

/**
 * A flag that indicates whether or not this player has protect-item
 * prayer active.
 */
val PROTECT_ITEM_ATTR = AttributeKey<Boolean>()

/**
 * The display mode that the player has submitted as a message.
 */
val DISPLAY_MODE_CHANGE_ATTR = AttributeKey<Int>()

/**
 * The [Pawn] which another pawn is facing.
 */
val FACING_PAWN_ATTR = AttributeKey<WeakReference<Pawn>>()

/**
 * An [Npc] that has us as their [FACING_PAWN_ATTR].
 */
val NPC_FACING_US_ATTR = AttributeKey<WeakReference<Npc>>()

/**
 * The current viewed shop.
 */
val CURRENT_SHOP_ATTR = AttributeKey<Shop>()

/**
 * The [Pawn] which another pawn wants to initiate combat with, whether they meet
 * the criteria to attack or not (including being in attack range).
 */
val COMBAT_TARGET_FOCUS_ATTR = AttributeKey<WeakReference<Pawn>>()

/**
 * The [Pawn] that killed another pawn.
 */
val KILLER_ATTR = AttributeKey<WeakReference<Pawn>>()

/**
 * The last [Pawn] that the owner of this attribute has hit.
 */
val LAST_HIT_ATTR = AttributeKey<WeakReference<Pawn>>()

/**
 * The last [Pawn] who has hit the owner of this attribute.
 */
val LAST_HIT_BY_ATTR = AttributeKey<WeakReference<Pawn>>()

/**
 * The amount of "poison ticks" left before the poison wears off.
 */
val POISON_TICKS_LEFT_ATTR = AttributeKey<Int>(persistenceKey = "poison_ticks_left", resetOnDeath = true)

/**
 * The amount of antifire potion charges left.
 */
val ANTIFIRE_POTION_CHARGES_ATTR = AttributeKey<Int>(persistenceKey = "antifire_potion_charges", resetOnDeath = true)

/**
 * If full dragonfire immunity is enabled.
 */
val DRAGONFIRE_IMMUNITY_ATTR = AttributeKey<Boolean>(persistenceKey = "dragonfire_immunity", resetOnDeath = true)

/**
 * The command that the player has submitted to the server using the '::' prefix.
 */
val COMMAND_ATTR = AttributeKey<String>()

/**
 * The arguments to the last command that was submitted by the player. This does
 * not include the command itself, if you want the command itself, use the
 * [COMMAND_ATTR] attribute.
 */
val COMMAND_ARGS_ATTR = AttributeKey<Array<String>>()

/**
 * The option that was last selected on any entity message.
 * For example: object action one will set this attribute to [1].
 */
val INTERACTING_OPT_ATTR = AttributeKey<Int>()

/**
 * The slot that was last selected on any entity message.
 */
val INTERACTING_SLOT_ATTR = AttributeKey<Int>()

/**
 * The [GroundItem] that was last clicked on.
 */
val INTERACTING_GROUNDITEM_ATTR = AttributeKey<WeakReference<GroundItem>>()

/**
 * The last [ItemTransaction] to occur when a ground item is picked up
 * from the ground.
 */
val GROUNDITEM_PICKUP_TRANSACTION = AttributeKey<WeakReference<ItemTransaction>>()

/**
 * The [GameObject] that was last clicked on.
 */
val INTERACTING_OBJ_ATTR = AttributeKey<WeakReference<out GameObject>>()

/**
 * The [Npc] that was last clicked on.
 */
val INTERACTING_NPC_ATTR = AttributeKey<WeakReference<Npc>>()

/**
 * The [Player] that was last clicked on.
 */
val INTERACTING_PLAYER_ATTR = AttributeKey<WeakReference<Player>>()

/**
 * The slot of the interacting item in its item container.
 */
val INTERACTING_ITEM_SLOT = AttributeKey<Int>()

/**
 * The id of the interacting item.
 */
val INTERACTING_ITEM_ID = AttributeKey<Int>()


/**
 * The id of the interacting button.
 */
val INTERACTING_BUTTON_ID = AttributeKey<Int>()

/**
 * The item pointer of the interacting item.
 */
val INTERACTING_ITEM = AttributeKey<WeakReference<Item>>()

/**
 * The slot index of any 'secondary' item being interacted with.
 */
val OTHER_ITEM_SLOT_ATTR = AttributeKey<Int>()

/**
 * The slot index of any 'secondary' item being interacted with.
 */
val SWAP_COMPONENT = AttributeKey<Int>()

/**
 * The slot index of any 'secondary' item being interacted with.
 */
val OTHER_SWAP_COMPONENT = AttributeKey<Int>()

/**
 * The item id of any 'secondary' item being interacted with.
 */
val OTHER_ITEM_ID_ATTR = AttributeKey<Int>()

/**
 * The item pointer of any 'secondary' item being interacted with.
 */
val OTHER_ITEM_ATTR = AttributeKey<WeakReference<Item>>()

/**
 * Interacting interface parent id.
 */
val INTERACTING_COMPONENT_HASH = AttributeKey<Int>()

/**
 * Interacting interface parent id.
 */
val INTERACTING_COMPONENT_PARENT = AttributeKey<Int>()

/**
 * Interacting interface child id.
 */
val INTERACTING_COMPONENT_CHILD = AttributeKey<Int>()

/**
 * The skill id of the latest level up.
 */
val LEVEL_UP_SKILL_ID = AttributeKey<Int>()

/**
 * The skill id of the latest experience up.
 */
val EXPERIENCE_UP_SKILL_ID = AttributeKey<Int>()

/**
 * The amount of levels that have incremented in a skill level up.
 */
val LEVEL_UP_INCREMENT = AttributeKey<Int>()

/**
 * The last combat level
 */
val LAST_COMBAT_LEVEL = AttributeKey<Int>()

/**
 * The last total level.
 */
val LAST_TOTAL_LEVEL = AttributeKey<Int>()

/**
 * The previous skill XP of the latest level up.
 */
val LEVEL_UP_OLD_XP = AttributeKey<Double>()

/**
 * The current skill menu opened
 */
val SKILL_MENU = AttributeKey<Int>()

/**
 * The opcode that was last sent on any interface message.
 * For example: opcode 64 was sent on an option in an if3 interface.
 */
val INTERACTING_OPCODE_ATTR = AttributeKey<Int>()

/**
 * The last viewed shop item slot
 * Required for the Item Information buy button
 */
val LAST_VIEWED_SHOP_ITEM_SLOT = AttributeKey<Int>()

/**
 * The last viewed shop item container (free/sold)
 * Required for the Item Information take button
 */
val LAST_VIEWED_SHOP_ITEM_FREE = AttributeKey<Boolean>()

/**
 * Whether the pawn was running or not
 */
val LAST_KNOWN_RUN_STATE = AttributeKey<Int>()

/**
 * The type of weapon the player is holding
 */
val LAST_KNOWN_WEAPON_TYPE = AttributeKey<Int>()


/**
 * The type of bar the player is smithing
 */
val BAR_TYPE = AttributeKey<Int>()

/**
 * If the player has activated Millie's extra-fine flour
 * operations
 */
val EXRTA_FINE_FLOUR = AttributeKey<Boolean>(persistenceKey = "extra_fine_flour")

/**
 * The last npc that the player interacted with
 * before teleporting to the essence mines
 */
val ESSENCE_MINE_INTERACTED_WITH = AttributeKey<Int>(persistenceKey = "last_loc_ess")

/**
 * The creation date for the account
 * saved in milliseonds
 */
val CREATION_DATE = AttributeKey<Long>(persistenceKey = "creation_date")

/**
 * The date the account last logged out
 * saved in milliseonds
 */
val LAST_LOGOUT_DATE = AttributeKey<Long>(persistenceKey = "last_logout_date")

/**
 * The last farming tick this character handled
 */
val LAST_WORLD_FARMING_TICK = AttributeKey<Int>(persistenceKey = "last_world_farming_tick")

/**
 * The last known action that the player
 * requested from the item box with space bar
 */
val LAST_KNOWN_SPACE_ACTION = AttributeKey<Int>(persistenceKey = "last_space_action")

/**
 * The last known initial item that the player
 * used to request an item box
 *
 * Note: this is used to "reset" the space bar
 * attribute should a "new" item box be produced
 */
val LAST_KNOWN_ITEMBOX_ITEM = AttributeKey<Int>(persistenceKey = "last_item_box")

val LONG_ATTRIBUTES = AttributeKey<Map<String, Long>>(persistenceKey = "long_attributes")
val DOUBLE_ATTRIBUTES = AttributeKey<Map<String, Double>>(persistenceKey = "double_attributes")