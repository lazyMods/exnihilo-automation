package lazy.exnihiloauto.tiles;

import com.google.common.base.Preconditions;
import lazy.exnihiloauto.Configs;
import lazy.exnihiloauto.inventory.InvHandler;
import lazy.exnihiloauto.inventory.container.AutoSieveContainer;
import lazy.exnihiloauto.setup.ModTiles;
import lazy.exnihiloauto.utils.ExNihiloUtils;
import lombok.var;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import novamachina.exnihilosequentia.common.item.mesh.MeshItem;
import novamachina.exnihilosequentia.common.utility.Config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AutoSieveTile extends AutoTileEntity implements ITickableTileEntity, INamedContainerProvider {

    public static final int INV_SIZE = 14;

    public AutoSieveTile() {
        super(ModTiles.AUTO_SIEVE.get(), "Auto Sieve");
    }

    @Override
    public void tick() {
        Preconditions.checkNotNull(this.world);
        if(!this.world.isRemote) {
            this.setFakePlayer((ServerWorld) this.world, "FakeSiever");
            boolean hasSiftable = !this.tileInv.isSlotEmpty(0);
            boolean hasMesh = !this.tileInv.isSlotEmpty(1);
            if(hasSiftable && hasMesh) {
                var siftable = this.tileInv.getItem(0);
                var mesh = ((MeshItem) this.tileInv.getItem(1)).getMesh();
                var sieveDrops = ExNihiloUtils.getWithMeshChance(siftable, mesh, this.world.rand);
                if(this.tileInv.canInsertAll(sieveDrops)) {
                    if(this.storage.canExtractAmount(1)) {
                        this.incrementTimer();
                        this.storage.decreaseEnergy(1);
                    }
                    if(this.isDone()) {
                        for (ItemStack stack : sieveDrops) {
                            if(this.tileInv.hasStack(stack)) {
                                int slot = this.tileInv.checkAndGetStack(stack);
                                this.tileInv.insertItem(slot, stack, false);
                            } else if(tileInv.hasEmptySlot()) {
                                int slot = this.tileInv.checkAndGetEmptySlot();
                                this.tileInv.insertItem(slot, stack, false);
                            }
                        }
                        if(Config.enableMeshDurability()) this.tileInv.getStackInSlot(2).damageItem(1, this.fakePlayer, player -> {});
                        this.tileInv.extractItem(0, 1, false);
                        this.resetTimer();
                    }
                }
            }
        }
    }

    @Override
    public int getFinishTime() {
        return Configs.AUTO_SIEVE_SPEED.get();
    }

    @Override
    public int getEnergyCapacity() {
        return Configs.AUTO_SIEVE_ENERGY_CAPACITY.get();
    }

    public InvHandler createInventory() {
        return new InvHandler(INV_SIZE) {
            @Override
            public boolean canInsertOn(int slot) {
                return slot > 1;
            }

            @Override
            @Nonnull
            public int[] getSlotsForFace(@Nonnull Direction side) {
                if(side == Direction.DOWN) {
                    return new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
                } else if(side == Direction.UP) {
                    return new int[]{0};
                }
                return new int[0];
            }

            @Override
            public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn, @Nullable Direction direction) {
                return direction == Direction.UP && index == 0;
            }

            @Override
            public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
                return direction == Direction.DOWN && index > 1;
            }
        };
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return new AutoSieveContainer(id, inventory, this.tileInv, this.data);
    }
}
