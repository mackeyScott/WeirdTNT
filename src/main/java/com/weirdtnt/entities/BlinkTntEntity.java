package com.weirdtnt.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.StructureTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlinkTntEntity extends TntEntity {
    private TagKey<Structure>[] ALLOWED_STRUCTURES;

    public BlinkTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
        this.ALLOWED_STRUCTURES = new TagKey[]{
                StructureTags.VILLAGE,
                StructureTags.MINESHAFT,
                StructureTags.OCEAN_RUIN,
                StructureTags.RUINED_PORTAL,
                StructureTags.SHIPWRECK
        };
    }

    public BlinkTntEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (!this.hasNoGravity()) { // optional sanity
            this.setVelocity(this.getVelocity().add(0, -0.04, 0));
        }

        super.tick();

        if (!this.getWorld().isClient) {
            int fuse = this.getFuse() - 1;
            this.setFuse(fuse);

            if (fuse <= 0) {
                this.discard(); // remove TNT entity
                teleportEntities(this.getWorld());
            }
        }
    }

    private void teleportEntities(World world) {
        if (!(world instanceof ServerWorld serverWorld)) return;

        double radius = 10.0;
        List<Entity> entities = serverWorld.getOtherEntities(this, this.getBoundingBox().expand(radius));

        int index = random.nextInt(this.ALLOWED_STRUCTURES.length);

        TagKey<Structure> targetTag = this.ALLOWED_STRUCTURES[index];


        BlockPos structurePos = serverWorld.locateStructure(
                targetTag,
                this.getBlockPos(),
                1000,
                false
        );

        if (structurePos == null) {
            for (Entity entity : entities) {
                if (entity instanceof TeleportTntEntity) continue;

                int attempts = 10;
                BlockPos targetPos = null;

                for (int i = 0; i < attempts; i++) {
                    int dx = serverWorld.getRandom().nextBetween(-500, 500);
                    int dz = serverWorld.getRandom().nextBetween(-500, 500);
                    BlockPos candidate = entity.getBlockPos().add(dx, 0, dz);

                    BlockPos below = candidate.down();
                    if (serverWorld.getBlockState(candidate).isAir() &&
                            serverWorld.getBlockState(below).isOpaqueFullCube(serverWorld, below)) {
                        targetPos = candidate;
                        break;
                    }
                }

                if (targetPos == null) {
                    targetPos = entity.getBlockPos().add(
                            serverWorld.getRandom().nextBetween(-500, 500),
                            0,
                            serverWorld.getRandom().nextBetween(-500, 500)
                    );
                }

                entity.requestTeleport(
                        targetPos.getX() + 0.5,
                        targetPos.getY() + 0.5,
                        targetPos.getZ() + 0.5
                );
            }
            return;
        }

        for (Entity entity : entities) {
            if (entity instanceof TeleportTntEntity) continue;
            int dx = serverWorld.getRandom().nextBetween(-5, 5);
            int dz = serverWorld.getRandom().nextBetween(-5, 5);

            BlockPos target = structurePos.add(dx, 0, dz);

            int y = serverWorld.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, target.getX(), target.getZ());
            y = Math.max(y, 1);

            entity.requestTeleport(
                    target.getX() + 0.5,
                    y + 0.5,
                    target.getZ() + 0.5
            );

            world.getPlayers().forEach(player ->
                    player.sendMessage(Text.literal("Teleported to structure type: " + targetTag.id()), false)
            );
        }
    }


}