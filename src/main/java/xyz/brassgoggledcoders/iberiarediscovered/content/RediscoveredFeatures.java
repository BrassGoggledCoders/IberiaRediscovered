package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.feature.HardStoneFeature;

public class RediscoveredFeatures {
    public static final RegistryEntry<HardStoneFeature> HARD_STONE_FEATURE = IberiaRediscovered.getRegistrate()
            .object("hard_stone")
            .simple(ForgeRegistries.Keys.FEATURES, HardStoneFeature::new);

    public static void setup() {

    }
}
