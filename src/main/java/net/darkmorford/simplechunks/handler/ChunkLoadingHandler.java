package net.darkmorford.simplechunks.handler;

import com.google.common.collect.ListMultimap;
import net.darkmorford.simplechunks.SimpleChunks;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import org.apache.logging.log4j.Level;

import java.util.List;

public class ChunkLoadingHandler implements ForgeChunkManager.PlayerOrderedLoadingCallback
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

	@Override
	public ListMultimap<String, ForgeChunkManager.Ticket> playerTicketsLoaded(ListMultimap<String, ForgeChunkManager.Ticket> tickets, World world)
	{
		// We don't care what order the tickets are in, just send back the same list
		return tickets;
	}
}
