package com.weirdtnt.item;

import com.weirdtnt.WeirdTNT;
import com.weirdtnt.blocks.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup WT_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(WeirdTNT.MOD_ID, "igwtnt"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.logo")).icon(()
                    -> new ItemStack(ModItems.logo)).entries((displayContext, entries) -> {
                //entries.add(ModItems.jay);
                entries.add(ModBlocks.WATER_TNT);
                entries.add(ModBlocks.LAVA_TNT);
                entries.add(ModBlocks.TELE_TNT);
                entries.add(ModBlocks.CHUNK_TNT);
            }).build());

    public static void registerItemGroups(){
        WeirdTNT.LOGGER.info("Registering Item Groups for " + WeirdTNT.MOD_ID);
    }
}
