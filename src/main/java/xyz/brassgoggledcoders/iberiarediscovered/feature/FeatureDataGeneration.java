package xyz.brassgoggledcoders.iberiarediscovered.feature;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.block.HardBlock;
import xyz.brassgoggledcoders.iberiarediscovered.block.IHardBlock;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredBlocks;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredFeatures;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FeatureDataGeneration {

    public static void createProviders(GatherDataEvent event) {
        Multimap<HardStoneLocation, IHardBlock> locations = HashMultimap.create();

        for (RegistryEntry<Block> block : IberiaRediscovered.getRegistrate().getAll(ForgeRegistries.Keys.BLOCKS)) {
            block.filter(IHardBlock.class::isInstance)
                    .map(IHardBlock.class::cast)
                    .ifPresent(hardBlock -> locations.put(hardBlock.getLocation(), hardBlock));
        }

        final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());

        Map<ResourceLocation, BiomeModifier> modifierMap = locations.asMap()
                .entrySet()
                .stream()
                .map(entry -> Pair.of(
                        IberiaRediscovered.rl("hardstone_%s".formatted(entry.getKey().levelKey().location().getPath())),
                        new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).orElseThrow(), entry.getKey().biomeKey()),
                                HolderSet.direct(Holder.direct(new PlacedFeature(
                                        Holder.direct(new ConfiguredFeature<>(
                                                RediscoveredFeatures.HARD_STONE_FEATURE.get(),
                                                HardStoneFeatureConfiguration.fromHardStone(entry.getValue())
                                        )),
                                        Collections.emptyList()
                                ))),
                                GenerationStep.Decoration.UNDERGROUND_DECORATION
                        )
                ))
                .collect(Collectors.toMap(Pair::left, Pair::right));

        event.getGenerator().addProvider(
                event.includeServer(),
                JsonCodecProvider.forDatapackRegistry(
                        event.getGenerator(),
                        event.getExistingFileHelper(),
                        IberiaRediscovered.ID,
                        ops,
                        ForgeRegistries.Keys.BIOME_MODIFIERS,
                        modifierMap
                )
        );
    }
}
