package com.weirdtnt.entities;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RainbowTntEntity extends PrimedTnt {

    public RainbowTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80); // 4 seconds
    }

    public RainbowTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide() && this.getFuse() <= 1) {
            explodeRainbow();
            level().playSound(null, this.blockPosition(),
                    SoundEvents.GENERIC_EXPLODE.value(),
                    SoundSource.BLOCKS,
                    4.0F, 1.0F);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    private void explodeRainbow() {
        BlockPos origin = this.blockPosition();
        RandomSource random = this.level().getRandom();
        int radius = 5;

        // Spawn colorful sheep
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (random.nextFloat() < 0.5f) { // 50% chance to spawn sheep
                    BlockPos pos = origin.offset(dx, 1, dz);

                    Sheep sheep = net.minecraft.world.entity.EntityTypes.SHEEP.create(this.level(), net.minecraft.world.entity.EntitySpawnReason.COMMAND);
                    if (sheep != null) {
                        sheep.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                        // Random color
                        sheep.setColor(DyeColor.values()[random.nextInt(DyeColor.values().length)]);
                        this.level().addFreshEntity(sheep);
                    }
                }
            }
        }

        // Rainbow particles
        for (int i = 0; i < 100; i++) {
            double px = origin.getX() + random.nextDouble() * radius * 2 - radius;
            double py = origin.getY() + random.nextDouble() * 2;
            double pz = origin.getZ() + random.nextDouble() * radius * 2 - radius;
            this.level().addParticle(
                    ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, 1.0F, 0.0F, 0.0F),
                    px, py, pz, 0, 0, 0);
        }

        // Optional: apply fun potion effects to nearby players
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class,
                new AABB(Vec3.atLowerCornerOf(origin.offset(-radius, -radius, -radius)), Vec3.atLowerCornerOf(origin.offset(radius, radius, radius))),
                e -> e instanceof LivingEntity);
        for (LivingEntity entity : entities) {
            entity.addEffect(new MobEffectInstance(MobEffects.SPEED, 200, 1));      // 10 sec speed boost
            entity.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 200, 2)); // 10 sec jump
            entity.addEffect(new MobEffectInstance(MobEffects.HASTE, 200, 1));      // 10 sec haste
            entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 2)); // 10 sec night vision
        }
    }
}