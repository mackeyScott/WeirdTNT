package com.weirdtnt.entities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LightningTntEntity extends TntEntity {

    public LightningTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80); // 4 seconds, adjust as needed
    }

    public LightningTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.getWorld().isClient) {
            if (fuse <= 0) {
                strikeLightningRipples();
                this.discard();
                return;
            }
        }

        this.getWorld().addParticle(
                ParticleTypes.SMOKE,
                this.getX(), this.getY() + 0.5, this.getZ(),
                0.0, 0.0, 0.0
        );

        this.age++;
    }

    private void strikeLightningRipples() {
        World world = this.getWorld();
        BlockPos center = this.getBlockPos();

        // Explosion sound
        world.playSound(null, center,
                SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.BLOCKS,
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

                LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                if (lightning != null) {
                    lightning.refreshPositionAfterTeleport(
                            strikePos.getX(),
                            strikePos.getY(),
                            strikePos.getZ()
                    );
                    world.spawnEntity(lightning);
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
