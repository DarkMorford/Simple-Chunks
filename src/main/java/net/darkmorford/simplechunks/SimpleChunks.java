package net.darkmorford.simplechunks;

import net.darkmorford.simplechunks.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = SimpleChunks.MODID,
		name = SimpleChunks.MODNAME,
		version = SimpleChunks.VERSION,
		acceptedMinecraftVersions = "[1.12,1.13)",
		useMetadata = true
)
public class SimpleChunks
{
	public static final String MODID = "simplechunks";
	public static final String MODNAME = "Simple Chunks";
	public static final String VERSION = "1.12-0.0.1.0";

	@Mod.Instance
	public static SimpleChunks instance;

	@SidedProxy(clientSide = "net.darkmorford.simplechunks.proxy.ClientProxy", serverSide = "net.darkmorford.simplechunks.proxy.ServerProxy")
	public static CommonProxy proxy;

	public static Logger logger;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		proxy.preInit(event);
	}

	@Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
