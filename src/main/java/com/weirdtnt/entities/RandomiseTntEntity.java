package com.weirdtnt.entities;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RandomiseTntEntity extends PrimedTnt {
    private List<Block> NOT_ALLOWED_BLOCKS = new ArrayList<>();

    public RandomiseTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.AIR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.CHEST);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.BEDROCK);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.ENDER_CHEST);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.BLACK_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.BROWN_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.BLUE_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.CYAN_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.GRAY_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.GREEN_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.LIGHT_BLUE_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.LIGHT_GRAY_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.MAGENTA_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.ORANGE_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.PINK_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.PURPLE_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.RED_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.WHITE_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.YELLOW_BED);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.GLASS_PANE);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.GLASS);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.DARK_OAK_DOOR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.OAK_DOOR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.SPRUCE_DOOR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.BIRCH_DOOR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.JUNGLE_DOOR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.ACACIA_DOOR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.BAMBOO_DOOR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.CRIMSON_DOOR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.WARPED_DOOR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.CHERRY_DOOR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.MANGROVE_DOOR);
        this.NOT_ALLOWED_BLOCKS.add(Blocks.IRON_DOOR);

    }

    public RandomiseTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (this.isRemoved()) return;

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);

        if (!this.level().isClientSide) {
            if (fuse <= 0) {
                explodeAndRandomize(this.level(), this.blockPosition(), 15, new Random());
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

    private void explodeAndRandomize(Level world, BlockPos center, int radius, Random random) {
        List<BlockPos> positions = new ArrayList<>();
        List<BlockState> states = new ArrayList<>();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = center.offset(x, y, z);

                    if (pos.closerThan(center, radius)) {
                        BlockState state = world.getBlockState(pos);

                        if (!NOT_ALLOWED_BLOCKS.contains(state.getBlock())) {
                            positions.add(pos);
                            states.add(state);
                        }
                    }
                }
            }
        }

        Collections.shuffle(states, random);

        for (int i = 0; i < positions.size(); i++) {
            world.setBlock(positions.get(i), states.get(i), 3);
        }
    }


}