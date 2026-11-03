package com.weirdtnt.entities;

import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RepulsorTntEntity extends PrimedTnt {

    public RepulsorTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    public RepulsorTntEntity(Level world, double x, double y, double z, LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    @Override
    public void tick() {
        if (!this.isAlive()) return;

        this.setFuse(this.getFuse() - 1);
        if (this.getFuse() <= 0) {
            this.discard();

            if (!this.level().isClientSide) {
                this.repulseEntities();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            this.level().addParticle(
                    net.minecraft.core.particles.ParticleTypes.SMOKE,
                    this.getX(), this.getY() + 0.5, this.getZ(),
                    0.0, 0.0, 0.0
            );
        }
    }

    private void repulseEntities() {
        Level world = this.level();

        double radius = 10.0;
        double strength = 100;

        List<Entity> entities = world.getEntities(this, this.getBoundingBox().inflate(radius));
        for (Entity target : entities) {
            if (target == this || !target.isAlive()) continue;

            Vec3 direction = target.position().subtract(this.position()).normalize();
            double distance = Math.max(0.1, this.distanceTo(target));
            double power = strength * (1.0 - (distance / radius));

            Vec3 velocity = direction.scale(power);
            target.push(velocity.x, velocity.y + 3, velocity.z);
            target.hurtMarked = true;
        }

        // Play sound + particles but no block damage
        world.explode(this, this.getX(), this.getY(), this.getZ(), 0.0f, Level.ExplosionInteraction.NONE);
    }
}