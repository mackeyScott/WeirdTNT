package com.weirdtnt.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ChunkTntEntity extends TntEntity {
    public ChunkTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    public ChunkTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.getWorld().isClient) {
            if (fuse <= 0) {
                nukeChunk();
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

    private void nukeChunk() {
        World world = this.getWorld();
        BlockPos center = this.getBlockPos();
        ChunkPos chunkPos = new ChunkPos(center);

        int startX = chunkPos.getStartX();
        int startZ = chunkPos.getStartZ();
        int endX = chunkPos.getEndX();
        int endZ = chunkPos.getEndZ();

        int minY = world.getBottomY() + 1;
        int maxY = world.getTopY();

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                for (int y = minY; y < maxY; y++) {
                    BlockPos target = new BlockPos(x, y, z);
                    world.setBlockState(target, Blocks.AIR.getDefaultState(), 3);
                }
            }
        }

        world.playSound(null, center,
                SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.BLOCKS,
                4.0F, 1.0F);

        for (int i = 0; i < 100; i++) {
            double px = center.getX() + world.random.nextDouble() * 16;
            double py = minY + world.random.nextDouble() * (maxY - minY);
            double pz = center.getZ() + world.random.nextDouble() * 16;
            world.addParticle(ParticleTypes.EXPLOSION, px, py, pz, 0, 0, 0);
        }
    }

}