package com.brassgoggledcoders.iberiarediscovered;

import com.tterrag.registrate.Registrate;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.fml.common.Mod;

@Mod(IberiaRediscovered.ID)
public class IberiaRediscovered {
    public static final String ID = "iberia_rediscovered";

    private static final NonNullLazy<Registrate> REGISTRATE = NonNullLazy.of(() -> Registrate.create(ID));

    public IberiaRediscovered() {

    }

    public static Registrate getRegistrate() {
        return REGISTRATE.get();
    }
}
