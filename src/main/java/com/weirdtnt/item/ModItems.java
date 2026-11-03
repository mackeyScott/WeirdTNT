package com.weirdtnt.item;

import com.weirdtnt.WeirdTNT;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final Item logo = registerItem("logo", new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item){
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(WeirdTNT.MOD_ID, name), item);
    }

    public static void registerModItems(){
        WeirdTNT.LOGGER.info("Registering Mod Items for " + WeirdTNT.MOD_ID);
    }
}
