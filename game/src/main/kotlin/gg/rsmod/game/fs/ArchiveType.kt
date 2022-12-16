package gg.rsmod.game.fs

enum class ArchiveType(val id: Int, val modernArchive: Boolean, val subId: Int = -1) {
    STRUCT(2, modernArchive = false, subId = 26),
    VARP(2, modernArchive = false, subId = 16),
    OBJECT(16, modernArchive = true),
    ENUM(17, modernArchive = true),
    NPC(18, modernArchive = true),
    ITEM(19, modernArchive = true),
    ANIM(20, modernArchive = true),
    VARBIT(22, modernArchive = true);

}