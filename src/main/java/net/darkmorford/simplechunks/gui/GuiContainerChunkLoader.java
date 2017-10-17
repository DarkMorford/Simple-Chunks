package net.darkmorford.simplechunks.gui;

import net.darkmorford.simplechunks.inventory.ContainerChunkLoader;
import net.darkmorford.simplechunks.tileentity.TileEntityChunkLoader;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiContainerChunkLoader extends GuiContainer
{
	private static final ResourceLocation backgroundTexture = new ResourceLocation("textures/gui/containerchunkloader.png");

	public GuiContainerChunkLoader(TileEntityChunkLoader tileEntity, ContainerChunkLoader container)
	{
		super(container);

		xSize = 180;
		ySize = 152;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(backgroundTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
