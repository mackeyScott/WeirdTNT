package com.weirdtnt.entities;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class GravityTntEntity extends PrimedTnt {

    public GravityTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80); // optional fuse
    }

    public GravityTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide && this.getFuse() <= 1) {
            applyLevitation();
            this.remove(RemovalReason.DISCARDED);
        }
    }

    private void applyLevitation() {
        double radius = 6.0;
        BlockPos origin = this.blockPosition();

        level().playSound(null, origin,
                SoundEvents.GENERIC_EXPLODE,
                SoundSource.BLOCKS,
                4.0F, 1.0F);

        // Get all entities in radius
        List<Entity> entities = level().getEntities(null, new AABB(
                origin.offset((int) -radius, (int) -radius, (int) -radius),
                origin.offset((int) radius, (int) radius, (int) radius)
        ));

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity living && !(entity instanceof PrimedTnt)) {
                // Apply levitation
                living.addEffect(new MobEffectInstance(
                        MobEffects.LEVITATION,
                        50,
                        20,
                        false, // show particles
                        false  // show icon
                ));
                //Apply Slow Falling
                living.addEffect(new MobEffectInstance(
                        MobEffects.SLOW_FALLING,
                        300,
                        20,
                        false, // show particles
                        false  // show icon
                ));
            }
        }

        // Optional particle explosion effect
        if (level() instanceof ServerLevel serverWorld) {
            RandomSource random = serverWorld.random;
            serverWorld.sendParticles(ParticleTypes.CLOUD, getX(), getY(), getZ(),
                    50, 1 + random.nextDouble(), 1 + random.nextDouble(), 1 + random.nextDouble(), 0.1);
        }
    }
}
