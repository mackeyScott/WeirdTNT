package com.weirdtnt.blocks;

import com.weirdtnt.entities.OTowerTntEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class OTowerTNT extends TntBlock {
    public OTowerTNT(Properties settings) {
        super(settings);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block,
                               BlockPos fromPos, boolean notify) {
        if (world.hasNeighborSignal(pos)) {
            ignite(world, pos, null);
            world.removeBlock(pos, false);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos,
                              Player player, InteractionHand hand, BlockHitResult hit) {
        ignite(world, pos, player);
        world.removeBlock(pos, false);
        return InteractionResult.SUCCESS;
    }

    private void ignite(Level world, BlockPos pos, @Nullable LivingEntity igniter) {
        if (!world.isClientSide) {
            OTowerTntEntity entity = new OTowerTntEntity(world,
                    pos.getX() + 0.5,
                    pos.getY(),
                    pos.getZ() + 0.5,
                    igniter);

            world.addFreshEntity(entity);
            world.playSound(null, pos, SoundEvents.TNT_PRIMED,
                    SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }
}