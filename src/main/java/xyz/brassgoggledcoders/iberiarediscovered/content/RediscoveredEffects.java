package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.effect.TreatedEffect;

@SuppressWarnings("unused")
public class RediscoveredEffects {

    public static final Component TREATED_TEXT = IberiaRediscovered.getRegistrate()
            .addLang("effect", IberiaRediscovered.rl("treated"), "Treated");

    public static final RegistryEntry<MobEffect> TREATED = IberiaRediscovered.getRegistrate()
            .object("treated")
            .simple(Registry.MOB_EFFECT_REGISTRY, TreatedEffect::new);

    public static void setup() {

    }
}
