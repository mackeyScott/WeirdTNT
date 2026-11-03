package com.weirdtnt.item;

import com.weirdtnt.WeirdTNT;
import com.weirdtnt.blocks.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    public static final CreativeModeTab WT_GROUP = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            new ResourceLocation(WeirdTNT.MOD_ID, "igwtnt"),
            FabricItemGroup.builder().title(Component.translatable("Weird TNT")).icon(()
                    -> new ItemStack(ModItems.logo)).displayItems((displayContext, entries) -> {
                //entries.add(ModItems.jay);
                entries.accept(ModBlocks.WATER_TNT);
                entries.accept(ModBlocks.LAVA_TNT);
                entries.accept(ModBlocks.TELE_TNT);
                entries.accept(ModBlocks.CHUNK_TNT);
                entries.accept(ModBlocks.BNT);
                entries.accept(ModBlocks.OTOWER_TNT);
                entries.accept(ModBlocks.BREACH_TNT);
                entries.accept(ModBlocks.GRAVITY_TNT);
                entries.accept(ModBlocks.RAINBOW_TNT);
                entries.accept(ModBlocks.CLUSTER_TNT);
                entries.accept(ModBlocks.RESTORE_TNT);
                entries.accept(ModBlocks.BOOK_TNT);
                entries.accept(ModBlocks.RANDOMISE_TNT);
                entries.accept(ModBlocks.LIGHTNING_TNT);
                entries.accept(ModBlocks.REPULSOR_TNT);
                entries.accept(ModBlocks.ATTRACTOR_TNT);
                entries.accept(ModBlocks.BLINK_TNT);
            }).build());

    public static void registerItemGroups(){
        WeirdTNT.LOGGER.info("Registering Item Groups for " + WeirdTNT.MOD_ID);
    }
}
