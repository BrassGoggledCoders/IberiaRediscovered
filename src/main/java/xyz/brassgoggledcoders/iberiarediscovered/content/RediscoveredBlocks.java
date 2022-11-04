package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.mojang.datafixers.util.Function3;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.block.HardBlock;
import xyz.brassgoggledcoders.iberiarediscovered.block.HardRotatedPillarBlock;
import xyz.brassgoggledcoders.iberiarediscovered.block.IHardBlock;
import xyz.brassgoggledcoders.iberiarediscovered.feature.HardStoneLocation;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class RediscoveredBlocks {

    //Overworld
    public static final BlockEntry<HardBlock> HARD_STONE = createHardBlockFor("stone", HardStoneLocation.OVERWORLD, () -> Blocks.STONE);
    public static final BlockEntry<HardBlock> HARD_DEEPSLATE = createHardBlockFor("deepslate", HardStoneLocation.OVERWORLD, () -> Blocks.DEEPSLATE);
    public static final BlockEntry<HardBlock> HARD_DIORITE = createHardBlockFor("diorite", HardStoneLocation.OVERWORLD, () -> Blocks.DIORITE);
    public static final BlockEntry<HardBlock> HARD_ANDESITE = createHardBlockFor("andesite", HardStoneLocation.OVERWORLD, () -> Blocks.ANDESITE);
    public static final BlockEntry<HardBlock> HARD_GRANITE = createHardBlockFor("granite", HardStoneLocation.OVERWORLD, () -> Blocks.GRANITE);
    //Nether
    public static final BlockEntry<HardBlock> HARD_NETHERRACK = createHardBlockFor("netherrack", HardStoneLocation.NETHER, () -> Blocks.NETHERRACK);
    public static final BlockEntry<HardRotatedPillarBlock> HARD_BASALT = createHardBlockFor("basalt", HardStoneLocation.NETHER, () -> Blocks.BASALT, HardRotatedPillarBlock::new)
            .blockstate((context, provider) -> {
                ResourceLocation blockName = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(context.get().getBaseBlock()));
                ModelFile existing = provider.models()
                        .getExistingFile(new ResourceLocation(
                                blockName.getNamespace(),
                                "block/" + blockName.getPath()
                        ));

                provider.axisBlock(
                        context.get(),
                        existing,
                        existing
                );
            })
            .register();
    public static final BlockEntry<HardBlock> HARD_BLACKSTONE = createHardBlockFor("blackstone", HardStoneLocation.NETHER, () -> Blocks.BLACKSTONE);

    private static BlockEntry<HardBlock> createHardBlockFor(String name, HardStoneLocation location, NonNullSupplier<Block> block) {
        return createHardBlockFor(name, location, block, HardBlock::new)
                .blockstate((context, provider) -> {
                    ResourceLocation blockName = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get()));
                    provider.simpleBlock(
                            context.get(),
                            provider.models()
                                    .getExistingFile(new ResourceLocation(
                                            blockName.getNamespace(),
                                            "block/" + blockName.getPath()
                                    ))
                    );
                })
                .register();
    }

    private static <T extends Block & IHardBlock> BlockBuilder<T, Registrate> createHardBlockFor(
            String name,
            HardStoneLocation location,
            NonNullSupplier<Block> block,
            Function3<BlockBehaviour.Properties, HardStoneLocation, Supplier<Block>, T> blockConstructor
    ) {
        return IberiaRediscovered.getRegistrate()
                .object("hard_%s".formatted(name))
                .block(properties -> blockConstructor.apply(properties, location, block))
                .lang("Hard %s")
                .initialProperties(block)
                .tag(location.blockKey())
                .loot((lootTables, hardBlock) -> lootTables.dropOther(hardBlock, block.get()));
    }

    public static void setup() {

    }
}
