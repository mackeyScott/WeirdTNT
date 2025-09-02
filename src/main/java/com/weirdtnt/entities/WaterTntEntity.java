package com.weirdtnt.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WaterTntEntity extends TntEntity {
    public WaterTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
    }

    public WaterTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient && this.getFuse() == 1) {
            explodeWater();
            this.remove(RemovalReason.DISCARDED);
        }
    }

    private void explodeWater() {
        int radius = 4;
        BlockPos center = this.getBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos target = center.add(dx, dy, dz);
                    if (target.getManhattanDistance(center) <= radius) {
                        this.getWorld().setBlockState(target, Blocks.WATER.getDefaultState(), 3);
                        getWorld().playSound(null, center, SoundEvents.ENTITY_GENERIC_EXPLODE,
                                SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                }
            }
        }
    }
}
