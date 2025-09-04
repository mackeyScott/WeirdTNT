package com.weirdtnt.entities;

import com.weirdtnt.WeirdTNT;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import javax.swing.text.html.parser.Entity;

import static net.minecraft.registry.Registries.ENTITY_TYPE;

public class ModEntities {

    public static final EntityType<WaterTntEntity> WATER_TNT_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "water_tnt_entity"),
            FabricEntityTypeBuilder.<WaterTntEntity>create(SpawnGroup.MISC, WaterTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<LavaTntEntity> LAVA_TNT_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "lava_tnt_entity"),
            FabricEntityTypeBuilder.<LavaTntEntity>create(SpawnGroup.MISC, LavaTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<TeleportTntEntity> TELE_TNT_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "tele_tnt_entity"),
            FabricEntityTypeBuilder.<TeleportTntEntity>create(SpawnGroup.MISC, TeleportTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<ChunkTntEntity> CHUNK_TNT_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "chunk_tnt_entity"),
            FabricEntityTypeBuilder.<ChunkTntEntity>create(SpawnGroup.MISC, ChunkTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<BntEntity> BNT_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "bnt_entity"),
            FabricEntityTypeBuilder.<BntEntity>create(SpawnGroup.MISC, BntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<OTowerTntEntity> OTOWER_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "otower_entity"),
            FabricEntityTypeBuilder.<OTowerTntEntity>create(SpawnGroup.MISC, OTowerTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<BreachTntEntity> BREACH_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "breach_entity"),
            FabricEntityTypeBuilder.<BreachTntEntity>create(SpawnGroup.MISC, BreachTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<GravityTntEntity> GRAVITY_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "gravity_entity"),
            FabricEntityTypeBuilder.<GravityTntEntity>create(SpawnGroup.MISC, GravityTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<RainbowTntEntity> RAINBOW_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "rainbow_entity"),
            FabricEntityTypeBuilder.<RainbowTntEntity>create(SpawnGroup.MISC, RainbowTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<ClusterTntEntity> CLUSTER_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "cluster_entity"),
            FabricEntityTypeBuilder.<ClusterTntEntity>create(SpawnGroup.MISC, ClusterTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<RestoreTntEntity> RESTORE_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "restore_entity"),
            FabricEntityTypeBuilder.<RestoreTntEntity>create(SpawnGroup.MISC, RestoreTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<BookTntEntity> BOOK_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "book_entity"),
            FabricEntityTypeBuilder.<BookTntEntity>create(SpawnGroup.MISC, BookTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<RandomiseTntEntity> RANDOMISE_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "book_entity"),
            FabricEntityTypeBuilder.<RandomiseTntEntity>create(SpawnGroup.MISC, RandomiseTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<LightningTntEntity> LIGHTNING_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "lightning_entity"),
            FabricEntityTypeBuilder.<LightningTntEntity>create(SpawnGroup.MISC, LightningTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<RepulsorTntEntity> REPULSOR_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "repulsor_entity"),
            FabricEntityTypeBuilder.<RepulsorTntEntity>create(SpawnGroup.MISC, RepulsorTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<AttractorTntEntity> ATTRACTOR_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(WeirdTNT.MOD_ID, "attractor_entity"),
            FabricEntityTypeBuilder.<AttractorTntEntity>create(SpawnGroup.MISC, AttractorTntEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(10)
                    .build()
    );



    public static void registerModEntities() {
        WeirdTNT.LOGGER.info("Registering Mod Entities for " + WeirdTNT.MOD_ID);
    }

}
