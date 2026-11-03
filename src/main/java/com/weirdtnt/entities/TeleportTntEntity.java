package com.weirdtnt.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TeleportTntEntity extends PrimedTnt {

    public TeleportTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    public TeleportTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.level().isClientSide) {
            if (this.tickCount % 5 == 0) {
                teleportRandomly();
            }

            if (fuse <= 0) {
                explode();
                level().playSound(null, this.blockPosition(),
                        SoundEvents.GENERIC_EXPLODE,
                        SoundSource.BLOCKS,
                        4.0F, 1.0F);
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

    private void teleportRandomly() {
        double radius = 5;
        double dx = (this.random.nextDouble() * 2 - 1) * radius;
        double dz = (this.random.nextDouble() * 2 - 1) * radius;

        double newX = this.getX() + dx;
        double newY = this.getY(); // same Y
        double newZ = this.getZ() + dz;

        BlockPos targetPos = BlockPos.containing(newX, newY, newZ);
        BlockState targetState = this.level().getBlockState(targetPos);
        if (targetState.isAir()) {
            this.moveTo(newX, newY, newZ, this.getYRot(), this.getXRot());
            this.hasImpulse = true;

            this.level().addParticle(ParticleTypes.PORTAL, newX, newY, newZ, 0, 0, 0);
            this.level().playSound(
                    null, this.blockPosition(),
                    SoundEvents.ENDERMAN_TELEPORT,
                    SoundSource.BLOCKS, 0.5f, 1.0f
            );
        }
    }


    private void explode() {
        Explosion explosion = new Explosion(
                this.level(),
                this,
                this.getX(), this.getY(), this.getZ(),
                4.0f,
                true,
                Explosion.BlockInteraction.DESTROY_WITH_DECAY
        );

        explosion.explode();
        explosion.finalizeExplosion(true);
        this.level().playSound(null, this.blockPosition(), SoundEvents.GENERIC_EXPLODE,
                SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}
