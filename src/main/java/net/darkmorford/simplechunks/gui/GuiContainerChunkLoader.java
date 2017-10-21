package net.darkmorford.simplechunks.gui;

import net.darkmorford.simplechunks.SimpleChunks;
import net.darkmorford.simplechunks.inventory.ContainerChunkLoader;
import net.darkmorford.simplechunks.tileentity.TileEntityChunkLoader;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiContainerChunkLoader extends GuiContainer
{
	private static final ResourceLocation backgroundTexture = new ResourceLocation(SimpleChunks.MODID, "textures/gui/containerchunkloader.png");

	public GuiContainerChunkLoader(TileEntityChunkLoader tileEntity, ContainerChunkLoader container)
	{
		super(container);

		xSize = 176;
		ySize = 170;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString("Time Left:", 84, 12, 0x404040);
		this.fontRenderer.drawString("--:--:--", 87, 23, 0x000000);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(backgroundTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
