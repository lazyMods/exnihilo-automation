package lazy.exnihiloauto.tiles;

import com.google.common.base.Preconditions;
import lazy.exnihiloauto.Configs;
import lazy.exnihiloauto.block.compressed.CompressedBlock;
import lazy.exnihiloauto.inventory.InvHandler;
import lazy.exnihiloauto.inventory.container.AutoHammerContainer;
import lazy.exnihiloauto.items.ReinforcedHammerItem;
import lazy.exnihiloauto.setup.ModItems;
import lazy.exnihiloauto.setup.ModTiles;
import lazy.exnihiloauto.utils.EnumCompressedBlocks;
import lazy.exnihiloauto.utils.ExNihiloUtils;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import novamachina.exnihilosequentia.api.ExNihiloRegistries;
import novamachina.exnihilosequentia.common.item.tools.hammer.HammerBaseItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class AutoHammerTile extends AutoTileEntity implements ITickableTileEntity {

    public static final int INV_SIZE = 6;

    public AutoHammerTile() {
        super(ModTiles.AUTO_HAMMER.get(), "tiles.title.hammer");
    }

    @Override
    public void tick() {
        Preconditions.checkNotNull(this.level);
        if (!this.level.isClientSide) {
            this.setFakePlayer((ServerWorld) this.level, "FakeHammer");

            boolean hasHammer = !this.tileInv.isSlotEmpty(0) && this.hasTheCorrectHammer();
            boolean hasBlockToHammer = !this.tileInv.isSlotEmpty(1);
            if (hasHammer && hasBlockToHammer) {
                boolean canHammer = this.canHammerCompressedBlocks() || ExNihiloRegistries.HAMMER_REGISTRY.isHammerable(this.tileInv.getBlockItem(1));
                if (canHammer) {
                    var drop = this.getDrop();
                    if (this.tileInv.canInsertItemOnSlot(2, drop)) {
                        if (this.storage.canExtractAmount(1)) {
                            this.incrementTimer();
                            this.storage.decreaseEnergy(1);
                        }
                        if (this.isDone()) {
                            this.tileInv.insertItem(2, drop, false);
                            this.tileInv.extractItem(1, 1, false);
                            this.tileInv.getItem(0).hurtAndBreak(1, this.fakePlayer, player -> {
                            });
                            this.resetTimer();
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getFinishTime() {
        return Configs.AUTO_HAMMER_SPEED.get();
    }

    @Override
    public int getEnergyCapacity() {
        return Configs.AUTO_HAMMER_ENERGY_CAPACITY.get();
    }

    @Override
    public InvHandler createInventory() {
        return new InvHandler(INV_SIZE) {
            @Override
            public boolean canInsertOn(int slot) {
                return slot == 2;
            }

            @Override
            @Nonnull
            public int[] getSlotsForFace(@Nonnull Direction side) {
                if (side == Direction.UP) {
                    return new int[]{0, 1};
                } else if (side == Direction.DOWN) {
                    return new int[]{2};
                }
                return new int[0];
            }

            @Override
            public boolean canPlaceItemThroughFace(int index, @Nonnull ItemStack itemStackIn, Direction direction) {
                if (index == 2) return false;
                return index == 0 && itemStackIn.getItem() instanceof HammerBaseItem
                        || index == 1 && (itemStackIn.getItem() instanceof BlockItem
                        && ExNihiloRegistries.HAMMER_REGISTRY.isHammerable(Block.byItem(itemStackIn.getItem())) || canHammerCompressedBlocks());
            }

            @Override
            public boolean canTakeItemThroughFace(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
                return index == 2;
            }
        };
    }

    @Override
    public List<ItemStack> getUpgradeSlots() {
        return this.tileInv.getStackFromTo(3, 5);
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity player) {
        return new AutoHammerContainer(id, playerInventory, this.tileInv, this.data);
    }


    private boolean canHammerCompressedBlocks() {
        return this.hasUpgrade(ModItems.REINFORCED_UPGRADE) && this.tileInv.getBlockItem(1) instanceof CompressedBlock;
    }

    private ItemStack getDrop() {
        Preconditions.checkNotNull(this.level);
        int count = this.hasUpgrade(ModItems.BONUS_UPGRADE) && this.level.random.nextFloat() < (.25f * this.getCountOf(ModItems.BONUS_UPGRADE)) ? 2 : 1;
        if (this.canHammerCompressedBlocks() && this.tileInv.getBlockItem(1) instanceof CompressedBlock)
            return new ItemStack(EnumCompressedBlocks.getCompressed((CompressedBlock) this.tileInv.getBlockItem(1)), count);
        return new ItemStack(ExNihiloUtils.getHammeredOutput(this.tileInv.getBlockItem(1)).getItem(), count);
    }

    private boolean hasTheCorrectHammer() {
        return this.canHammerCompressedBlocks() ? this.tileInv.get(0) instanceof ReinforcedHammerItem
                : this.tileInv.get(0) instanceof HammerBaseItem;
    }
}
