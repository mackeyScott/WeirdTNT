package com.weirdtnt.item;

import com.weirdtnt.WeirdTNT;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import java.util.function.Function;

public class ModItems {
    
    private static Item registerItem(String name, Function<Item.Properties, Item> factory) {
        ResourceKey<Item> key = ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(WeirdTNT.MOD_ID, name));
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                factory.apply(new Item.Properties().setId(key)));
    }

    public static final Item logo = registerItem("logo", Item::new);

    public static void registerModItems(){
        WeirdTNT.LOGGER.info("Registering Mod Items for " + WeirdTNT.MOD_ID);
    }
}
