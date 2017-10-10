package net.darkmorford.simplechunks.init;

import net.darkmorford.simplechunks.block.BlockChunkLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
	@GameRegistry.ObjectHolder("simplechunks:chunkloader")
	public static BlockChunkLoader chunkLoader;
}
