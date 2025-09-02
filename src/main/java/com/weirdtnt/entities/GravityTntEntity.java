package com.weirdtnt.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GravityTntEntity extends TntEntity {

    public GravityTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80); // optional fuse
    }

    public GravityTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (!getWorld().isClient && this.getFuse() <= 1) {
            applyLevitation();
            this.remove(RemovalReason.DISCARDED);
        }
    }

    private void applyLevitation() {
        double radius = 6.0;
        BlockPos origin = this.getBlockPos();

        // Get all entities in radius
        List<Entity> entities = getWorld().getOtherEntities(null, new Box(
                origin.add((int) -radius, (int) -radius, (int) -radius),
                origin.add((int) radius, (int) radius, (int) radius)
        ));

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity living && !(entity instanceof TntEntity)) {
                // Apply levitation
                living.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.LEVITATION,
                        50,
                        20,
                        false, // show particles
                        false  // show icon
                ));
            }
        }

        // Optional particle explosion effect
        if (getWorld() instanceof ServerWorld serverWorld) {
            Random random = serverWorld.random;
            serverWorld.spawnParticles(ParticleTypes.CLOUD, getX(), getY(), getZ(),
                    50, 1 + random.nextDouble(), 1 + random.nextDouble(), 1 + random.nextDouble(), 0.1);
        }
    }
}
