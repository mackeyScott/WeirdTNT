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



    public static void registerModEntities() {
        WeirdTNT.LOGGER.info("Registering Mod Entities for " + WeirdTNT.MOD_ID);
    }

}
