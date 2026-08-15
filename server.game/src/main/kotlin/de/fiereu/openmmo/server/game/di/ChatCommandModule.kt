package de.fiereu.openmmo.server.game.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import de.fiereu.openmmo.server.game.services.command.CatchCommand
import de.fiereu.openmmo.server.game.services.command.ChatCommand
import de.fiereu.openmmo.server.game.services.command.GiveCommand
import de.fiereu.openmmo.server.game.services.command.HelpCommand
import de.fiereu.openmmo.server.game.services.command.DuelCommand
import de.fiereu.openmmo.server.game.services.command.RaidCommand
import de.fiereu.openmmo.server.game.services.command.TourneyCommand
import de.fiereu.openmmo.server.game.services.command.TradeCommand
import de.fiereu.openmmo.server.game.services.command.LinkCommand
import de.fiereu.openmmo.server.game.services.command.PmCommand
import de.fiereu.openmmo.server.game.services.command.PosCommand
import de.fiereu.openmmo.server.game.services.command.ProbeCommand
import de.fiereu.openmmo.server.game.services.command.StoryCommand
import de.fiereu.openmmo.server.game.services.command.TestBattleCommand
import de.fiereu.openmmo.server.game.services.command.WatchCommand
import de.fiereu.openmmo.server.game.services.command.WhoCommand

/**
 * A name must not start with a client side command. The client resolves those itself and never
 * sends them, which is why there is a /pos and no /where, which /w would have swallowed.
 */
@Module
interface ChatCommandModule {
  @Binds @IntoSet fun helpCommand(command: HelpCommand): ChatCommand

  @Binds @IntoSet fun posCommand(command: PosCommand): ChatCommand

  @Binds @IntoSet fun testBattleCommand(command: TestBattleCommand): ChatCommand

  @Binds @IntoSet fun catchCommand(command: CatchCommand): ChatCommand

  @Binds @IntoSet fun giveCommand(command: GiveCommand): ChatCommand

  @Binds @IntoSet fun storyCommand(command: StoryCommand): ChatCommand

  @Binds @IntoSet fun duelCommand(command: DuelCommand): ChatCommand

  @Binds @IntoSet fun tradeCommand(command: TradeCommand): ChatCommand

  @Binds @IntoSet fun raidCommand(command: RaidCommand): ChatCommand

  @Binds @IntoSet fun tourneyCommand(command: TourneyCommand): ChatCommand

  @Binds @IntoSet fun whoCommand(command: WhoCommand): ChatCommand

  @Binds @IntoSet fun pmCommand(command: PmCommand): ChatCommand

  @Binds @IntoSet fun linkCommand(command: LinkCommand): ChatCommand

  @Binds @IntoSet fun watchCommand(command: WatchCommand): ChatCommand

  @Binds @IntoSet fun probeCommand(command: ProbeCommand): ChatCommand
}
