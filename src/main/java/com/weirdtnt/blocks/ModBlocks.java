package com.weirdtnt.blocks;
import com.weirdtnt.WeirdTNT;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block WATER_TNT = registerBlock("water_tnt",
            new WaterTNT(Block.Settings.copy(Blocks.TNT)));

    public static final Block LAVA_TNT = registerBlock("lava_tnt",
            new LavaTNT(Block.Settings.copy(Blocks.TNT)));

    public static final Block TELE_TNT = registerBlock("tele_tnt",
            new TeleportTNT(Block.Settings.copy(Blocks.TNT)));

    public static final Block CHUNK_TNT = registerBlock("chunk_tnt",
            new ChunkTNT(Block.Settings.copy(Blocks.TNT)));

    public static final Block BNT = registerBlock("bnt",
            new BNT(Block.Settings.copy(Blocks.TNT)));

    public static final Block OTOWER_TNT = registerBlock("otower_tnt",
            new OTowerTNT(Block.Settings.copy(Blocks.TNT)));

    public static final Block BREACH_TNT = registerBlock("breach_tnt",
            new BreachTNT(Block.Settings.copy(Blocks.TNT)));

    public static final Block GRAVITY_TNT = registerBlock("gravity_tnt",
            new GravityTNT(Block.Settings.copy(Blocks.TNT)));

    public static final Block RAINBOW_TNT = registerBlock("rainbow_tnt",
            new RainbowTNT(Block.Settings.copy(Blocks.TNT)));

    public static final Block CLUSTER_TNT = registerBlock("cluster_tnt",
            new ClusterTNT(Block.Settings.copy(Blocks.TNT)));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(WeirdTNT.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(WeirdTNT.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerBlocks(){
        WeirdTNT.LOGGER.info("Registering Blocks for "+ WeirdTNT.MOD_ID);
    }
}
