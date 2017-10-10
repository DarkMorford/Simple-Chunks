package net.darkmorford.simplechunks.compat;

import mcjty.theoneprobe.api.*;
import net.darkmorford.simplechunks.SimpleChunks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import java.util.function.Function;

public class TOPCompat
{
	private static boolean registered;

	public static void register()
	{
		if (registered)
		{
			return;
		}

		registered = true;

		FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "net.darkmorford.simplechunks.compat.TOPCompat$GetTOP");
	}

	public static class GetTOP implements Function<ITheOneProbe, Void>
	{
		public static ITheOneProbe probe;

		@Override
		public Void apply(ITheOneProbe theOneProbe)
		{
			probe = theOneProbe;
			SimpleChunks.logger.info("Enabled TheOneProbe support");

			probe.registerProvider(new IProbeInfoProvider() {
				@Override
				public String getID()
				{
					return "simplechunks:default";
				}

				@Override
				public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData)
				{
					if (iBlockState.getBlock() instanceof TOPInfoProvider)
					{
						TOPInfoProvider provider = (TOPInfoProvider)iBlockState.getBlock();
						provider.addProbeInfo(probeMode, iProbeInfo, entityPlayer, world, iBlockState, iProbeHitData);
					}
				}
			});

			return null;
		}
	}
}
