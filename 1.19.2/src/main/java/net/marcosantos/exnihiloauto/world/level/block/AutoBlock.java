package net.marcosantos.exnihiloauto.world.level.block;

import java.util.function.BiFunction;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class AutoBlock extends Block implements EntityBlock {

	private final BiFunction<BlockPos, BlockState, BlockEntity> beSup;

	public AutoBlock(BiFunction<BlockPos, BlockState, BlockEntity> beSup) {
		super(BlockBehaviour.Properties.of(Material.HEAVY_METAL).destroyTime(1.f));
		this.beSup = beSup;
	}

	@Override
	@ParametersAreNonnullByDefault
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return beSup.apply(blockPos, blockState);
	}

	@Override
	@ParametersAreNonnullByDefault
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return ((level1, blockPos, blockState, t) -> ((AutoBlockEntity) t).tick(level, blockPos, blockState, (AutoBlockEntity) t));
	}

	@Override
	@ParametersAreNonnullByDefault
	public @NotNull InteractionResult use(BlockState p_60503_, Level level, BlockPos pos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
		if (level.isClientSide())
			return InteractionResult.SUCCESS;
		if (level.getBlockEntity(pos) instanceof AutoBlockEntity te) {
			player.openMenu(te);
		}
		return InteractionResult.CONSUME;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		super.playerWillDestroy(level, pos, state, player);
		if (!level.isClientSide) {
			if (level.getBlockEntity(pos) instanceof AutoBlockEntity be) {
				Containers.dropContents(level, pos, be.tileInv);
			}
		}
	}
}
