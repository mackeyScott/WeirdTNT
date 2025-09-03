package com.weirdtnt.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class RestoreTntEntity extends TntEntity {
    public RestoreTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    public RestoreTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.getWorld().isClient) {
            if (fuse <= 0) {
                restoreCluster();
                this.discard();
                return;
            }
        }

        this.getWorld().addParticle(
                ParticleTypes.SMOKE,
                this.getX(), this.getY() + 0.5, this.getZ(),
                0.0, 0.0, 0.0
        );

        this.age++;
    }
    private void restoreCluster() {
        int radius = 6;
        BlockPos center = this.getBlockPos();
        World world = this.getWorld();
        Random random = world.getRandom();

        int avgSurface = 0, count = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos sample = center.add(dx, 0, dz);
                int surfaceY = world.getTopY(Heightmap.Type.WORLD_SURFACE, sample.getX(), sample.getZ());
                avgSurface += surfaceY;
                count++;
            }
        }
        avgSurface /= Math.max(count, 1);

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    BlockPos target = center.add(dx, dy, dz);
                    double distance = Math.sqrt(target.getSquaredDistance(center));
                    if (distance > radius) continue;

                    int surfaceHeight = world.getTopY(Heightmap.Type.WORLD_SURFACE, target.getX(), target.getZ());

                    double falloff = Math.exp(-(distance * distance) / (2.0 * radius * radius));
                    int blendedHeight = (int) Math.round(avgSurface * falloff + surfaceHeight * (1 - falloff));

                    if (target.getY() <= blendedHeight) {
                        BlockState state;

                        double layerFactor = (double)(blendedHeight - target.getY()) / (radius);
                        if (layerFactor > 0.6) {
                            state = Blocks.STONE.getDefaultState();
                        } else if (layerFactor > 0.3) {
                            state = Blocks.DIRT.getDefaultState();
                        } else {
                            state = Blocks.GRASS_BLOCK.getDefaultState();
                        }

                        if (!world.getBlockState(target).isOf(Blocks.BEDROCK)) {
                            world.setBlockState(target, state, 3);
                        }

                        if (state.isOf(Blocks.GRASS_BLOCK) && world.getBlockState(target.up()).isAir()) {
                            if (random.nextInt(20) == 0) {
                                placeDecoration(target.up(), random);
                            }
                        }
                    }
                }
            }
        }
    }

    private void placeDecoration(BlockPos pos, Random random) {
        World world = this.getWorld();
        int roll = random.nextInt(10);
        if (roll < 6) {
            world.setBlockState(pos, Blocks.TALL_GRASS.getDefaultState(), 3);
        } else if (roll < 8) {
            world.setBlockState(pos, Blocks.DANDELION.getDefaultState(), 3);
        } else if (roll == 8) {
            world.setBlockState(pos, Blocks.OAK_SAPLING.getDefaultState(), 3);
        }
    }


}