package com.weirdtnt.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class TeleportTntEntity extends TntEntity {

    public TeleportTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    public TeleportTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.getWorld().isClient) {
            if (this.age % 5 == 0) {
                teleportRandomly();
            }

            if (fuse <= 0) {
                explode();
                getWorld().playSound(null, this.getBlockPos(),
                        SoundEvents.ENTITY_GENERIC_EXPLODE,
                        SoundCategory.BLOCKS,
                        4.0F, 1.0F);
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

    private void teleportRandomly() {
        double radius = 5;
        double dx = (this.random.nextDouble() * 2 - 1) * radius;
        double dz = (this.random.nextDouble() * 2 - 1) * radius;

        double newX = this.getX() + dx;
        double newY = this.getY(); // same Y
        double newZ = this.getZ() + dz;

        BlockPos targetPos = BlockPos.ofFloored(newX, newY, newZ);
        BlockState targetState = this.getWorld().getBlockState(targetPos);
        if (targetState.isAir()) {
            this.refreshPositionAndAngles(newX, newY, newZ, this.getYaw(), this.getPitch());
            this.velocityDirty = true;

            this.getWorld().addParticle(ParticleTypes.PORTAL, newX, newY, newZ, 0, 0, 0);
            this.getWorld().playSound(
                    null, this.getBlockPos(),
                    SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.BLOCKS, 0.5f, 1.0f
            );
        }
    }


    private void explode() {
        Explosion explosion = new Explosion(
                this.getWorld(),
                this,
                this.getX(), this.getY(), this.getZ(),
                4.0f,
                true,
                Explosion.DestructionType.DESTROY_WITH_DECAY
        );

        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(true);
        this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
}
