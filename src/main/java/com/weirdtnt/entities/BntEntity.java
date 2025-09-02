package com.weirdtnt.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BntEntity extends TntEntity {

    public BntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    public BntEntity(World world, double x, double y, double z, LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80); // optional fuse
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.getWorld().isClient && fuse <= 0) {
            spawnBees();
            this.discard();
            return;
        }

        this.getWorld().addParticle(
                net.minecraft.particle.ParticleTypes.SMOKE,
                this.getX(), this.getY() + 0.5, this.getZ(),
                0, 0, 0
        );

        this.age++;
    }

    private void spawnBees() {
        World world = this.getWorld();
        BlockPos center = this.getBlockPos();

        int beeCount = 25;
        for (int i = 0; i < beeCount; i++) {
            BeeEntity bee = EntityType.BEE.create(world);
            if (bee != null) {
                double offsetX = world.random.nextDouble() * 2 - 1;
                double offsetZ = world.random.nextDouble() * 2 - 1;

                bee.refreshPositionAndAngles(
                        center.getX() + 0.5 + offsetX,
                        center.getY() + 0.5,
                        center.getZ() + 0.5 + offsetZ,
                        0,
                        0
                );

                bee.setAngerTime(3600);

                List<LivingEntity> targets = this.getWorld().getEntitiesByClass(LivingEntity.class, bee.getBoundingBox().expand(16), e -> !(e instanceof BeeEntity));
                for (LivingEntity target : targets) {
                    bee.setTarget(target);
                }
                world.spawnEntity(bee);
            }
        }
    }
}