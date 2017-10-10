package net.darkmorford.simplechunks.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeChunkManager;

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
}
