package gg.rsmod.game.model.attr

/**
 * A system responsible for storing and exposing [AttributeKey]s and their
 * associated values. The type of the key is inferred by the [AttributeKey]
 * used when putting or getting the value.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class AttributeMap {
    private var attributes: MutableMap<AttributeKey<*>, Any> = HashMap(0)

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: AttributeKey<T>): T? = (attributes[key] as? T)

    @Suppress("UNCHECKED_CAST")
    fun <T> getOrDefault(
        key: AttributeKey<T>,
        default: T,
    ): T = (attributes[key] as? T) ?: default

    @Suppress("UNCHECKED_CAST")
    fun <T> put(
        key: AttributeKey<T>,
        value: T,
    ): AttributeMap {
        attributes[key] = value as Any
        return this
    }

    operator fun <T> set(
        key: AttributeKey<T>,
        value: T,
    ) {
        put(key, value)
    }

    fun remove(key: AttributeKey<*>) {
        if (attributes.containsKey(key)) {
            attributes.remove(key)
        }
    }

    fun remove(vararg key: AttributeKey<*>) {
        key.forEach {
            if (attributes.containsKey(it)) {
                attributes.remove(it)
            }
        }
    }

    fun has(key: AttributeKey<*>): Boolean = attributes.containsKey(key)

    fun clear() {
        attributes.clear()
    }

    fun removeIf(predicate: (AttributeKey<*>) -> Boolean) {
        val iterator = attributes.iterator()
        while (iterator.hasNext()) {
            val attr = iterator.next()
            if (predicate(attr.key)) {
                iterator.remove()
            }
        }
    }

    /**
     * Gathers all persistent attributes so that they can be saved in the player profile.
     * The dynamic nature of attributes makes it hard if not impossible to distinguish between Ints, Doubles and Longs
     * when loading a player profile, without explicitly stating the type. Therefore, all Longs and Doubles are
     * stored in a separate attribute.
     */
    fun toPersistentMap(): Map<String, Any> {
        val persistentAttributes = attributes.filterKeys { it.persistenceKey != null }
        val longs = persistentAttributes.filter { it.value is Long }
        val doubles = persistentAttributes.filter { it.value is Double }
        val others = persistentAttributes.filter { it.value !is Long && it.value !is Double }.toMutableMap()
        if (longs.any()) {
            others[LONG_ATTRIBUTES] = longs.mapKeys { it.key.persistenceKey!! }
        }
        if (doubles.any()) {
            others[DOUBLE_ATTRIBUTES] = doubles.mapKeys { it.key.persistenceKey!! }
        }
        return others.mapKeys { it.key.persistenceKey!! }
    }

    fun getOrNull(key: AttributeKey<*>): Any? {
        return attributes[key]
    }
}
