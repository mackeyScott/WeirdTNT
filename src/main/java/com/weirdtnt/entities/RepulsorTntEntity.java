package com.weirdtnt.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class RepulsorTntEntity extends TntEntity {

    public RepulsorTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    public RepulsorTntEntity(World world, double x, double y, double z, LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    @Override
    public void tick() {
        if (!this.isAlive()) return;

        this.setFuse(this.getFuse() - 1);
        if (this.getFuse() <= 0) {
            this.discard();

            if (!this.getWorld().isClient) {
                this.repulseEntities();
            }
        } else {
            this.updateWaterState();
            this.getWorld().addParticle(
                    net.minecraft.particle.ParticleTypes.SMOKE,
                    this.getX(), this.getY() + 0.5, this.getZ(),
                    0.0, 0.0, 0.0
            );
        }
    }

    private void repulseEntities() {
        World world = this.getWorld();
        BlockPos pos = this.getBlockPos();

        double radius = 10.0;
        double strength = 100;

        List<Entity> entities = world.getOtherEntities(this, this.getBoundingBox().expand(radius));
        for (Entity target : entities) {
            if (target == this || !target.isAlive()) continue;

            Vec3d direction = target.getPos().subtract(this.getPos()).normalize();
            double distance = Math.max(0.1, this.distanceTo(target));
            double power = strength * (1.0 - (distance / radius));

            Vec3d velocity = direction.multiply(power);
            target.addVelocity(velocity.x, velocity.y + 3, velocity.z);
            target.velocityModified = true;
        }

        // Play sound + particles but no block damage
        world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 0.0f, World.ExplosionSourceType.NONE);
    }
}