package lazy.exnihiloauto.block;

import lazy.exnihiloauto.tiles.AutoTileEntity;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.function.Supplier;

public class AutoBlock extends Block {

    private final Supplier<TileEntity> tileEntitySupplier;

    public AutoBlock(Supplier<TileEntity> tileEntitySupplier) {
        super(Properties.create(Material.IRON));
        this.tileEntitySupplier = tileEntitySupplier;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return tileEntitySupplier != null;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileEntitySupplier != null ? tileEntitySupplier.get() : null;
    }

    @Override
    @SuppressWarnings({"deprecation", "NullableProblems"})
    @Nonnull
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            if (worldIn.getTileEntity(pos) instanceof AutoTileEntity) {
                player.openContainer((INamedContainerProvider) worldIn.getTileEntity(pos));
            }
            return ActionResultType.CONSUME;
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if (!worldIn.isRemote) {
            if (worldIn.getTileEntity(pos) instanceof AutoTileEntity) {
                var tileEntity = (AutoTileEntity) worldIn.getTileEntity(pos);
                Objects.requireNonNull(tileEntity);
                InventoryHelper.dropInventoryItems(worldIn, pos, tileEntity.getTileInv());
            }
        }
    }
}
