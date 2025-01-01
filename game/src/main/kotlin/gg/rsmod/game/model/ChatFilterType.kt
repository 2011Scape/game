package gg.rsmod.game.model

enum class ChatFilterType(
    val settingId: Int,
) {
    HIDE(3),
    OFF(2),
    FRIENDS(1),
    ON(0), ;

    companion object {
        fun getSettingById(id: Int?): ChatFilterType? {
            if (id == null) return null
            val values = enumValues<ChatFilterType>()
            values.forEach {
                if (it.settingId == id) return it
            }

            return null
        }
    }
}
