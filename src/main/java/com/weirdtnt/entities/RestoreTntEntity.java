package com.weirdtnt.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

public class RestoreTntEntity extends PrimedTnt {
    public RestoreTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    public RestoreTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.level().isClientSide) {
            if (fuse <= 0) {
                restoreCluster();
                this.discard();
                return;
            }
        }

        this.level().addParticle(
                ParticleTypes.SMOKE,
                this.getX(), this.getY() + 0.5, this.getZ(),
                0.0, 0.0, 0.0
        );

        this.tickCount++;
    }
    private void restoreCluster() {
        int radius = 6;
        BlockPos center = this.blockPosition();
        Level world = this.level();
        RandomSource random = world.getRandom();

        int avgSurface = 0, count = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos sample = center.offset(dx, 0, dz);
                int surfaceY = world.getHeight(Heightmap.Types.WORLD_SURFACE, sample.getX(), sample.getZ());
                avgSurface += surfaceY;
                count++;
            }
        }
        avgSurface /= Math.max(count, 1);

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    BlockPos target = center.offset(dx, dy, dz);
                    double distance = Math.sqrt(target.distSqr(center));
                    if (distance > radius) continue;

                    int surfaceHeight = world.getHeight(Heightmap.Types.WORLD_SURFACE, target.getX(), target.getZ());

                    double falloff = Math.exp(-(distance * distance) / (2.0 * radius * radius));
                    int blendedHeight = (int) Math.round(avgSurface * falloff + surfaceHeight * (1 - falloff));

                    if (target.getY() <= blendedHeight) {
                        BlockState state;

                        double layerFactor = (double)(blendedHeight - target.getY()) / (radius);
                        if (layerFactor > 0.6) {
                            state = Blocks.STONE.defaultBlockState();
                        } else if (layerFactor > 0.3) {
                            state = Blocks.DIRT.defaultBlockState();
                        } else {
                            state = Blocks.GRASS_BLOCK.defaultBlockState();
                        }

                        if (!world.getBlockState(target).is(Blocks.BEDROCK)) {
                            world.setBlock(target, state, 3);
                        }

                        if (state.is(Blocks.GRASS_BLOCK) && world.getBlockState(target.above()).isAir()) {
                            if (random.nextInt(20) == 0) {
                                placeDecoration(target.above(), random);
                            }
                        }
                    }
                }
            }
        }
    }

    private void placeDecoration(BlockPos pos, RandomSource random) {
        Level world = this.level();
        int roll = random.nextInt(10);
        if (roll < 6) {
            world.setBlock(pos, Blocks.TALL_GRASS.defaultBlockState(), 3);
        } else if (roll < 8) {
            world.setBlock(pos, Blocks.DANDELION.defaultBlockState(), 3);
        } else if (roll == 8) {
            world.setBlock(pos, Blocks.OAK_SAPLING.defaultBlockState(), 3);
        }
    }


}