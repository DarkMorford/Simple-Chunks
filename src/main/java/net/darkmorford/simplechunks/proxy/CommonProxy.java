package net.darkmorford.simplechunks.proxy;

import net.darkmorford.simplechunks.SimpleChunks;
import net.darkmorford.simplechunks.block.BlockChunkLoader;
import net.darkmorford.simplechunks.init.ModBlocks;
import net.darkmorford.simplechunks.tileentity.TileEntityChunkLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
	}

	public void init(FMLInitializationEvent event)
	{
	}

	public void postInit(FMLPostInitializationEvent event)
	{
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new BlockChunkLoader());
		GameRegistry.registerTileEntity(TileEntityChunkLoader.class, SimpleChunks.MODID + ":chunkloader");
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(new ItemBlock(ModBlocks.chunkLoader).setRegistryName(ModBlocks.chunkLoader.getRegistryName()));
	}
}
