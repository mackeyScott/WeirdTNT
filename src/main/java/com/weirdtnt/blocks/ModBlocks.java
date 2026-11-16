package com.weirdtnt.blocks;

import com.weirdtnt.WeirdTNT;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {

    public static final Block WATER_TNT = registerBlock("water_tnt", WaterTNT::new);

    public static final Block LAVA_TNT = registerBlock("lava_tnt", LavaTNT::new);

    public static final Block TELE_TNT = registerBlock("tele_tnt", TeleportTNT::new);

    public static final Block CHUNK_TNT = registerBlock("chunk_tnt", ChunkTNT::new);

    // Lambda used here because the field name BNT matches the class name BNT.
    public static final Block BNT = registerBlock("bnt", props -> new BNT(props));

    public static final Block OTOWER_TNT = registerBlock("otower_tnt", OTowerTNT::new);

    public static final Block BREACH_TNT = registerBlock("breach_tnt", BreachTNT::new);

    public static final Block GRAVITY_TNT = registerBlock("gravity_tnt", GravityTNT::new);

    public static final Block RAINBOW_TNT = registerBlock("rainbow_tnt", RainbowTNT::new);

    public static final Block CLUSTER_TNT = registerBlock("cluster_tnt", ClusterTNT::new);

    public static final Block RESTORE_TNT = registerBlock("restore_tnt", RestoreTNT::new);

    public static final Block BOOK_TNT = registerBlock("book_tnt", BookTNT::new);

    public static final Block RANDOMISE_TNT = registerBlock("randomise_tnt", RandomiseTNT::new);

    public static final Block LIGHTNING_TNT = registerBlock("lightning_tnt", LightningTNT::new);

    public static final Block REPULSOR_TNT = registerBlock("repulsor_tnt", RepulsorTNT::new);

    public static final Block ATTRACTOR_TNT = registerBlock("attractor_tnt", AttractorTNT::new);

    public static final Block BLINK_TNT = registerBlock("blink_tnt", BlinkTNT::new);

    private static Block registerBlock(String name,
                                       Function<BlockBehaviour.Properties, Block> factory) {
        Identifier id = Identifier.fromNamespaceAndPath(WeirdTNT.MOD_ID, name);
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);

        // The block id must be set on the Properties BEFORE the block is constructed.
        Block block = factory.apply(
                BlockBehaviour.Properties.ofFullCopy(Blocks.TNT).setId(blockKey));
        Registry.register(BuiltInRegistries.BLOCK, blockKey, block);

        // Same for the item: its id must be set before the BlockItem is created.
        BlockItem item = new BlockItem(block,
                new Item.Properties().useBlockDescriptionPrefix().setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return block;
    }

    public static void registerBlocks() {
        WeirdTNT.LOGGER.info("Registering Blocks for " + WeirdTNT.MOD_ID);
    }
}