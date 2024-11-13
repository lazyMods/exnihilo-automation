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
        super(Properties.of(Material.HEAVY_METAL));
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
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            if (worldIn.getBlockEntity(pos) instanceof AutoTileEntity) {
                player.openMenu((INamedContainerProvider) worldIn.getBlockEntity(pos));
            }
            return ActionResultType.CONSUME;
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        if (!worldIn.isClientSide) {
            if (worldIn.getBlockEntity(pos) instanceof AutoTileEntity) {
                var tileEntity = (AutoTileEntity) worldIn.getBlockEntity(pos);
                Objects.requireNonNull(tileEntity);
                InventoryHelper.dropContents(worldIn, pos, tileEntity.getTileInv());
            }
        }
    }
}
