package com.weirdtnt.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
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

public class BreachTntEntity extends TntEntity {
    public BreachTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(5);

    }

    public BreachTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void checkBlockCollision() {
        // do nothing → ignore collisions
    }

    @Override
    public void tick() {
        // Fuse countdown
        if (!this.getWorld().isClient) {
            int fuse = this.getFuse();
            if (fuse > 0){
                // Loop through the 10 blocks beneath
                BlockPos tntPos = this.getBlockPos();
                for (int i = 1; i <= 2; i++) {
                    BlockPos posBelow = tntPos.down(i);
                    BlockState state = this.getWorld().getBlockState(posBelow);
                    if (!state.isAir() && !state.isOf(Blocks.BEDROCK)) {
                        this.getWorld().removeBlock(posBelow, false);
                        this.setPos(tntPos.getX(), tntPos.getY()-2, tntPos.getZ());
                    }
                }


                this.setFuse(fuse - 1);
            }

            if (fuse <= 0) {
                this.getWorld().createExplosion(this, getX(), getY(), getZ(), 4.0f, World.ExplosionSourceType.TNT);
                this.remove(RemovalReason.DISCARDED);
                return;
            }
        }

        // Particles
        this.getWorld().addParticle(ParticleTypes.SMOKE, getX(), getY() + 0.5, getZ(), 0, 0, 0);
    }


}
