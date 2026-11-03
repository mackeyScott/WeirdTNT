package com.weirdtnt.entities;

import com.weirdtnt.WeirdTNT;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

    public static final EntityType<WaterTntEntity> WATER_TNT_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "water_tnt_entity"),
            FabricEntityTypeBuilder.<WaterTntEntity>create(MobCategory.MISC, WaterTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<LavaTntEntity> LAVA_TNT_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "lava_tnt_entity"),
            FabricEntityTypeBuilder.<LavaTntEntity>create(MobCategory.MISC, LavaTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<TeleportTntEntity> TELE_TNT_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "tele_tnt_entity"),
            FabricEntityTypeBuilder.<TeleportTntEntity>create(MobCategory.MISC, TeleportTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<ChunkTntEntity> CHUNK_TNT_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "chunk_tnt_entity"),
            FabricEntityTypeBuilder.<ChunkTntEntity>create(MobCategory.MISC, ChunkTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<BntEntity> BNT_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "bnt_entity"),
            FabricEntityTypeBuilder.<BntEntity>create(MobCategory.MISC, BntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<OTowerTntEntity> OTOWER_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "otower_entity"),
            FabricEntityTypeBuilder.<OTowerTntEntity>create(MobCategory.MISC, OTowerTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<BreachTntEntity> BREACH_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "breach_entity"),
            FabricEntityTypeBuilder.<BreachTntEntity>create(MobCategory.MISC, BreachTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<GravityTntEntity> GRAVITY_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "gravity_entity"),
            FabricEntityTypeBuilder.<GravityTntEntity>create(MobCategory.MISC, GravityTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<RainbowTntEntity> RAINBOW_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "rainbow_entity"),
            FabricEntityTypeBuilder.<RainbowTntEntity>create(MobCategory.MISC, RainbowTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<ClusterTntEntity> CLUSTER_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "cluster_entity"),
            FabricEntityTypeBuilder.<ClusterTntEntity>create(MobCategory.MISC, ClusterTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<RestoreTntEntity> RESTORE_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "restore_entity"),
            FabricEntityTypeBuilder.<RestoreTntEntity>create(MobCategory.MISC, RestoreTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<BookTntEntity> BOOK_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "book_entity"),
            FabricEntityTypeBuilder.<BookTntEntity>create(MobCategory.MISC, BookTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<RandomiseTntEntity> RANDOMISE_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "book_entity"),
            FabricEntityTypeBuilder.<RandomiseTntEntity>create(MobCategory.MISC, RandomiseTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<LightningTntEntity> LIGHTNING_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "lightning_entity"),
            FabricEntityTypeBuilder.<LightningTntEntity>create(MobCategory.MISC, LightningTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<RepulsorTntEntity> REPULSOR_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "repulsor_entity"),
            FabricEntityTypeBuilder.<RepulsorTntEntity>create(MobCategory.MISC, RepulsorTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<AttractorTntEntity> ATTRACTOR_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "attractor_entity"),
            FabricEntityTypeBuilder.<AttractorTntEntity>create(MobCategory.MISC, AttractorTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<BlinkTntEntity> BLINK_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(WeirdTNT.MOD_ID, "blink_entity"),
            FabricEntityTypeBuilder.<BlinkTntEntity>create(MobCategory.MISC, BlinkTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );



    public static void registerModEntities() {
        WeirdTNT.LOGGER.info("Registering Mod Entities for " + WeirdTNT.MOD_ID);
    }

}
