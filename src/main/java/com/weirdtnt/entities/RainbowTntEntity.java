package com.weirdtnt.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RainbowTntEntity extends TntEntity {

    public RainbowTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80); // 4 seconds
    }

    public RainbowTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (!getWorld().isClient && this.getFuse() <= 1) {
            explodeRainbow();
            this.remove(RemovalReason.DISCARDED);
        }
    }

    private void explodeRainbow() {
        BlockPos origin = this.getBlockPos();
        Random random = this.getWorld().random;
        int radius = 5;

        // Spawn colorful sheep
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (random.nextFloat() < 0.5f) { // 50% chance to spawn sheep
                    BlockPos pos = origin.add(dx, 1, dz);

                    SheepEntity sheep = EntityType.SHEEP.create(this.getWorld());
                    if (sheep != null) {
                        sheep.refreshPositionAndAngles(pos, 0, 0);
                        // Random color
                        sheep.setColor(DyeColor.values()[random.nextInt(DyeColor.values().length)]);
                        this.getWorld().spawnEntity(sheep);
                    }
                }
            }
        }

        // Rainbow particles
        for (int i = 0; i < 100; i++) {
            double px = origin.getX() + random.nextDouble() * radius * 2 - radius;
            double py = origin.getY() + random.nextDouble() * 2;
            double pz = origin.getZ() + random.nextDouble() * radius * 2 - radius;
            this.getWorld().addParticle(ParticleTypes.ENTITY_EFFECT, px, py, pz,
                    1, 0, 0);
        }

        // Optional: apply fun potion effects to nearby players
        List<LivingEntity> entities = this.getWorld().getEntitiesByClass(LivingEntity.class,
                new Box(origin.add(-radius, -radius, -radius), origin.add(radius, radius, radius)),
                e -> e instanceof LivingEntity);
        for (LivingEntity entity : entities) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1)); // 10 sec speed boost
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 2)); // 10 sec jump
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 200, 1)); // 10 sec speed boost
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 200, 2)); // 10 sec jump
        }
    }
}