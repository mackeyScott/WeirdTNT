package com.weirdtnt.entities;

import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
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

            if (!this.level().isClientSide()) {
                this.repulseEntities();
            }
        } else {
            this.level().addParticle(
                    ParticleTypes.SMOKE,
                    this.getX(), this.getY() + 0.5, this.getZ(),
                    0.0, 0.0, 0.0
            );
        }
    }

    private void repulseEntities() {
        Level world = this.level();

        double radius = 10.0;
        double strength = 20;

        List<Entity> entities = world.getEntities(this, this.getBoundingBox().inflate(radius));
        for (Entity target : entities) {
            if (target == this || !target.isAlive()) continue;

            Vec3 direction = target.position().subtract(this.position()).normalize();
            double distance = Math.max(0.1, this.distanceTo(target));
            double power = strength * (1.0 - (distance / radius));

            Vec3 velocity = direction.scale(power);
            target.push(velocity.x, velocity.y + 3, velocity.z);
            sendVelocity(target);
        }

        // Play sound + particles but no block damage
        world.explode(this, this.getX(), this.getY(), this.getZ(), 0.0f, Level.ExplosionInteraction.NONE);
    }

    // In 26.3, needsSync only updates OTHER players watching the entity.
    // A pushed player also needs their own motion packet, sent immediately.
    private static void sendVelocity(Entity entity) {
        entity.needsSync = true;
        if (entity instanceof ServerPlayer player) {
            player.connection.send(new ClientboundSetEntityMotionPacket(player));
        }
    }
}
