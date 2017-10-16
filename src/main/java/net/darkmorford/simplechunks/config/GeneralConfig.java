package net.darkmorford.simplechunks.config;

import net.minecraftforge.common.config.Configuration;

public class GeneralConfig
{
	private static final String CATEGORY_NAME = "general";

	// Default to 4.5 hours per pearl, or 3 days per stack
	public static int minutesPerPearl = 270;

	public static void readConfig(Configuration cfg)
	{
		cfg.addCustomCategoryComment(CATEGORY_NAME, "General configuration");

		minutesPerPearl = cfg.getInt("minutesPerPearl", CATEGORY_NAME, minutesPerPearl, 1, 1440, "Length of time to run on one ender pearl");
	}
}
