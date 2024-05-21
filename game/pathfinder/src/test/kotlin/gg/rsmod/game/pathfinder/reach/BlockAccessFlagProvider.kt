package gg.rsmod.game.pathfinder.reach

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import gg.rsmod.game.pathfinder.Direction
import gg.rsmod.game.pathfinder.flag.DirectionFlag
import java.util.stream.Stream

object BlockAccessFlagProvider : ArgumentsProvider {

    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
        return Stream.of(
            Arguments.of(Direction.North, DirectionFlag.NORTH),
            Arguments.of(Direction.East, DirectionFlag.EAST),
            Arguments.of(Direction.South, DirectionFlag.SOUTH),
            Arguments.of(Direction.West, DirectionFlag.WEST)
        )
    }
}
