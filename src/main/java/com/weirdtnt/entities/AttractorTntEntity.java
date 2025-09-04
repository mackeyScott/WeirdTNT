package com.weirdtnt.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AttractorTntEntity extends TntEntity {
    public AttractorTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    public AttractorTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (!this.isAlive()) return;
        pullEntities();
        this.setFuse(this.getFuse() - 1);
        if (this.getFuse() <= 0) {
            this.discard();
            explode();
        }
        this.getWorld().addParticle(
                ParticleTypes.SMOKE,
                this.getX(), this.getY() + 0.5, this.getZ(),
                0.0, 0.0, 0.0
        );
    }

    private void pullEntities() {
        World world = this.getWorld();
        BlockPos pos = this.getBlockPos();
        Vec3d epicenter = new Vec3d(pos.getX(), pos.getY(), pos.getZ());

        double radius = 10.0;
        double strength = 0.15; // tweak for how fast entities are pulled

        List<Entity> entities = world.getOtherEntities(this, this.getBoundingBox().expand(radius));
        for (Entity entity : entities) {
            if (entity == this || !entity.isAlive()) continue;

            Vec3d toCenter = epicenter.subtract(entity.getPos());
            double distance = toCenter.length();
            if (distance > radius || distance < 0.01) continue;

            Vec3d pull = toCenter.normalize().multiply(strength);
            entity.addVelocity(pull.x, pull.y, pull.z);
            entity.velocityModified = true;
        }
    }

    private void explode() {
        World world = this.getWorld();
        world.createExplosion(this, this.getX(), this.getY(), this.getZ(),
                0.0f, World.ExplosionSourceType.NONE);
    }
}