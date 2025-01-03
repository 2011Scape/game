package gg.rsmod.plugins.content.mechanics.stairs

data class Stairs(
    val objectId: Int = -1,
    val northFacingRotation: Int = 0,
    val horizontalMovementDistance: Int = 0,
    val verticalMovementDistance: Int = 1,
    val climbDownOption: String = "climb-down",
    val climbUpOption: String = "climb-up",
    val climbDialogueOption: String = "climb",
)
