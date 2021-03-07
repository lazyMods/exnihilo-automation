package lazy.exnihiloauto.tiles;

import com.mojang.authlib.GameProfile;
import lazy.exnihiloauto.inventory.InvHandler;
import lazy.exnihiloauto.utils.EnergyData;
import lombok.var;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
                    return getFinishTime();
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

    public void incrementTimer() {
        this.timer++;
        this.markDirty();
    }

    public void resetTimer() {
        this.timer = 0;
        this.markDirty();
    }

    public boolean isDone() {
        return this.timer >= this.getFinishTime();
    }

    public FakePlayer createFakePlayer(ServerWorld world, String name) {
        return new FakePlayer(world, new GameProfile(UUID.randomUUID(), name));
    }

    public void setFakePlayer(ServerWorld world, String name) {
        if(this.fakePlayer != null) return;
        this.fakePlayer = this.createFakePlayer(world, name);
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
        if(!this.isRemoved() && side != null) {
            if(cap == CapabilityEnergy.ENERGY && Direction.Plane.HORIZONTAL.test(side)) {
                return this.energyCap.cast();
            } else if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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
        return new StringTextComponent(this.containerTitle);
    }
}
