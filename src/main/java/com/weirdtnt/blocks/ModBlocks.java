package com.weirdtnt.blocks;
import com.weirdtnt.WeirdTNT;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

    public static final Block WATER_TNT = registerBlock("water_tnt",
            new WaterTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block LAVA_TNT = registerBlock("lava_tnt",
            new LavaTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block TELE_TNT = registerBlock("tele_tnt",
            new TeleportTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block CHUNK_TNT = registerBlock("chunk_tnt",
            new ChunkTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block BNT = registerBlock("bnt",
            new BNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block OTOWER_TNT = registerBlock("otower_tnt",
            new OTowerTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block BREACH_TNT = registerBlock("breach_tnt",
            new BreachTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block GRAVITY_TNT = registerBlock("gravity_tnt",
            new GravityTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block RAINBOW_TNT = registerBlock("rainbow_tnt",
            new RainbowTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block CLUSTER_TNT = registerBlock("cluster_tnt",
            new ClusterTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block RESTORE_TNT = registerBlock("restore_tnt",
            new RestoreTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block BOOK_TNT = registerBlock("book_tnt",
            new BookTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block RANDOMISE_TNT = registerBlock("randomise_tnt",
            new RandomiseTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block LIGHTNING_TNT = registerBlock("lightning_tnt",
            new LightningTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block REPULSOR_TNT = registerBlock("repulsor_tnt",
            new RepulsorTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block ATTRACTOR_TNT = registerBlock("attractor_tnt",
            new AttractorTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    public static final Block BLINK_TNT = registerBlock("blink_tnt",
            new BlinkTNT(BlockBehaviour.Properties.copy(Blocks.TNT)));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(WeirdTNT.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(WeirdTNT.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerBlocks(){
        WeirdTNT.LOGGER.info("Registering Blocks for "+ WeirdTNT.MOD_ID);
    }
}
