package net.darkmorford.simplechunks.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public class SingleSlotHandler implements IItemHandlerModifiable, INBTSerializable<NBTTagCompound>
{
	private ItemStack internalStack = ItemStack.EMPTY;
	private static final int MAX_STACK_SIZE = 16;

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		internalStack.writeToNBT(tag);

		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		internalStack = new ItemStack(nbt);
	}

	@Override
	public int getSlots()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		validateSlotNumber(slot);
		return internalStack;
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack)
	{
		validateSlotNumber(slot);
		if (ItemStack.areItemStacksEqual(internalStack, stack))
		{
			return;
		}

		internalStack = stack;

		onContentsChanged(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		// Return empty stack if given one
		if (stack.isEmpty())
		{
			return ItemStack.EMPTY;
		}

		// Only accept Ender Pearls
		if (stack.getItem() != Items.ENDER_PEARL)
		{
			return stack;
		}

		validateSlotNumber(slot);

		// Return the given stack if inventory is full
		if (internalStack.getCount() >= MAX_STACK_SIZE)
		{
			return stack;
		}

		// Return the given stack if not acceptable item
		if (!internalStack.isEmpty() && !ItemHandlerHelper.canItemStacksStack(stack, internalStack))
		{
			return stack;
		}

		// How many more items can we accept?
		int spaceAvailable = MAX_STACK_SIZE - internalStack.getCount();

		// Would inserting the whole stack overfill the inventory?
		boolean overfill = stack.getCount() > spaceAvailable;

		if (!simulate)
		{
			// Not simulated, actually transfer the items
			if (internalStack.isEmpty())
			{
				internalStack = overfill ? ItemHandlerHelper.copyStackWithSize(stack, spaceAvailable) : stack;
			}
			else
			{
				internalStack.grow(overfill ? spaceAvailable : stack.getCount());
			}
			onContentsChanged(slot);
		}

		if (!overfill)
		{
			// Accepted all items, return empty stack
			return ItemStack.EMPTY;
		}
		else
		{
			// Items left over, return the spares
			return ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - spaceAvailable);
		}
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		// Return empty stack if not extracting anything
		if (amount == 0)
		{
			return ItemStack.EMPTY;
		}

		validateSlotNumber(slot);

		// Return empty stack if no items left
		if (internalStack.getCount() == 0)
		{
			return ItemStack.EMPTY;
		}

		// How many items can we give up?
		int itemsRequested = Math.min(amount, MAX_STACK_SIZE);

		// Do we have that many?
		if (internalStack.getCount() <= itemsRequested)
		{
			ItemStack existingItems = internalStack;

			if (!simulate)
			{
				// Empty out the internal stack
				internalStack = ItemHandlerHelper.copyStackWithSize(internalStack, 0);
				onContentsChanged(slot);
			}

			// Return the whole stack of items
			return existingItems;
		}
		else
		{
			if (!simulate)
			{
				// Reduce the number of items we're holding
				internalStack = ItemHandlerHelper.copyStackWithSize(internalStack, internalStack.getCount() - itemsRequested);
				onContentsChanged(slot);
			}

			return ItemHandlerHelper.copyStackWithSize(internalStack, itemsRequested);
		}
	}

	@Override
	public int getSlotLimit(int slot)
	{
		return MAX_STACK_SIZE;
	}

	protected void validateSlotNumber(int slot)
	{
		if (slot != 0)
		{
			throw new RuntimeException("Invalid slot " + slot + " for SingleSlotHandler!");
		}
	}

	protected void onContentsChanged(int slot)
	{
	}
}
