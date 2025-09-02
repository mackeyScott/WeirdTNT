package com.weirdtnt.blocks;

import com.weirdtnt.entities.LavaTntEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LavaTNT extends Block {
    public LavaTNT(Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block,
                               BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            ignite(world, pos, null);
            world.removeBlock(pos, false);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        ignite(world, pos, player);
        world.removeBlock(pos, false);
        return ActionResult.SUCCESS;
    }

    private void ignite(World world, BlockPos pos, @Nullable LivingEntity igniter) {
        if (!world.isClient) {
            // Instead of TNT entity, spawn your LavaTntEntity
            LavaTntEntity entity = new LavaTntEntity(world,
                    pos.getX() + 0.5,
                    pos.getY(),
                    pos.getZ() + 0.5,
                    igniter);

            world.spawnEntity(entity);
            world.playSound(null, pos, SoundEvents.ENTITY_TNT_PRIMED,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }
}