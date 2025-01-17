package gg.rsmod.game

object NettyPatch {
    fun disableDirectMemory(): Boolean {
        return try {
            // Access the PlatformDependent class
            val platformDependentClass = Class.forName("io.netty.util.internal.PlatformDependent")

            // Locate the private USE_DIRECT_MEMORY field
            val useDirectMemoryField = platformDependentClass.getDeclaredField("USE_DIRECT_MEMORY")

            // Make the field accessible
            useDirectMemoryField.isAccessible = true

            // Set the static field to false
            useDirectMemoryField.setBoolean(null, false)

            println("Successfully disabled Netty's direct memory allocation.")
            true // Patch applied successfully
        } catch (e: Exception) {
            println("Failed to patch Netty: ${e.message}")
            e.printStackTrace()
            false // Patch failed
        }
    }
}
