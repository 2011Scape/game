package gg.rsmod.game.fs

enum class ArchiveType(val id: Int, val modernArchive: Boolean, val subId: Int = -1, val archiveOperand: Int = 8, val fileOperand: Int = 0xFF) {
    STRUCT(2, modernArchive = false, subId = 26),
    VARP(2, modernArchive = false, subId = 16),
    OBJECT(16, modernArchive = true),
    ENUM(17, modernArchive = true),
    NPC(18, modernArchive = true, archiveOperand = 134238215, fileOperand = 0x7f),
    ITEM(19, modernArchive = true),
    ANIM(20, modernArchive = true, archiveOperand = 7, fileOperand = 0x7f),
    VARBIT(22, modernArchive = true, archiveOperand = 10, fileOperand = 0x3FF);

}