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

public class ClusterTntEntity extends TntEntity {
    public ClusterTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
    }

    public ClusterTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient && this.getFuse() == 1) { // just before explosion
            explodeCluster();
            getWorld().playSound(null, this.getBlockPos(),
                    SoundEvents.ENTITY_GENERIC_EXPLODE,
                    SoundCategory.BLOCKS,
                    4.0F, 1.0F);
            this.remove(RemovalReason.DISCARDED); // remove TNT manually
        }
    }

    private void explodeCluster() {
        int radius = 4;
        BlockPos center = this.getBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos target = center.add(dx, dy, dz);

                    if (target.getManhattanDistance(center) <= radius) {
                        // Spawn primed TNT entity instead of placing TNT block
                        TntEntity tnt = new TntEntity(
                                this.getWorld(),
                                target.getX() + 0.5, // center it in the block
                                target.getY(),
                                target.getZ() + 0.5,
                                null // you can pass an igniter entity here if you want
                        );

                        this.getWorld().spawnEntity(tnt);
                    }
                }
            }
        }

        // Play explosion sound at center (optional, since TNT will also make sounds)
        this.getWorld().playSound(
                null,
                center,
                SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.BLOCKS,
                1.0F,
                1.0F
        );
    }
}