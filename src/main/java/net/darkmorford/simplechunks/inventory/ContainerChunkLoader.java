package net.darkmorford.simplechunks.inventory;

import net.darkmorford.simplechunks.tileentity.TileEntityChunkLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerChunkLoader extends Container
{
	private TileEntityChunkLoader te;

	public ContainerChunkLoader(IInventory playerInventory, TileEntityChunkLoader te)
	{
		this.te = te;

		addTileEntitySlots();
		addPlayerSlots(playerInventory);
	}

	private void addPlayerSlots(IInventory playerInventory)
	{
		int colWidth = 18;
		int rowHeight = 18;

		int xOffset = 9;
		int yOffset = 70;

		// Main inventory
		int inventoryOffset = 9;
		for (int row = 0; row < 3; ++row)
		{
			for (int col = 0; col < 9; ++col)
			{
				int xPos = xOffset + (col * colWidth);
				int yPos = yOffset + (row * rowHeight);
				addSlotToContainer(new Slot(playerInventory, col + (row * 9) + inventoryOffset, xPos, yPos));
			}
		}

		// Hotbar
		inventoryOffset = 0;
		for (int col = 0; col < 9; ++col)
		{
			int xPos = xOffset + (col * colWidth);
			int yPos = yOffset + 58;
			addSlotToContainer(new Slot(playerInventory, col + inventoryOffset, xPos, yPos));
		}
	}

	private void addTileEntitySlots()
	{
		IItemHandler handler = te.getStackHandler();

		int xPos = 9;
		int yPos = 6;

		int colWidth = 18;

		for (int i = 0; i < handler.getSlots(); ++i)
		{
			addSlotToContainer(new SlotItemHandler(handler, i, xPos, yPos));
			xPos += colWidth;
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();

			if (index < TileEntityChunkLoader.INV_SLOTS)
			{
				if (!this.mergeItemStack(slotStack, TileEntityChunkLoader.INV_SLOTS, inventorySlots.size(), true))
				{
					return new ItemStack(Items.ENDER_PEARL, 0);
				}
			}
			else if (!this.mergeItemStack(slotStack, 0, TileEntityChunkLoader.INV_SLOTS, false))
			{
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return te.canInteractWith(playerIn);
	}
}
