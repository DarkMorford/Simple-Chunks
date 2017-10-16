package net.darkmorford.simplechunks.init;

import net.darkmorford.simplechunks.block.BlockChunkLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks
{
	@GameRegistry.ObjectHolder("simplechunks:chunkloader")
	public static BlockChunkLoader chunkLoader;

	@SideOnly(Side.CLIENT)
	public static void initModels()
	{
		chunkLoader.initModel();
	}
}
