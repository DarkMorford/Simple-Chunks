package net.darkmorford.simplechunks.handler;

import net.darkmorford.simplechunks.SimpleChunks;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import org.apache.logging.log4j.Level;

import java.util.List;

public class ChunkLoadingHandler implements ForgeChunkManager.LoadingCallback
{
	@Override
	public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world)
	{
		SimpleChunks.logger.info("ticketsLoaded callback fired");

		for (ForgeChunkManager.Ticket ticket : tickets)
		{
			if (ticket.isPlayerTicket())
			{
				SimpleChunks.logger.log(Level.INFO, "Releasing ticket for player " + ticket.getPlayerName());
			}
			else
			{
				SimpleChunks.logger.log(Level.INFO, "Releasing ticket for mod " + ticket.getModId());
			}

			ForgeChunkManager.releaseTicket(ticket);
		}
	}
}
