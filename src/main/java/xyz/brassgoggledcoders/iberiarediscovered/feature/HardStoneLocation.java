package xyz.brassgoggledcoders.iberiarediscovered.feature;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public record HardStoneLocation(
        ResourceKey<Level> levelKey,
        TagKey<Biome> biomeKey,
        TagKey<Block> blockKey
) {
    public static HardStoneLocation NETHER = new HardStoneLocation(Level.NETHER, BiomeTags.IS_NETHER, BlockTags.BASE_STONE_NETHER);
    public static HardStoneLocation OVERWORLD = new HardStoneLocation(Level.OVERWORLD, BiomeTags.IS_OVERWORLD, BlockTags.BASE_STONE_OVERWORLD);
}
