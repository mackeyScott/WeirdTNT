package com.weirdtnt.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class WaterTntEntity extends PrimedTnt {
    public WaterTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
    }

    public WaterTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && this.getFuse() == 1) {
            explodeWater();
            level().playSound(null, this.blockPosition(),
                    SoundEvents.GENERIC_EXPLODE,
                    SoundSource.BLOCKS,
                    4.0F, 1.0F);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    private void explodeWater() {
        int radius = 4;
        BlockPos center = this.blockPosition();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos target = center.offset(dx, dy, dz);
                    if (target.distManhattan(center) <= radius) {
                        this.level().setBlock(target, Blocks.WATER.defaultBlockState(), 3);
                        level().playSound(null, center, SoundEvents.GENERIC_EXPLODE,
                                SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                }
            }
        }
    }
}
