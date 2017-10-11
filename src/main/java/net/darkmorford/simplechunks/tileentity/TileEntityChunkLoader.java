package net.darkmorford.simplechunks.tileentity;

import net.darkmorford.simplechunks.SimpleChunks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.HashSet;
import java.util.Set;

public class TileEntityChunkLoader extends TileEntity
{
	private ForgeChunkManager.Ticket chunkTicket;

	public void setChunkTicket(ForgeChunkManager.Ticket ticket)
	{
		chunkTicket = ticket;
	}

	public ForgeChunkManager.Ticket getChunkTicket()
	{
		return chunkTicket;
	}

	public void clearTicketChunks()
	{
		if (chunkTicket == null)
		{
			return;
		}

		for (ChunkPos chunk : chunkTicket.getChunkList())
		{
			SimpleChunks.logger.info("Unforcing chunkload at " + chunk);
			ForgeChunkManager.unforceChunk(chunkTicket, chunk);
		}
	}

	public void forceChunks()
	{
		if (chunkTicket == null)
		{
			return;
		}

		for (ChunkPos chunk : getSurroundingChunks(1))
		{
			SimpleChunks.logger.info("Forcing chunkload at " + chunk);
			ForgeChunkManager.forceChunk(chunkTicket, chunk);
		}
	}

	private Set<ChunkPos> getSurroundingChunks(int radius)
	{
		Set<ChunkPos> chunks = new HashSet<>();
		ChunkPos centerChunk = world.getChunkFromBlockCoords(pos).getPos();

		for (int x = centerChunk.x - radius; x <= centerChunk.x + radius; ++x)
		{
			for (int z = centerChunk.z - radius; z <= centerChunk.z + radius; ++z)
			{
				chunks.add(new ChunkPos(x, z));
			}
		}

		return chunks;
	}
}
