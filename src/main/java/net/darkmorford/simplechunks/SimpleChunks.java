package net.darkmorford.simplechunks;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

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
    public static final String VERSION = "1.12-0.0.0.0";
	
	@Mod.Instance
	public static SimpleChunks instance;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
    }
}
