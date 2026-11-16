package com.weirdtnt.entities;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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

        if (!this.level().isClientSide() && fuse <= 0) {
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
                SoundEvents.GENERIC_EXPLODE.value(),
                SoundSource.BLOCKS,
                4.0F, 1.0F);
        int beeCount = 25;
        for (int i = 0; i < beeCount; i++) {
            
            Mob bee = EntityTypes.BEE.create(world, net.minecraft.world.entity.EntitySpawnReason.COMMAND);
            if (bee != null) {
                double offsetX = world.getRandom().nextDouble() * 2 - 1;
                double offsetZ = world.getRandom().nextDouble() * 2 - 1;

                bee.setPos(center.getX() + offsetX, center.getY(), center.getZ() + offsetZ);

                List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, bee.getBoundingBox().inflate(16), e -> e.getType() != EntityTypes.BEE);
                for (LivingEntity target : targets) {
                    bee.setTarget(target);
                }
                world.addFreshEntity(bee);
            }
        }
    }
}