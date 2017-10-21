package net.darkmorford.simplechunks.gui;

import net.darkmorford.simplechunks.inventory.ContainerChunkLoader;
import net.darkmorford.simplechunks.tileentity.TileEntityChunkLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos position = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(position);

		if (tileEntity instanceof TileEntityChunkLoader)
		{
			TileEntityChunkLoader loader = (TileEntityChunkLoader)tileEntity;
			return new ContainerChunkLoader(player.inventory, loader);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos position = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(position);

		if (tileEntity instanceof TileEntityChunkLoader)
		{
			TileEntityChunkLoader loader = (TileEntityChunkLoader)tileEntity;
			return new GuiContainerChunkLoader(loader, new ContainerChunkLoader(player.inventory, loader));
		}

		return null;
	}
}
