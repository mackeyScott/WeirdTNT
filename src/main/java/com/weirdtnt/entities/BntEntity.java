package com.weirdtnt.entities;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

public class BntEntity extends PrimedTnt {

    public BntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    public BntEntity(Level world, double x, double y, double z, LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);

    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.level().isClientSide && fuse <= 0) {
            spawnBees();
            this.discard();
            return;
        }

        this.level().addParticle(
                net.minecraft.core.particles.ParticleTypes.SMOKE,
                this.getX(), this.getY() + 0.5, this.getZ(),
                0, 0, 0
        );

        this.tickCount++;
    }


    private void spawnBees() {
        Level world = this.level();
        BlockPos center = this.blockPosition();

        world.playSound(null, center,
                SoundEvents.GENERIC_EXPLODE,
                SoundSource.BLOCKS,
                4.0F, 1.0F);
        int beeCount = 25;
        for (int i = 0; i < beeCount; i++) {
            Bee bee = EntityType.BEE.create(world);
            if (bee != null) {
                double offsetX = world.random.nextDouble() * 2 - 1;
                double offsetZ = world.random.nextDouble() * 2 - 1;

                bee.moveTo(
                        center.getX() + 0.5 + offsetX,
                        center.getY() + 0.5,
                        center.getZ() + 0.5 + offsetZ,
                        0,
                        0
                );

                bee.setRemainingPersistentAngerTime(3600);

                List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, bee.getBoundingBox().inflate(16), e -> !(e instanceof Bee));
                for (LivingEntity target : targets) {
                    bee.setTarget(target);
                }
                world.addFreshEntity(bee);
            }
        }
    }
}