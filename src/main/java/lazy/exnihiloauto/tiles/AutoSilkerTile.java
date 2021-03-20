package lazy.exnihiloauto.tiles;

import com.google.common.base.Preconditions;
import lazy.exnihiloauto.Configs;
import lazy.exnihiloauto.inventory.InvHandler;
import lazy.exnihiloauto.inventory.container.AutoSilkerContainer;
import lazy.exnihiloauto.setup.ModTiles;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import novamachina.exnihilosequentia.common.item.resources.EnumResource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class AutoSilkerTile extends AutoTileEntity implements ITickableTileEntity {

    public static final int INV_SIZE = 3;

    public AutoSilkerTile() {
        super(ModTiles.AUTO_SILKER.get(), "Auto Silker");
    }

    @Override
    public void tick() {
        Preconditions.checkNotNull(this.world);
        if (!this.world.isRemote) {
            boolean hasSilk = !this.tileInv.isSlotEmpty(0);
            boolean hasLeaves = !this.tileInv.isSlotEmpty(1);
            if (hasSilk && hasLeaves) {
                if (!this.tileInv.isSlotFull(2)) {
                    if (this.storage.canExtractAmount(1)) {
                        this.incrementTimer();
                        this.storage.decreaseEnergy(1);
                    }
                    if (this.isDone()) {
                        this.tileInv.extractItem(0, 1, false);
                        this.tileInv.extractItem(1, 1, false);
                        this.tileInv.insertItem(2, new ItemStack(Items.STRING), false);
                        this.resetTimer();
                    }
                }
            }
        }
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
                if (side == Direction.UP) return new int[]{0, 1};
                return new int[0];
            }

            @Override
            public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
                if (direction != Direction.UP) return false;
                if (index == 0 && itemStackIn.getItem() == EnumResource.SILKWORM.getRegistryObject().get()) return true;
                return index == 1 && itemStackIn.getItem() instanceof BlockItem && Block.getBlockFromItem(itemStackIn.getItem()).isIn(BlockTags.LEAVES);
            }

            @Override
            @ParametersAreNonnullByDefault
            public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
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

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new AutoSilkerContainer(windowId, playerInventory, this.tileInv, this.data);
    }
}
