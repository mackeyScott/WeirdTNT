package com.weirdtnt.entities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LightningTntEntity extends PrimedTnt {

    public LightningTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80); // 4 seconds, adjust as needed
    }

    public LightningTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.level().isClientSide) {
            if (fuse <= 0) {
                strikeLightningRipples();
                this.discard();
                return;
            }
        }

        this.level().addParticle(
                ParticleTypes.SMOKE,
                this.getX(), this.getY() + 0.5, this.getZ(),
                0.0, 0.0, 0.0
        );

        this.tickCount++;
    }

    private void strikeLightningRipples() {
        Level world = this.level();
        BlockPos center = this.blockPosition();

        // Explosion sound
        world.playSound(null, center,
                SoundEvents.GENERIC_EXPLODE,
                SoundSource.BLOCKS,
                4.0F, 1.0F);

        int rings = 5;        // number of ripple waves
        int strikes = 24;     // lightning per ring
        int spacing = 10;     // distance between each ring

        for (int r = 1; r <= rings; r++) {
            int radius = r * spacing; // radius grows with each ring

            for (int i = 0; i < strikes; i++) {
                double angle = (2 * Math.PI / strikes) * i;
                double offsetX = center.getX() + Math.cos(angle) * radius;
                double offsetZ = center.getZ() + Math.sin(angle) * radius;

                BlockPos strikePos = new BlockPos((int) offsetX, center.getY(), (int) offsetZ);

                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(world);
                if (lightning != null) {
                    lightning.moveTo(
                            strikePos.getX(),
                            strikePos.getY(),
                            strikePos.getZ()
                    );
                    world.addFreshEntity(lightning);
                }
            }
        }

        // Central electric burst
        for (int i = 0; i < 150; i++) {
            double px = center.getX() + world.random.nextDouble() * 10 - 5;
            double py = center.getY() + world.random.nextDouble() * 3;
            double pz = center.getZ() + world.random.nextDouble() * 10 - 5;
            world.addParticle(ParticleTypes.ELECTRIC_SPARK, px, py, pz, 0, 0, 0);
        }
    }
}
