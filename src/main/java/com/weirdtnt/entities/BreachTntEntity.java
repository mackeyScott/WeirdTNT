package com.weirdtnt.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BreachTntEntity extends PrimedTnt {
    public BreachTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(5);

    }

    public BreachTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    @Override
    protected void checkInsideBlocks() {
        // do nothing → ignore collisions
    }

    @Override
    public void tick() {
        // Fuse countdown
        if (!this.level().isClientSide) {
            int fuse = this.getFuse();
            if (fuse > 0){
                // Loop through the 10 blocks beneath
                BlockPos tntPos = this.blockPosition();
                for (int i = 1; i <= 2; i++) {
                    BlockPos posBelow = tntPos.below(i);
                    BlockState state = this.level().getBlockState(posBelow);
                    if (!state.isAir() && !state.is(Blocks.BEDROCK)) {
                        this.level().removeBlock(posBelow, false);
                        this.setPosRaw(tntPos.getX(), tntPos.getY()-2, tntPos.getZ());
                    }
                }


                this.setFuse(fuse - 1);
            }

            if (fuse <= 0) {
                this.level().explode(this, getX(), getY(), getZ(), 4.0f, Level.ExplosionInteraction.TNT);
                level().playSound(null, this.blockPosition(),
                        SoundEvents.GENERIC_EXPLODE,
                        SoundSource.BLOCKS,
                        4.0F, 1.0F);
                this.remove(RemovalReason.DISCARDED);
                return;
            }
        }

        // Particles
        this.level().addParticle(ParticleTypes.SMOKE, getX(), getY() + 0.5, getZ(), 0, 0, 0);
    }


}
