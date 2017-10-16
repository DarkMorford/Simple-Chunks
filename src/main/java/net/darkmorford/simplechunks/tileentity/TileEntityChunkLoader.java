package net.darkmorford.simplechunks.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.darkmorford.simplechunks.SimpleChunks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.HashSet;
import java.util.Set;

public class TileEntityChunkLoader extends TileEntity
{
	public static final int INV_SLOTS = 1;

	private ItemStackHandler stackHandler = new ItemStackHandler(INV_SLOTS)
	{
		@Override
		protected void onContentsChanged(int slot)
		{
			TileEntityChunkLoader.this.markDirty();
		}
	};

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("inventory"))
		{
			stackHandler.deserializeNBT((NBTTagCompound)compound.getTag("inventory"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("inventory", stackHandler.serializeNBT());
		return compound;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}

		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(stackHandler);
		}

		return super.getCapability(capability, facing);
	}

	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5, 0.5, 0.5)) <= 64;
	}
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
