package com.weirdtnt.entities;

import com.weirdtnt.WeirdTNT;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

    public static final EntityType<WaterTntEntity> WATER_TNT_ENTITY =
            register("water_tnt_entity", WaterTntEntity::new);

    public static final EntityType<LavaTntEntity> LAVA_TNT_ENTITY =
            register("lava_tnt_entity", LavaTntEntity::new);

    public static final EntityType<TeleportTntEntity> TELE_TNT_ENTITY =
            register("tele_tnt_entity", TeleportTntEntity::new);

    public static final EntityType<ChunkTntEntity> CHUNK_TNT_ENTITY =
            register("chunk_tnt_entity", ChunkTntEntity::new);

    public static final EntityType<BntEntity> BNT_ENTITY =
            register("bnt_entity", BntEntity::new);

    public static final EntityType<OTowerTntEntity> OTOWER_ENTITY =
            register("otower_entity", OTowerTntEntity::new);

    public static final EntityType<BreachTntEntity> BREACH_ENTITY =
            register("breach_entity", BreachTntEntity::new);

    public static final EntityType<GravityTntEntity> GRAVITY_ENTITY =
            register("gravity_entity", GravityTntEntity::new);

    public static final EntityType<RainbowTntEntity> RAINBOW_ENTITY =
            register("rainbow_entity", RainbowTntEntity::new);

    public static final EntityType<ClusterTntEntity> CLUSTER_ENTITY =
            register("cluster_entity", ClusterTntEntity::new);

    public static final EntityType<RestoreTntEntity> RESTORE_ENTITY =
            register("restore_entity", RestoreTntEntity::new);

    public static final EntityType<BookTntEntity> BOOK_ENTITY =
            register("book_entity", BookTntEntity::new);

    // NOTE: the old code registered this under "book_entity" (a duplicate id).
    // Fixed to its own id.
    public static final EntityType<RandomiseTntEntity> RANDOMISE_ENTITY =
            register("randomise_entity", RandomiseTntEntity::new);

    public static final EntityType<LightningTntEntity> LIGHTNING_ENTITY =
            register("lightning_entity", LightningTntEntity::new);

    public static final EntityType<RepulsorTntEntity> REPULSOR_ENTITY =
            register("repulsor_entity", RepulsorTntEntity::new);

    public static final EntityType<AttractorTntEntity> ATTRACTOR_ENTITY =
            register("attractor_entity", AttractorTntEntity::new);

    public static final EntityType<BlinkTntEntity> BLINK_ENTITY =
            register("blink_entity", BlinkTntEntity::new);

    private static <T extends Entity> EntityType<T> register(
            String name, EntityType.EntityFactory<T> factory) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(
                Registries.ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(WeirdTNT.MOD_ID, name));

        return Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                key,
                EntityType.Builder.of(factory, MobCategory.MISC)
                        .sized(0.98F, 0.98F)
                        .clientTrackingRange(10)
                        .updateInterval(10)
                        .build(key));
    }

    public static void registerModEntities() {
        WeirdTNT.LOGGER.info("Registering Mod Entities for " + WeirdTNT.MOD_ID);
    }

}