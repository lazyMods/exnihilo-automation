package lazy.exnihiloauto.tiles;

import com.mojang.authlib.GameProfile;
import lazy.exnihiloauto.inventory.InvHandler;
import lazy.exnihiloauto.setup.ModItems;
import lazy.exnihiloauto.utils.EnergyData;
import lombok.var;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class AutoTileEntity extends TileEntity implements INamedContainerProvider {

    public static final String TAG_SIEVE_TIME = "SieveCurrentTime";
    private int timer = 0;

    public static final String TAG_INV = "Inventory";
    protected final InvHandler tileInv = this.createInventory();
    protected final LazyOptional<IItemHandlerModifiable>[] invCap = SidedInvWrapper.create(tileInv, Direction.UP, Direction.DOWN);

    public static final String TAG_ENERGY = "Power";
    protected final EnergyData storage = new EnergyData(this.getEnergyCapacity(), true, false);
    protected final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> storage);

    private final String containerTitle;
    protected FakePlayer fakePlayer;

    public static final int DATA_SIZE = 4;
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
                default:
                    return -1;
            }
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int size() {
            return DATA_SIZE;
        }
    };

    public AutoTileEntity(TileEntityType<?> tileEntityTypeIn, String containerTitle) {
        super(tileEntityTypeIn);
        this.containerTitle = containerTitle;
    }

    public abstract InvHandler createInventory();

    public abstract int getFinishTime();

    public abstract int getEnergyCapacity();

    public InvHandler getTileInv() {
        return this.tileInv;
    }

    public void incrementTimer() {
        this.timer++;
        this.markDirty();
    }

    public void resetTimer() {
        this.timer = 0;
        this.markDirty();
    }

    public boolean isDone() {
        return this.timer >= this.calcTime();
    }

    public FakePlayer createFakePlayer(ServerWorld world, String name) {
        return new FakePlayer(world, new GameProfile(UUID.randomUUID(), name));
    }

    public void setFakePlayer(ServerWorld world, String name) {
        if (this.fakePlayer != null) return;
        this.fakePlayer = this.createFakePlayer(world, name);
    }

    public abstract List<ItemStack> getUpgradeSlots();

    public boolean addUpgrade(ItemStack stack) {
        if (stack.getItem() == Items.AIR) return false;
        this.getUpgradeSlots().add(stack);
        return true;
    }

    public boolean hasUpgrade(ItemStack stack) {
        return this.getUpgradeSlots().stream().map(ItemStack::getItem).anyMatch(item -> item == stack.getItem());
    }

    public boolean hasUpgrade(RegistryObject<Item> upgradeObj) {
        return this.hasUpgrade(new ItemStack(upgradeObj.get()));
    }

    public int getCountOf(RegistryObject<Item> upgradeObj) {
        return (int) this.getUpgradeSlots().stream().map(ItemStack::getItem).filter(i -> i == upgradeObj.get()).count();
    }

    public boolean canApplyUpgrade() {
        return this.getUpgradeSlots().size() == this.getMaxUpgrades();
    }

    public int getMaxUpgrades() {
        return 3;
    }

    @Override
    @Nonnull
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        var nbt = super.write(compound);
        nbt.putInt(TAG_SIEVE_TIME, this.timer);
        nbt.put(TAG_INV, this.tileInv.serializeNBT());
        nbt.put(TAG_ENERGY, this.storage.writeNBT());
        return nbt;
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.read(state, nbt);
        this.timer = nbt.getInt(TAG_SIEVE_TIME);
        this.tileInv.deserializeNBT(nbt.getCompound(TAG_INV));
        this.storage.readNBT(nbt.getCompound(TAG_ENERGY));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.isRemoved() && side != null) {
            if (cap == CapabilityEnergy.ENERGY && Direction.Plane.HORIZONTAL.test(side)) {
                return this.energyCap.cast();
            } else if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                switch (side) {
                    case UP:
                        return this.invCap[0].cast();
                    case DOWN:
                        return this.invCap[1].cast();
                }
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        for (var iItemHandlerModifiableLazyOptional : this.invCap) {
            iItemHandlerModifiableLazyOptional.invalidate();
        }
        this.energyCap.invalidate();
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(this.containerTitle);
    }

    private int calcTime() {
        int speedBonus = this.hasUpgrade(ModItems.SPEED_UPGRADE) ? getCountOf(ModItems.SPEED_UPGRADE) * 20 : 0;
        return this.getFinishTime() - speedBonus;
    }
}
