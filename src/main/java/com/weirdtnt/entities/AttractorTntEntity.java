package com.weirdtnt.entities;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AttractorTntEntity extends PrimedTnt {
    public AttractorTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
    }

    public AttractorTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
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
        this.level().addParticle(
                ParticleTypes.SMOKE,
                this.getX(), this.getY() + 0.5, this.getZ(),
                0.0, 0.0, 0.0
        );
    }

    private void pullEntities() {
        Level world = this.level();
        BlockPos pos = this.blockPosition();
        Vec3 epicenter = new Vec3(pos.getX(), pos.getY(), pos.getZ());

        double radius = 10.0;
        double strength = 0.15; // tweak for how fast entities are pulled

        List<Entity> entities = world.getEntities(this, this.getBoundingBox().inflate(radius));
        for (Entity entity : entities) {
            if (entity == this || !entity.isAlive()) continue;

            Vec3 toCenter = epicenter.subtract(entity.position());
            double distance = toCenter.length();
            if (distance > radius || distance < 0.01) continue;

            Vec3 pull = toCenter.normalize().scale(strength);
            entity.push(pull.x, pull.y, pull.z);
            entity.hurtMarked = true;
        }
    }

    private void explode() {
        Level world = this.level();
        world.explode(this, this.getX(), this.getY(), this.getZ(),
                0.0f, Level.ExplosionInteraction.NONE);
    }
}