package net.darkmorford.simplechunks.proxy;

import net.darkmorford.simplechunks.SimpleChunks;
import net.darkmorford.simplechunks.block.BlockChunkLoader;
import net.darkmorford.simplechunks.config.GeneralConfig;
import net.darkmorford.simplechunks.init.ModBlocks;
import net.darkmorford.simplechunks.tileentity.TileEntityChunkLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy
{
	private static Configuration config;

	public void preInit(FMLPreInitializationEvent event)
	{
		// Handle configuration
		File configDir = event.getModConfigurationDirectory();
		config = new Configuration(new File(configDir.getPath(), "simplechunks.cfg"));
		try
		{
			config.load();

			GeneralConfig.readConfig(config);
		}
		catch (Exception e)
		{
			SimpleChunks.logger.error("Error loading config file!", e);
		}
		finally
		{
			if (config.hasChanged())
			{
				config.save();
			}
		}
	}

	public void init(FMLInitializationEvent event)
	{
	}

	public void postInit(FMLPostInitializationEvent event)
	{
		if (config.hasChanged())
		{
			config.save();
		}
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
