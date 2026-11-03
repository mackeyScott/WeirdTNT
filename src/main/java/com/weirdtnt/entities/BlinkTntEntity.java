package com.weirdtnt.entities;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;

public class BlinkTntEntity extends PrimedTnt {
    private List<TagKey<Structure>> allowedStructures;

    public BlinkTntEntity(Level world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(80);
        this.allowedStructures = List.of(
                StructureTags.VILLAGE,
                StructureTags.MINESHAFT,
                StructureTags.OCEAN_RUIN,
                StructureTags.RUINED_PORTAL,
                StructureTags.SHIPWRECK
        );

    }

    public BlinkTntEntity(EntityType<? extends PrimedTnt> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) { // optional sanity
            this.setDeltaMovement(this.getDeltaMovement().add(0, -0.04, 0));
        }

        super.tick();

        if (!this.level().isClientSide) {
            int fuse = this.getFuse() - 1;
            this.setFuse(fuse);

            if (fuse <= 0) {
                this.discard(); // remove TNT entity
                teleportEntities(this.level());
            }
        }
    }

    private void teleportEntities(Level world) {
        if (!(world instanceof ServerLevel serverWorld)) return;

        double radius = 10.0;
        List<Entity> entities = serverWorld.getEntities(this, this.getBoundingBox().inflate(radius));

        int index = random.nextInt(this.allowedStructures.size());

        TagKey<Structure> targetTag = this.allowedStructures.get(index);


        BlockPos structurePos = serverWorld.findNearestMapStructure(
                targetTag,
                this.blockPosition(),
                1000,
                false
        );

        if (structurePos == null) {
            for (Entity entity : entities) {
                if (entity instanceof TeleportTntEntity) continue;

                int attempts = 10;
                BlockPos targetPos = null;

                for (int i = 0; i < attempts; i++) {
                    int dx = serverWorld.getRandom().nextIntBetweenInclusive(-500, 500);
                    int dz = serverWorld.getRandom().nextIntBetweenInclusive(-500, 500);
                    BlockPos candidate = entity.blockPosition().offset(dx, 0, dz);

                    BlockPos below = candidate.below();
                    if (serverWorld.getBlockState(candidate).isAir() &&
                            serverWorld.getBlockState(below).isSolidRender(serverWorld, below)) {
                        targetPos = candidate;
                        break;
                    }
                }

                if (targetPos == null) {
                    targetPos = entity.blockPosition().offset(
                            serverWorld.getRandom().nextIntBetweenInclusive(-500, 500),
                            0,
                            serverWorld.getRandom().nextIntBetweenInclusive(-500, 500)
                    );
                }

                entity.teleportTo(
                        targetPos.getX() + 0.5,
                        targetPos.getY() + 0.5,
                        targetPos.getZ() + 0.5
                );
            }
            return;
        }

        for (Entity entity : entities) {
            if (entity instanceof TeleportTntEntity) continue;
            int dx = serverWorld.getRandom().nextIntBetweenInclusive(-5, 5);
            int dz = serverWorld.getRandom().nextIntBetweenInclusive(-5, 5);

            BlockPos target = structurePos.offset(dx, 0, dz);

            int y = serverWorld.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, target.getX(), target.getZ());
            y = Math.max(y, 1);

            entity.teleportTo(
                    target.getX() + 0.5,
                    y + 0.5,
                    target.getZ() + 0.5
            );

            world.players().forEach(player ->
                    player.displayClientMessage(Component.literal("Teleported to structure type: " + targetTag.location()), false)
            );
        }
    }


}