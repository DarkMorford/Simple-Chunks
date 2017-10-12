package net.darkmorford.simplechunks.handler;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.darkmorford.simplechunks.tileentity.TileEntityChunkLoader;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.ArrayList;
import java.util.List;

public class ChunkLoadingHandler implements ForgeChunkManager.PlayerOrderedLoadingCallback
{
	@Override
	public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world)
	{
		for (ForgeChunkManager.Ticket ticket : tickets)
		{
			BlockPos ticketPosition = NBTUtil.getPosFromTag(ticket.getModData().getCompoundTag("position"));
			TileEntity te = world.getTileEntity(ticketPosition);
			if (te instanceof TileEntityChunkLoader)
			{
				TileEntityChunkLoader loader = (TileEntityChunkLoader) te;
				loader.setChunkTicket(ticket);
				loader.forceChunks();
			}
		}
	}

	@Override
	public ListMultimap<String, ForgeChunkManager.Ticket> playerTicketsLoaded(ListMultimap<String, ForgeChunkManager.Ticket> tickets, World world)
	{
		// We don't care what order the tickets are in, but filter out the invalid ones
		ListMultimap<String, ForgeChunkManager.Ticket> validTickets = ArrayListMultimap.create();

		for (String playerName : tickets.keySet())
		{
			List<ForgeChunkManager.Ticket> playerTickets = new ArrayList<>();

			for (ForgeChunkManager.Ticket tkt : tickets.get(playerName))
			{
				BlockPos ticketPosition = NBTUtil.getPosFromTag(tkt.getModData().getCompoundTag("position"));
				TileEntity te = world.getTileEntity(ticketPosition);
				if (te instanceof TileEntityChunkLoader)
				{
					playerTickets.add(tkt);
				}
			}

			validTickets.putAll(playerName, playerTickets);
		}

		return validTickets;
	}
}
