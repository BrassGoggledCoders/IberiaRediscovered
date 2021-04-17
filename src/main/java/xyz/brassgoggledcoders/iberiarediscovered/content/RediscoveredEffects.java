package xyz.brassgoggledcoders.iberiarediscovered.content;

import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.effect.TreatedEffect;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.TranslationTextComponent;

@SuppressWarnings("unused")
public class RediscoveredEffects {

    public static final TranslationTextComponent TREATED_TEXT = IberiaRediscovered.getRegistrate()
            .addLang("effect", IberiaRediscovered.rl("treated"), "Treated");

    public static final RegistryEntry<Effect> TREATED = IberiaRediscovered.getRegistrate()
            .object("treated")
            .simple(Effect.class, TreatedEffect::new);

    public static void setup() {

    }
}
