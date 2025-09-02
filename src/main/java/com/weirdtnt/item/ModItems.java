package com.weirdtnt.item;

import com.weirdtnt.WeirdTNT;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item logo = registerItem("logo", new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(WeirdTNT.MOD_ID, name), item);
    }

    public static void registerModItems(){
        WeirdTNT.LOGGER.info("Registering Mod Items for " + WeirdTNT.MOD_ID);
    }
}
