package com.weirdtnt.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class ChunkTntEntity extends PrimedTnt {
    public ChunkTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    public ChunkTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.level().isClientSide) {
            if (fuse <= 0) {
                nukeChunk();
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

    private void nukeChunk() {
        Level world = this.level();
        BlockPos center = this.blockPosition();
        ChunkPos chunkPos = new ChunkPos(center);

        int startX = chunkPos.getMinBlockX();
        int startZ = chunkPos.getMinBlockZ();
        int endX = chunkPos.getMaxBlockX();
        int endZ = chunkPos.getMaxBlockZ();

        int minY = world.getMinBuildHeight() + 1;
        int maxY = world.getMaxBuildHeight();

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                for (int y = minY; y < maxY; y++) {
                    BlockPos target = new BlockPos(x, y, z);
                    world.setBlock(target, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }

        world.playSound(null, center,
                SoundEvents.GENERIC_EXPLODE,
                SoundSource.BLOCKS,
                4.0F, 1.0F);

        for (int i = 0; i < 100; i++) {
            double px = center.getX() + world.random.nextDouble() * 16;
            double py = minY + world.random.nextDouble() * (maxY - minY);
            double pz = center.getZ() + world.random.nextDouble() * 16;
            world.addParticle(ParticleTypes.EXPLOSION, px, py, pz, 0, 0, 0);
        }
    }

}