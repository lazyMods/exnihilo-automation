package lazy.exnihiloauto.inventory;

import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public abstract class InvHandler implements ISidedInventory, INBTSerializable<CompoundNBT> {

    private NonNullList<ItemStack> stacks;

    public InvHandler(int size) {
        this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public abstract boolean canInsertOn(int slot);

    public Item getItem(int slot) {
        return this.getStackInSlot(slot).getItem();
    }

    public Block getBlockItem(int slot) {
        return Block.getBlockFromItem(this.getItem(slot));
    }

    public boolean isSlotEmpty(int slot) {
        return this.getStackInSlot(slot).isEmpty();
    }

    public boolean hasEmptySlot() {
        return this.checkAndGetEmptySlot() != -1;
    }

    public int checkAndGetEmptySlot() {
        for (int i = 0; i < this.stacks.size(); i++) {
            if (this.canInsertOn(i)) {
                if (this.stacks.get(i).isEmpty()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean hasStack(ItemStack stack) {
        return this.checkAndGetStack(stack) != -1;
    }

    public int checkAndGetStack(ItemStack stack) {
        for (int i = 0; i < this.stacks.size(); i++) {
            if (this.canInsertOn(i)) {
                var stackAt = this.stacks.get(i);
                if (ItemStack.areItemsEqual(stackAt, stack) && stackAt.getCount() != stackAt.getMaxStackSize()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean isSlotFull(int slot) {
        if (this.isSlotEmpty(slot)) return false;
        return this.getStackInSlot(slot).getCount() == this.getStackInSlot(slot).getMaxStackSize();
    }

    public boolean canInsertItemOnSlot(int slot, ItemStack stack) {
        return (ItemStack.areItemsEqual(this.stacks.get(slot), stack) || this.stacks.get(slot).isEmpty()) && this.stacks.get(slot).getCount() != stack.getMaxStackSize();
    }

    public boolean canInsertAll(List<ItemStack> stacks) {
        if (stacks.size() > this.getSizeInventory()) return false;
        if (this.isEmpty()) return true;
        int added = 0;
        for (ItemStack itemStack : stacks) {
            if (this.hasStack(itemStack)) {
                added++;
            } else if (this.hasEmptySlot()) {
                added++;
            }
        }
        return added == stacks.size();
    }

    public boolean isItemValid(int slot, ItemStack stack) {
        return true;
    }

    public void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= this.stacks.size())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
    }

    public int getStackLimit(int slot, ItemStack stack) {
        return this.getStackInSlot(slot).getMaxStackSize();
    }

    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isItemValid(slot, stack))
            return stack;

        validateSlotIndex(slot);

        var existing = this.stacks.get(slot);

        int limit = getStackLimit(slot, stack);

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            onContentsChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

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
                onContentsChanged(slot);
                return existing;
            } else {
                return existing.copy();
            }
        } else {
            if (!simulate) {
                this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
                onContentsChanged(slot);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    public void setSize(int size) {
        this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public void onContentsChanged(int slot) {

    }

    public void onLoad() {
    }

    //IInventory

    @Override
    public int getSizeInventory() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        return this.stacks.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int index) {
        return this.stacks.get(index);
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.stacks, index, count);
    }

    @Override
    @Nonnull
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.stacks, index);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.stacks.set(index, stack);
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUsableByPlayer(@Nonnull PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }

    //INbtSerializer
    @Override
    public CompoundNBT serializeNBT() {
        var nbtTagList = new ListNBT();
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                CompoundNBT itemTag = new CompoundNBT();
                itemTag.putInt("Slot", i);
                stacks.get(i).write(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        var nbt = new CompoundNBT();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", stacks.size());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setSize(nbt.contains("Size", Constants.NBT.TAG_INT) ? nbt.getInt("Size") : stacks.size());
        var tagList = nbt.getList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            var itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < stacks.size()) {
                stacks.set(slot, ItemStack.read(itemTags));
            }
        }
        onLoad();
    }
}
