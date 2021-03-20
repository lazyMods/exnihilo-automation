package lazy.exnihiloauto.block;

import lazy.exnihiloauto.setup.ModTiles;
import lazy.exnihiloauto.tiles.AutoSieveTile;
import lazy.exnihiloauto.tiles.AutoSilkerTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AutoSilkerBlock extends Block {

    public AutoSilkerBlock() {
        super(Properties.create(Material.IRON));
    }

    @SuppressWarnings({"NullableProblems", "deprecation"})
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            if(worldIn.getTileEntity(pos) instanceof AutoSilkerTile) {
                player.openContainer((INamedContainerProvider) worldIn.getTileEntity(pos));
            }
            return ActionResultType.CONSUME;
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTiles.AUTO_SILKER.get().create();
    }
}
