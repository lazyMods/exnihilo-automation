package lazy.exnihiloauto.tiles;

import com.google.common.base.Preconditions;
import lazy.exnihiloauto.Configs;
import lazy.exnihiloauto.inventory.InvHandler;
import lazy.exnihiloauto.inventory.container.AutoSilkerContainer;
import lazy.exnihiloauto.setup.ModItems;
import lazy.exnihiloauto.setup.ModTiles;
import lombok.val;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import novamachina.exnihilosequentia.common.init.ExNihiloItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class AutoSilkerTile extends AutoTileEntity implements ITickableTileEntity {

    public static final int INV_SIZE = 6;
    protected final LazyOptional<IItemHandlerModifiable>[] invCap = SidedInvWrapper.create(tileInv, Direction.UP, Direction.DOWN, Direction.SOUTH);

    public static final String TAG_SILKWORM_TIMER = "silkworm_effect_time";
    public int silkwormTimer = 0;

    public static final int DATA_SIZE = 6;
    protected final IIntArray data = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return storage.getEnergyStored();
                case 1:
                    return storage.getMaxEnergyStored();
                case 2:
                    return timer;
                case 3:
                    return calcTime();
                case 4:
                    return silkwormTimer;
                case 5:
                    return calcTime() / 4;
                default:
                    return -1;
            }
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int getCount() {
            return DATA_SIZE;
        }
    };

    public AutoSilkerTile() {
        super(ModTiles.AUTO_SILKER.get(), "tiles.title.silker");
    }

    @Override
    public void tick() {
        Preconditions.checkNotNull(this.level);
        if (!this.level.isClientSide) {
            boolean hasSilk = !this.tileInv.isSlotEmpty(0);
            boolean hasLeaves = !this.tileInv.isSlotEmpty(1);
            if (hasSilk && hasLeaves) {
                if (!this.tileInv.isSlotFull(2)) {
                    if (this.storage.canExtractAmount(1)) {
                        this.incrementTimer();
                        this.incrementSilkwormTime();
                        this.storage.decreaseEnergy(1);
                    }
                    if (this.isDone()) {
                        this.tileInv.extractItem(0, 1, false);
                        this.resetTimer();
                    }
                    if(this.isSilkwormDone()){
                        int count = this.hasUpgrade(ModItems.BONUS_UPGRADE) && this.level.random.nextFloat() < (.25f * this.getCountOf(ModItems.BONUS_UPGRADE)) ? 2 : 1;
                        this.tileInv.insertItem(2, new ItemStack(Items.STRING, count), false);
                        this.tileInv.extractItem(1, 1, false);
                        this.resetSilkwormTimer();
                    }
                }
            }
        }
    }

    public void incrementSilkwormTime(){
        this.silkwormTimer++;
        this.setChanged();
    }

    public boolean isSilkwormDone(){
        return this.silkwormTimer >= this.calcTime() / 4;
    }

    public void resetSilkwormTimer(){
        this.silkwormTimer = 0;
        this.setChanged();
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        this.silkwormTimer = nbt.getInt(TAG_SILKWORM_TIMER);
    }

    @Nonnull
    @Override
    public CompoundNBT save(@Nonnull CompoundNBT compound) {
        val nbt = super.save(compound);
        nbt.putInt(TAG_SILKWORM_TIMER, this.silkwormTimer);
        return nbt;
    }

    @Override
    public InvHandler createInventory() {
        return new InvHandler(INV_SIZE) {
            @Override
            public boolean canInsertOn(int slot) {
                return slot == 0 || slot == 1;
            }

            @Override
            @Nonnull
            @ParametersAreNonnullByDefault
            public int[] getSlotsForFace(Direction side) {
                if (side == Direction.DOWN) return new int[]{2};
                if (side == Direction.UP) return new int[]{1};
                if (side == Direction.SOUTH) return new int[]{0};
                return new int[0];
            }

            @Override
            public boolean canPlaceItemThroughFace(int index, @Nonnull ItemStack itemStackIn, @Nullable Direction direction) {
                if (direction == Direction.UP || direction == Direction.SOUTH) {
                    if (index == 0 && itemStackIn.getItem() == ExNihiloItems.SILKWORM.get())
                        return true;
                }
                return index == 1 && itemStackIn.getItem() instanceof BlockItem && Block.byItem(itemStackIn.getItem()).is(BlockTags.LEAVES);
            }

            @Override
            @ParametersAreNonnullByDefault
            public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
                return direction == Direction.DOWN && index == 2;
            }
        };
    }

    @Override
    public int getFinishTime() {
        return Configs.AUTO_SILKER_SPEED.get();
    }

    @Override
    public int getEnergyCapacity() {
        return Configs.AUTO_SILKER_ENERGY_CAPACITY.get();
    }

    @Override
    public List<ItemStack> getUpgradeSlots() {
        return this.tileInv.getStackFromTo(3, 5);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new AutoSilkerContainer(windowId, playerInventory, this.tileInv, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.isRemoved() && side != null) {
            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                switch (side) {
                    case UP:
                        return this.invCap[0].cast();
                    case DOWN:
                        return this.invCap[1].cast();
                    case SOUTH:
                        return this.invCap[2].cast();
                }
            } else if (cap == CapabilityEnergy.ENERGY && side != Direction.SOUTH && Direction.Plane.HORIZONTAL.test(side)) {
                return this.energyCap.cast();
            }
        }
        return super.getCapability(cap, side);
    }
}
