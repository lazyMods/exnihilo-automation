package net.marcosantos.exnihiloauto.utils;

import com.google.common.base.Preconditions;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public abstract class InvHandler implements WorldlyContainer {
	public NonNullList<ItemStack> stacks;

	public InvHandler(int size) {
		this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
	}

	public abstract boolean isOutputSlot(int slot);

	public List<ItemStack> getStackFromTo(int fromIndex, int toIndex) {
		var itemStacks = new ArrayList<ItemStack>();
		for (int i = fromIndex; i < toIndex; i++) {
			itemStacks.add(this.getItem(i));
		}
		return itemStacks;
	}

	public Item get(int slot) {
		return this.getItem(slot).getItem();
	}

	public Block getBlockItem(int slot) {
		return Block.byItem(this.get(slot));
	}

	public boolean isSlotEmpty(int slot) {
		return this.getItem(slot).isEmpty();
	}

	public int checkAndGetStackInsertSlots(ItemStack stack) {
		for (int i : outputSlots()) {
			if (this.isOutputSlot(i)) {
				var stackAt = this.stacks.get(i);
				if (ItemStack.isSameItem(stackAt, stack) && stackAt.getCount() != stackAt.getMaxStackSize()) {
					return i;
				}
			}
		}
		return -1;
	}

	public boolean isSlotFull(int slot) {
		if (this.isSlotEmpty(slot))
			return false;
		return this.getItem(slot).getCount() == this.getItem(slot).getMaxStackSize();
	}

	public boolean canInsertItemOnSlot(int slot, ItemStack stack) {
		return (ItemStack.isSameItem(this.stacks.get(slot), stack) || this.stacks.get(slot).isEmpty()) && this.stacks.get(slot).getCount() != stack.getMaxStackSize();
	}

	public abstract int[] outputSlots();

	public int getEmptyInsertSlot() {
		for (int slot : outputSlots()) {
			if (isSlotEmpty(slot))
				return slot;
		}
		return -1;
	}

	public boolean tryInsertMany(List<ItemStack> toInsert) {
		boolean hasInserted = false;
		for (var insert : toInsert) {
			int slot = checkAndGetStackInsertSlots(insert);
			if (slot == -1) {
				int empty = getEmptyInsertSlot();
				if (empty == -1) {
					break;
				}
				var left = insertItem(empty, insert, false);
				hasInserted = true;
				Preconditions.checkArgument(left == ItemStack.EMPTY);
			} else {
				insertItem(slot, insert, false);
				hasInserted = true;
			}
		}
		return hasInserted;
	}

	public void validateSlotIndex(int slot) {
		if (slot < 0 || slot >= this.stacks.size())
			throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
	}

	@Nonnull
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		if (stack.isEmpty())
			return ItemStack.EMPTY;

		validateSlotIndex(slot);

		var existing = this.stacks.get(slot);

		int limit = stack.getMaxStackSize();

		if (!existing.isEmpty()) {
			if (!canItemStacksStack(stack, existing))
				return stack;

			limit -= existing.getCount();
		}

		if (limit <= 0)
			return stack;

		boolean reachedLimit = stack.getCount() > limit;

		if (!simulate) {
			if (existing.isEmpty()) {
				this.stacks.set(slot, reachedLimit ? copyStackWithSize(stack, limit) : stack);
			} else {
				existing.grow(reachedLimit ? limit : stack.getCount());
			}
		}

		return reachedLimit ? copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
	}

	@SuppressWarnings("UnusedReturnValue")
	@Nonnull
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (amount == 0)
			return ItemStack.EMPTY;

		validateSlotIndex(slot);

		var existing = this.stacks.get(slot);

		if (existing.isEmpty())
			return ItemStack.EMPTY;

		int toExtract = Math.min(amount, existing.getMaxStackSize());

		if (existing.getCount() <= toExtract) {
			if (!simulate) {
				this.stacks.set(slot, ItemStack.EMPTY);
				return existing;
			} else {
				return existing.copy();
			}
		} else {
			if (!simulate) {
				this.stacks.set(slot, copyStackWithSize(existing, existing.getCount() - toExtract));
			}

			return copyStackWithSize(existing, toExtract);
		}
	}

	public void setSize(int size) {
		this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
	}

	@Override
	public int getContainerSize() {
		return this.stacks.size();
	}

	@Override
	public boolean isEmpty() {
		return this.stacks.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	@Nonnull
	public ItemStack getItem(int index) {
		return this.stacks.get(index);
	}

	@Override
	@Nonnull
	public ItemStack removeItem(int index, int count) {
		return ContainerHelper.removeItem(this.stacks, index, count);
	}

	@Override
	@Nonnull
	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(this.stacks, index);
	}

	@Override
	@ParametersAreNonnullByDefault
	public void setItem(int index, ItemStack stack) {
		this.stacks.set(index, stack);
	}

	@Override
	public void setChanged() {

	}

	@Override
	public boolean stillValid(@Nonnull Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		this.stacks.clear();
	}

	private boolean canItemStacksStack(ItemStack a, ItemStack b) {
		if (a.isEmpty() || !ItemStack.isSameItem(a, b))
			return false;

		return ItemStack.isSameItemSameComponents(a, b);
	}

	private ItemStack copyStackWithSize(ItemStack itemStack, int size) {
		if (size == 0)
			return ItemStack.EMPTY;
		ItemStack copy = itemStack.copy();
		copy.setCount(size);
		return copy;
	}
}
