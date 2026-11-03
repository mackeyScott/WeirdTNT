package com.weirdtnt.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ClusterTntEntity extends PrimedTnt {
    public ClusterTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
    }

    public ClusterTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && this.getFuse() == 1) {
            explodeCluster();
            level().playSound(null, this.blockPosition(),
                    SoundEvents.GENERIC_EXPLODE,
                    SoundSource.BLOCKS,
                    4.0F, 1.0F);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    private void explodeCluster() {
        int radius = 4;
        BlockPos center = this.blockPosition();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos target = center.offset(dx, dy, dz);
                    int distance = target.distManhattan(center);

                    if (distance <= radius) {
                        PrimedTnt tnt = new PrimedTnt(
                                this.level(),
                                target.getX() + 0.5,
                                target.getY(),
                                target.getZ() + 0.5,
                                null
                        );

                        int fuse = 40 + (distance * 10);
                        tnt.setFuse(fuse);

                        this.level().addFreshEntity(tnt);
                    }
                }
            }
        }

        this.level().playSound(
                null,
                center,
                SoundEvents.GENERIC_EXPLODE,
                SoundSource.BLOCKS,
                1.0F,
                1.0F
        );
    }

}